package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.PrepopulatedData
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.I18nManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun readStringFromContext_matchesAppName() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Mihainoa ny Teny", appName)
    }

    @Test
    fun verifyTrilingualTranslations() {
        val mg = I18nManager.get(AppLanguage.MALAGASY)
        val fr = I18nManager.get(AppLanguage.FRENCH)
        val en = I18nManager.get(AppLanguage.ENGLISH)

        assertEquals("Mihainoa ny Teny", mg.appName)
        assertEquals("« Mihainoa ny Tenin'Andriamanitra »", mg.appSignature)
        assertEquals("Solohery RAKOTOARISOA", mg.authorName)

        assertEquals("« Écoutez la Parole de Dieu »", fr.appSignature)
        assertEquals("« Listen to the Word of God »", en.appSignature)

        assertTrue(mg.offlineReadyNotice.contains("100%"))
        assertTrue(fr.offlineReadyNotice.contains("100%"))
        assertTrue(en.offlineReadyNotice.contains("100%"))
    }

    @Test
    fun verifyPrepopulatedDataIntegrity() {
        // Versions
        assertTrue(PrepopulatedData.versions.any { it.code == "MBP" && it.isInstalled })
        assertTrue(PrepopulatedData.versions.any { it.code == "LSG1910" && it.isInstalled })
        assertTrue(PrepopulatedData.versions.any { it.code == "BHS" && it.isOriginalText })
        assertTrue(PrepopulatedData.versions.any { it.code == "NA28" && it.isOriginalText })
        assertTrue(PrepopulatedData.versions.any { it.code == "DIEM" && !it.isInstalled })

        // Books (66 canonical books)
        assertEquals(66, PrepopulatedData.books.size)
        val gen = PrepopulatedData.books.find { it.bookNumber == 1 }
        assertNotNull(gen)
        assertEquals("Genesisy", gen?.nameMg)
        assertEquals("Genèse", gen?.nameFr)
        assertEquals("Genesis", gen?.nameEn)

        val rev = PrepopulatedData.books.find { it.bookNumber == 66 }
        assertNotNull(rev)
        assertEquals("Apokalypsy", rev?.nameMg)

        // Hebrew Exegesis
        val hebrewWords = PrepopulatedData.originalWords.filter { it.language == "HEBREW" }
        assertTrue(hebrewWords.isNotEmpty())
        assertTrue(hebrewWords.any { it.originalText == "בְּרֵאשִׁית" && it.isRtl })

        // Greek Exegesis
        val greekWords = PrepopulatedData.originalWords.filter { it.language == "GREEK" }
        assertTrue(greekWords.isNotEmpty())
        assertTrue(greekWords.any { it.originalText == "λόγος" })

        // Publications authored by Solohery RAKOTOARISOA
        assertTrue(PrepopulatedData.publications.all { it.author == "Solohery RAKOTOARISOA" })
        assertTrue(PrepopulatedData.publications.any { it.type == "SERMON" })
        assertTrue(PrepopulatedData.publications.any { it.type == "DOCTRINE" })

        // KJV Bible Version
        assertTrue(PrepopulatedData.versions.any { it.code == "KJV" && it.isInstalled })

        // Verses in MBP, LSG1910, KJV
        val mbpVerses = PrepopulatedData.verses.filter { it.versionCode == "MBP" }
        val lsgVerses = PrepopulatedData.verses.filter { it.versionCode == "LSG1910" }
        val kjvVerses = PrepopulatedData.verses.filter { it.versionCode == "KJV" }
        assertTrue(mbpVerses.isNotEmpty())
        assertTrue(lsgVerses.isNotEmpty())
        assertTrue(kjvVerses.isNotEmpty())
    }

    @Test
    fun verifyAdminAndCopyFeaturesInTranslations() {
        val mg = I18nManager.get(AppLanguage.MALAGASY)
        val fr = I18nManager.get(AppLanguage.FRENCH)
        val en = I18nManager.get(AppLanguage.ENGLISH)

        assertEquals("Adikao", mg.copyDirect)
        assertEquals("Copier", fr.copyDirect)
        assertEquals("Copy", en.copyDirect)

        assertFalse(mg.allScripturesCopiable.isBlank())
        assertFalse(fr.allScripturesCopiable.isBlank())
        assertFalse(en.allScripturesCopiable.isBlank())

        assertFalse(mg.quickWriteAction.isBlank())
        assertFalse(fr.quickWriteAction.isBlank())
        assertFalse(en.quickWriteAction.isBlank())
    }
}
