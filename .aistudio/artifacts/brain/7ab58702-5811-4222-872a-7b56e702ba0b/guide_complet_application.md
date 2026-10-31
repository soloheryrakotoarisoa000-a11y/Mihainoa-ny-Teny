# MIHAINOA NY TENY
## Application Biblique Trilingue, Bibliothèque de Prédications & Centre de Formation Chrétienne

**Créateur et Responsable éditorial :** Solohery RAKOTOARISOA  
**Signatures officielles :**
- **Malagasy :** *« Mihainoa ny Tenin'Andriamanitra »*
- **Français :** *« Écoutez la Parole de Dieu »*
- **English :** *« Listen to the Word of God »*

---

## 1. Vue d'ensemble et Identité du Projet

L'application **Mihainoa ny Teny** est conçue comme un outil d'édification spirituelle, d'approfondissement théologique et d'étude biblique, capable de fonctionner **à 100% hors ligne** pour répondre notamment aux besoins des zones dépourvues de connexion Internet.

Elle offre un accès libre et direct aux prédications, conférences, enseignements doctrinaux et plans homilétiques rédigés par **Solohery RAKOTOARISOA**, tout en intégrant une suite complète d'outils d'étude biblique et linguistique.

---

## 2. Versions Bibliques Intégrées

| Sigle | Nom complet | Langue | Statut | Particularités |
| :--- | :--- | :--- | :--- | :--- |
| **MBP** | Baiboly Malagasy Protestanta (1908) | Malagasy | **100% Complet (31 099 versets)** | Les 66 livres intégraux de Genesisy à Apokalypsy |
| **LSG 1910** | Louis Segond (1910) | Français | **100% Complet (31 102 versets)** | Les 66 livres intégraux de Genèse à Apocalypse |
| **KJV** | King James Version (1611/1769) | English | **100% Complet (31 102 versets)** | Les 66 livres intégraux de Genesis à Revelation |
| **BHS** | Biblia Hebraica Stuttgartensia | Hébreu | 100% Embarqué | Ancien Testament original avec support RTL et morphologie |
| **NA28** | Novum Testamentum Graece | Grec koinè | 100% Embarqué | Nouveau Testament original avec lemmes et numéros Strong |
| **DIEM** | Dikan-teny Iombonana Eny Madagasikara | Malagasy | En attente | Indication claire de respect des droits éditoriaux |

---

## 3. Cloisonnement Strict des Langues (Tsy afangaro)

L'application respecte rigoureusement la langue sélectionnée par l'utilisateur :
- **Malagasy (par défaut) :** Tous les menus, boutons, titres de section, les 66 livres de la Bible (Genesisy, Salamo, Jaona, Apokalypsy) et la version biblique principale s'affichent en malagasy (MBP).
- **Français :** Interface traduite intégralement, canon des 66 livres en français (Genèse, Psaumes, Jean, Apocalypse) et synchronisation automatique avec la version Louis Segond (LSG 1910).
- **English :** Interface traduite intégralement, canon des 66 livres en anglais (Genesis, Psalms, John, Revelation) et synchronisation automatique avec la King James Version (KJV).

Aucun mélange linguistique n'est toléré dans l'interface utilisateur.

---

## 4. Outils de Copie Intégrale (Tout est copiable)

Pour faciliter le partage, la préparation des messages et les notes personnelles :
1. **Lecteur biblique :**
   - Chaque verset dispose d'un bouton d'action dédié **« Adikao / Copier »** qui place le texte et la référence dans le presse-papier avec confirmation visuelle immédiate.
   - Un bouton en en-tête permet de copier l'intégralité du chapitre en cours en une seule touche.
   - Le texte est enveloppé dans un composant `SelectionContainer` permettant la sélection native manuelle de tout extrait.
2. **Comparateur de versions :**
   - Chaque colonne ou ligne de comparaison comporte un bouton de copie directe du verset dans la traduction choisie.
3. **Fiche de publication & Prédication :**
   - Un bouton de copie en haut de l'écran copie l'intégralité du titre, des références bibliques, du corps du texte, des applications pratiques et de la signature de **Solohery RAKOTOARISOA**.

---

## 5. Espace Auteur & Studio d'Écriture (Mode Administrateur)

En tant qu'administrateur et auteur, **Solohery RAKOTOARISOA** bénéficie d'une liberté totale de rédaction au sein de l'application :

* **Authentification et session persistante :**
  - **Code PIN par défaut :** `7777` (ou `1234`).
  - La session reste active dans les préférences locales (`SharedPreferences`) sans forcer la réauthentification à chaque ouverture.
* **Accès direct depuis l'Accueil :**
  - Une carte interactive élégante « Studio Fanoratana (Auteur) » apparaît directement sur l'écran d'accueil dès que l'administrateur est connecté.
* **Fonctionnalités du Studio d'Écriture :**
  - **Sélection du type :** Toriteny (SERMON), Fianarana Baiboly (BIBLE_STUDY), Fiofanana Kristiana (TRAINING), Fampianarana fototra (DOCTRINE), Drafitra toriteny (HOMILETIC_PLAN), Egzeseze (EXEGESIS).
  - **Barre d'outils de formatage rapide :** Insertion en un clic de `# Lohateny`, `## Zana-dohateny`, `**Gras**`, `*Italique*`, `> Andininy` (citations), `- Lisitra`, `[Fampiharana]` (applications pratiques).
  - **Compteur dynamique :** Affichage en temps réel du nombre de mots et de caractères.
  - **Onglet Aperçu en direct (Fijerena mialoha) :** Visualisation exacte du rendu pour les lecteurs avec mise en page typographique soignée, surlignages et blocs de citation.
  - **Gestion de publication :** Bascule aisée entre statut *Publié* (visible par tous les lecteurs) et *Brouillon* (réservé à l'auteur).
  - **Sauvegarde et restauration JSON :** Exportation et importation de la base de données intégrale pour sécuriser les écrits hors ligne.

---

## 6. Architecture Technique

- **Langage :** Kotlin 2.0+
- **Interface graphique :** Jetpack Compose avec Material Design 3 (M3)
- **Base de données :** Room Database (SQLite local)
  - Tables : `publications`, `bible_versions`, `bible_books`, `bible_verses`, `original_words`, `theological_commentaries`, `personal_notes`, `bookmarks`, `favorites`.
- **Thème :**
  - Vert profond émeraude (`#0D2B24`), Ivoire parchemin (`#FAF8F5`), Or discret (`#D4AF37`).
  - Modes Parchment, Clair et Sombre avec taille de police et interligne ajustables.
- **Icône Adaptative Android :** Générée sur mesure avec la Bible ouverte, rayons dorés et croix chrétienne discrète.

---

## 7. Fichiers et Téléchargement de l'APK

* **Binaire compilé :** `app/build/outputs/apk/debug/app-debug.apk`
* **Plateformes supportées :** Android 7.0 (API 24) à Android 15 (API 36).
* **Téléchargement depuis AI Studio :** Via le menu contextuel en haut à droite > **« Download APK »** ou **« Export ZIP »**.
