package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PublicationEntity
import com.example.ui.i18n.AppTranslations
import com.example.ui.theme.GoldAccent

enum class AdminViewMode {
    LIST,
    STUDIO_EDITOR
}

@Composable
fun AdminPortalScreen(
    isAuthenticated: Boolean,
    adminPublications: List<PublicationEntity>,
    translations: AppTranslations,
    onVerifyPin: (String) -> Boolean,
    onSavePublication: (PublicationEntity, () -> Unit) -> Unit,
    onDeletePublication: (PublicationEntity, () -> Unit) -> Unit,
    onExportBackup: ((String) -> Unit) -> Unit,
    onImportBackup: (String, (Boolean) -> Unit) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var pinInput by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }

    // Navigation mode within Admin
    var viewMode by remember { mutableStateOf(AdminViewMode.LIST) }
    var editingPub by remember { mutableStateOf<PublicationEntity?>(null) }

    // Studio Editor Form States
    var formTitle by remember { mutableStateOf("") }
    var formSummary by remember { mutableStateOf("") }
    var formFullText by remember { mutableStateOf("") }
    var formType by remember { mutableStateOf("SERMON") }
    var formLang by remember { mutableStateOf("mg") }
    var formBibleRefs by remember { mutableStateOf("") }
    var formCategories by remember { mutableStateOf("Toriteny") }
    var formApplications by remember { mutableStateOf("") }
    var formBibliography by remember { mutableStateOf("Baiboly Malagasy Protestanta") }
    var formIsPublished by remember { mutableStateOf(true) }
    var studioTab by remember { mutableIntStateOf(0) } // 0: Editor, 1: Live Preview

    // Backup Dialogs
    var showExportResultDialog by remember { mutableStateOf(false) }
    var exportedJsonContent by remember { mutableStateOf("") }
    var showImportDialog by remember { mutableStateOf(false) }
    var importJsonInput by remember { mutableStateOf("") }

    // Delete Confirmation
    var pubToDelete by remember { mutableStateOf<PublicationEntity?>(null) }

    BackHandler {
        if (viewMode == AdminViewMode.STUDIO_EDITOR) {
            viewMode = AdminViewMode.LIST
        } else {
            onBack()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("admin_portal_screen")
    ) {
        // Top Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (viewMode == AdminViewMode.STUDIO_EDITOR) {
                        viewMode = AdminViewMode.LIST
                    } else {
                        onBack()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (viewMode == AdminViewMode.STUDIO_EDITOR) {
                            if (editingPub == null) "Studio : Lahatsoratra Vaovao" else "Studio : Fanovana Lahatsoratra"
                        } else {
                            translations.adminPortalTitle
                        },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = translations.authorName,
                        style = MaterialTheme.typography.labelSmall.copy(color = GoldAccent)
                    )
                }

                if (isAuthenticated && viewMode == AdminViewMode.LIST) {
                    IconButton(
                        onClick = {
                            editingPub = null
                            formTitle = ""
                            formSummary = ""
                            formFullText = ""
                            formType = "SERMON"
                            formLang = "mg"
                            formBibleRefs = ""
                            formCategories = "Toriteny"
                            formApplications = ""
                            formBibliography = "Baiboly Malagasy Protestanta"
                            formIsPublished = true
                            studioTab = 0
                            viewMode = AdminViewMode.STUDIO_EDITOR
                        },
                        modifier = Modifier.testTag("header_quick_new_pub_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "New publication",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        if (!isAuthenticated) {
            // Lock Screen Form
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = translations.adminPortalTitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = translations.adminLockedPrompt,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = pinInput,
                            onValueChange = {
                                pinInput = it
                                pinError = false
                            },
                            label = { Text(translations.adminPinPlaceholder) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            isError = pinError,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_pin_input")
                        )

                        if (pinError) {
                            Text(
                                text = translations.adminInvalidPin,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val ok = onVerifyPin(pinInput)
                                if (!ok) {
                                    pinError = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_login_submit_button")
                        ) {
                            Text(translations.adminUnlockButton)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Kaody fitsapana / PIN par défaut : 7777",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        } else if (viewMode == AdminViewMode.STUDIO_EDITOR) {
            // AUTHORING STUDIO (Full Screen with Live Formatting & Live Preview)
            Column(modifier = Modifier.fillMaxSize()) {
                TabRow(
                    selectedTabIndex = studioTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Tab(
                        selected = studioTab == 0,
                        onClick = { studioTab = 0 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Manoratra / Rédiger")
                            }
                        }
                    )
                    Tab(
                        selected = studioTab == 1,
                        onClick = { studioTab = 1 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Preview, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Fijerena mialoha / Aperçu")
                            }
                        }
                    )
                }

                if (studioTab == 0) {
                    // EDITOR TAB
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Type Selector Chips
                        item {
                            Text(
                                text = "Karazana lahatsoratra / Type :",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            val types = listOf(
                                "SERMON" to "Toriteny",
                                "BIBLE_STUDY" to "Fianarana Baiboly",
                                "TRAINING" to "Fiofanana Kristiana",
                                "DOCTRINE" to "Fampianarana fototra",
                                "HOMILETIC_PLAN" to "Drafitra toriteny",
                                "EXEGESIS" to "Egzeseze"
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                types.forEach { (typeCode, label) ->
                                    FilterChip(
                                        selected = formType == typeCode,
                                        onClick = { formType = typeCode },
                                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                                        leadingIcon = if (formType == typeCode) {
                                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                        } else null
                                    )
                                }
                            }
                        }

                        // Language Selector
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Fiteny :",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                listOf("mg" to "Malagasy", "fr" to "Français", "en" to "English").forEach { (code, name) ->
                                    FilterChip(
                                        selected = formLang == code,
                                        onClick = { formLang = code },
                                        label = { Text(name, style = MaterialTheme.typography.labelSmall) }
                                    )
                                }
                            }
                        }

                        // Title
                        item {
                            OutlinedTextField(
                                value = formTitle,
                                onValueChange = { formTitle = it },
                                label = { Text("Lohatenin'ny lahatsoratra / Titre *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        // Bible References
                        item {
                            OutlinedTextField(
                                value = formBibleRefs,
                                onValueChange = { formBibleRefs = it },
                                label = { Text("Andininy fototra / Références bibliques (oh: Jaona 1:1-14)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        // Summary
                        item {
                            OutlinedTextField(
                                value = formSummary,
                                onValueChange = { formSummary = it },
                                label = { Text("Famintinana fohy / Résumé") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 2,
                                maxLines = 4,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        // Formatting Toolbar Chips
                        item {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Lahatsoratra feno / Texte intégral *",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    val wordCount = formFullText.trim().split("\\s+".toRegex()).count { it.isNotBlank() }
                                    Text(
                                        text = "$wordCount teny / mots • ${formFullText.length} litera",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))

                                // Quick Formatting Toolbar
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    AssistChip(
                                        onClick = { formFullText += "\n\n# Lohateny lehibe\n" },
                                        label = { Text("# Lohateny", fontSize = 11.sp) },
                                        leadingIcon = { Icon(Icons.Default.Title, null, Modifier.size(12.dp)) }
                                    )
                                    AssistChip(
                                        onClick = { formFullText += "\n\n## Zana-dohateny\n" },
                                        label = { Text("## Zana-dohateny", fontSize = 11.sp) }
                                    )
                                    AssistChip(
                                        onClick = { formFullText += "**matevina** " },
                                        label = { Text("**Gras**", fontSize = 11.sp) },
                                        leadingIcon = { Icon(Icons.Default.FormatBold, null, Modifier.size(12.dp)) }
                                    )
                                    AssistChip(
                                        onClick = { formFullText += "*mandry* " },
                                        label = { Text("*Italique*", fontSize = 11.sp) },
                                        leadingIcon = { Icon(Icons.Default.FormatItalic, null, Modifier.size(12.dp)) }
                                    )
                                    AssistChip(
                                        onClick = { formFullText += "\n> \"Teny nindramina na andininy\"\n" },
                                        label = { Text("> Andininy", fontSize = 11.sp) },
                                        leadingIcon = { Icon(Icons.Default.FormatQuote, null, Modifier.size(12.dp)) }
                                    )
                                    AssistChip(
                                        onClick = { formFullText += "\n- Hevitra voalohany\n- Hevitra faharoa\n" },
                                        label = { Text("- Lisitra", fontSize = 11.sp) },
                                        leadingIcon = { Icon(Icons.Default.FormatListBulleted, null, Modifier.size(12.dp)) }
                                    )
                                    AssistChip(
                                        onClick = { formFullText += "\n\n### Fampiharana eo amin'ny fiainana :\n1. " },
                                        label = { Text("[Fampiharana]", fontSize = 11.sp) },
                                        colors = AssistChipDefaults.assistChipColors(containerColor = GoldAccent.copy(alpha = 0.15f))
                                    )
                                }
                            }
                        }

                        // Full Text Editor Area
                        item {
                            OutlinedTextField(
                                value = formFullText,
                                onValueChange = { formFullText = it },
                                placeholder = {
                                    Text("Soraty eto an-kalalahana ny toriteny, ny fandinihana, na ny fampianarana teolojika...")
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("admin_full_text_input"),
                                minLines = 10,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        // Practical Applications Field
                        item {
                            OutlinedTextField(
                                value = formApplications,
                                onValueChange = { formApplications = it },
                                label = { Text("Fampiharana eo amin'ny fiainana / Applications pratiques") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 2,
                                maxLines = 4,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        // Bibliography Field
                        item {
                            OutlinedTextField(
                                value = formBibliography,
                                onValueChange = { formBibliography = it },
                                label = { Text("Loharano sy boky nampiasaina / Bibliographie") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        // Published Status Switch
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (formIsPublished) {
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                    } else {
                                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                                    }
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (formIsPublished) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = null,
                                            tint = if (formIsPublished) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = if (formIsPublished) "Mivoaka ho hitan'ny rehetra (Publié)" else "Drafitra ihany (Brouillon masoina)",
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                                color = if (formIsPublished) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                            )
                                            Text(
                                                text = if (formIsPublished) "Hitan'ny mpampiasa amin'ny fandraisana sy fikarohana" else "Tsy miseho amin'ny mpamaky raha tsy efa avoakanao",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                    Switch(
                                        checked = formIsPublished,
                                        onCheckedChange = { formIsPublished = it }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // LIVE PREVIEW TAB (Copiable and beautifully formatted)
                    SelectionContainer {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp),
                            contentPadding = PaddingValues(vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            item {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Preview,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Fijerena mialoha ny endriky ny lahatsoratra ho an'ny mpamaky (Aperçu en direct)",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            }

                            // Header details
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Text(
                                                text = formType,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                ),
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = MaterialTheme.colorScheme.surfaceVariant
                                        ) {
                                            Text(
                                                text = formLang.uppercase(),
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = if (formIsPublished) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = if (formIsPublished) "Mivoaka (Publié)" else "Drafitra (Brouillon)",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            ),
                                            color = if (formIsPublished) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = formTitle.ifBlank { "(Tsy misy lohateny)" },
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                if (formBibleRefs.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Bookmark,
                                            contentDescription = null,
                                            tint = GoldAccent,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = formBibleRefs,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = GoldAccent
                                            )
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Mpanoratra : Solohery RAKOTOARISOA",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            // Summary preview
                            if (formSummary.isNotBlank()) {
                                item {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = formSummary,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                            ),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }
                                }
                            }

                            // Full text body
                            item {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        val lines = formFullText.lines()
                                        lines.forEach { line ->
                                            when {
                                                line.startsWith("# ") -> {
                                                    Spacer(modifier = Modifier.height(10.dp))
                                                    Text(
                                                        text = line.removePrefix("# "),
                                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                }
                                                line.startsWith("## ") -> {
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Text(
                                                        text = line.removePrefix("## "),
                                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                }
                                                line.startsWith("### ") -> {
                                                    Spacer(modifier = Modifier.height(6.dp))
                                                    Text(
                                                        text = line.removePrefix("### "),
                                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                        color = MaterialTheme.colorScheme.secondary
                                                    )
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                }
                                                line.startsWith("> ") -> {
                                                    Surface(
                                                        shape = RoundedCornerShape(4.dp),
                                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(vertical = 4.dp)
                                                    ) {
                                                        Text(
                                                            text = line.removePrefix("> "),
                                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                                            ),
                                                            color = MaterialTheme.colorScheme.onSurface,
                                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                                        )
                                                    }
                                                }
                                                line.startsWith("- ") || line.startsWith("* ") -> {
                                                    Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp)) {
                                                        Text("• ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                                        Text(
                                                            text = line.substring(2),
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = MaterialTheme.colorScheme.onSurface
                                                        )
                                                    }
                                                }
                                                line.isBlank() -> {
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                }
                                                else -> {
                                                    Text(
                                                        text = line,
                                                        style = MaterialTheme.typography.bodyMedium.copy(
                                                            lineHeight = 24.sp
                                                        ),
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        modifier = Modifier.padding(vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // Applications preview
                            if (formApplications.isNotBlank()) {
                                item {
                                    ElevatedCard(
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.elevatedCardColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(14.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.AutoAwesome,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = translations.practicalApplications,
                                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = formApplications,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }

                            // Bibliography preview
                            if (formBibliography.isNotBlank()) {
                                item {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        tonalElevation = 1.dp,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text(
                                                text = translations.bibliography,
                                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = formBibliography,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Bottom Action Bar in Studio
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                val fullCopy = buildString {
                                    appendLine(formTitle)
                                    if (formBibleRefs.isNotBlank()) appendLine(formBibleRefs)
                                    appendLine("Solohery RAKOTOARISOA")
                                    appendLine()
                                    if (formSummary.isNotBlank()) {
                                        appendLine("--- Famintinana ---")
                                        appendLine(formSummary)
                                        appendLine()
                                    }
                                    appendLine(formFullText)
                                    if (formApplications.isNotBlank()) {
                                        appendLine()
                                        appendLine("--- Fampiharana ---")
                                        appendLine(formApplications)
                                    }
                                }
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText(formTitle.ifBlank { "Mihainoa Lahatsoratra" }, fullCopy)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, translations.textCopied, Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(translations.copyDirect)
                        }

                        Button(
                            onClick = {
                                if (formTitle.isNotBlank()) {
                                    val now = "2026-10-29"
                                    val pubToSave = (editingPub ?: PublicationEntity(
                                        title = formTitle,
                                        summary = formSummary,
                                        fullText = formFullText,
                                        originalLang = formLang,
                                        date = now,
                                        bibleRefs = formBibleRefs,
                                        type = formType.ifBlank { "SERMON" },
                                        categories = formCategories,
                                        practicalApplications = formApplications,
                                        bibliography = formBibliography,
                                        isPublished = formIsPublished
                                    )).copy(
                                        title = formTitle,
                                        summary = formSummary,
                                        fullText = formFullText,
                                        originalLang = formLang,
                                        bibleRefs = formBibleRefs,
                                        type = formType.ifBlank { "SERMON" },
                                        categories = formCategories,
                                        practicalApplications = formApplications,
                                        bibliography = formBibliography,
                                        isPublished = formIsPublished
                                    )
                                    onSavePublication(pubToSave) {
                                        viewMode = AdminViewMode.LIST
                                    }
                                } else {
                                    Toast.makeText(context, "Ampidiro azafady ny lohateny", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .weight(1.4f)
                                .testTag("admin_save_publication_submit_button")
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (formIsPublished) "Avoaka (Publier)" else "Tehirizina (Brouillon)")
                        }
                    }
                }
            }
        } else {
            // AUTHENTICATED MANAGEMENT DASHBOARD
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Editorial Security & Identity Statement Card
                item {
                    ElevatedCard(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified Author",
                                tint = GoldAccent,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Espace Auteur & Administrateur",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Solohery RAKOTOARISOA • Mihainoa ny Teny",
                                    style = MaterialTheme.typography.labelMedium.copy(color = GoldAccent)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = translations.adminSecurityDisclaimer,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }

                // Main Action: Write New Publication
                item {
                    Button(
                        onClick = {
                            editingPub = null
                            formTitle = ""
                            formSummary = ""
                            formFullText = ""
                            formType = "SERMON"
                            formLang = "mg"
                            formBibleRefs = ""
                            formCategories = "Toriteny"
                            formApplications = ""
                            formBibliography = "Baiboly Malagasy Protestanta"
                            formIsPublished = true
                            studioTab = 0
                            viewMode = AdminViewMode.STUDIO_EDITOR
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("admin_new_publication_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = translations.adminNewPublication,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                // Backup & Restore Actions
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                onExportBackup { json ->
                                    exportedJsonContent = json
                                    showExportResultDialog = true
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("admin_export_backup_button")
                        ) {
                            Icon(Icons.Default.Upload, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Export Backup")
                        }

                        OutlinedButton(
                            onClick = {
                                importJsonInput = ""
                                showImportDialog = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("admin_import_backup_button")
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Import Backup")
                        }
                    }
                }

                // Publications count header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Lahatsoratra rehetra (${adminPublications.size})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = translations.allScripturesCopiable,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                // List of publications
                items(adminPublications) { pub ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = if (pub.isPublished) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                                    ) {
                                        Text(
                                            text = if (pub.isPublished) translations.adminPublishedStatus else translations.adminDraftStatus,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                            color = if (pub.isPublished) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "${pub.type} • ${pub.originalLang.uppercase()}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }

                                Row {
                                    // Copy text action
                                    IconButton(
                                        onClick = {
                                            val fullCopy = buildString {
                                                appendLine(pub.title)
                                                if (pub.bibleRefs.isNotBlank()) appendLine(pub.bibleRefs)
                                                appendLine("Solohery RAKOTOARISOA")
                                                appendLine()
                                                appendLine(pub.fullText)
                                            }
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText(pub.title, fullCopy)
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, translations.textCopied, Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy text",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Publish / Draft toggle
                                    IconButton(
                                        onClick = {
                                            onSavePublication(pub.copy(isPublished = !pub.isPublished)) {}
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Publish,
                                            contentDescription = "Toggle publish",
                                            tint = if (pub.isPublished) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Edit
                                    IconButton(
                                        onClick = {
                                            editingPub = pub
                                            formTitle = pub.title
                                            formSummary = pub.summary
                                            formFullText = pub.fullText
                                            formType = pub.type
                                            formLang = pub.originalLang
                                            formBibleRefs = pub.bibleRefs
                                            formCategories = pub.categories
                                            formApplications = pub.practicalApplications
                                            formBibliography = pub.bibliography
                                            formIsPublished = pub.isPublished
                                            studioTab = 0
                                            viewMode = AdminViewMode.STUDIO_EDITOR
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Delete
                                    IconButton(
                                        onClick = { pubToDelete = pub },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = pub.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (pub.bibleRefs.isNotBlank()) {
                                Text(
                                    text = pub.bibleRefs,
                                    style = MaterialTheme.typography.bodySmall.copy(color = GoldAccent),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    pubToDelete?.let { pub ->
        AlertDialog(
            onDismissRequest = { pubToDelete = null },
            title = { Text(translations.deleteConfirm) },
            text = { Text("\"${pub.title}\"") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeletePublication(pub) {
                            pubToDelete = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(translations.adminDelete)
                }
            },
            dismissButton = {
                TextButton(onClick = { pubToDelete = null }) {
                    Text(translations.cancel)
                }
            }
        )
    }

    // Export Result Dialog
    if (showExportResultDialog) {
        AlertDialog(
            onDismissRequest = { showExportResultDialog = false },
            title = { Text(translations.adminExportSuccess) },
            text = {
                Column {
                    Text(
                        text = "Ireo lahatsoratra sy naoty rehetra dia voaomana ho JSON :",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = exportedJsonContent.take(400) + "\n... (${exportedJsonContent.length} bytes)",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 5
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Mihainoa Backup JSON", exportedJsonContent)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Voatana amin'ny fandraisan-tsoratra (JSON copié)", Toast.LENGTH_SHORT).show()
                    showExportResultDialog = false
                }) {
                    Text("Handika JSON (Copier)")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportResultDialog = false }) {
                    Text(translations.cancel)
                }
            }
        )
    }

    // Import Dialog
    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = { Text(translations.adminImportJson) },
            text = {
                Column {
                    Text(
                        text = "Apetaho eto ny tahiry JSON hafarana :",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = importJsonInput,
                        onValueChange = { importJsonInput = it },
                        placeholder = { Text("{\"app\": \"Mihainoa ny Teny\", ...}") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 6
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (importJsonInput.isNotBlank()) {
                        onImportBackup(importJsonInput) {
                            showImportDialog = false
                        }
                    }
                }) {
                    Text(translations.adminImportJson)
                }
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text(translations.cancel)
                }
            }
        )
    }
}
