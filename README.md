# Mihainoa ny Teny (Écoutez la Parole) 📖🕊️

> **« Ny lanitra sy ny tany ho levona, fa ny teniko tsy mba ho levona. »** — *Marka 13:31*  
> **« Le ciel et la terre passeront, mais mes paroles ne passeront point. »** — *Marc 13:31*  
> **« Heaven and earth shall pass away: but my words shall not pass away. »** — *Mark 13:31*

Application Android moderne, complète et 100% hors-ligne pour la méditation biblique et les enseignements chrétiens, dédiée à l'œuvre du serviteur de Dieu **Solohery RAKOTOARISOA**.

---

## 🌟 Fonctionnalités Principales

1. **3 Bibles Complètes (66 Livres & 93 303 Versets - 100% Hors-ligne)** :
   - 🇲🇬 **Malagasy** : Baiboly Protestanta (MBP) – Ancien & Nouveau Testament complets (31 099 versets).
   - 🇫🇷 **Français** : Bible Louis Segond 1910 (LSG1910) – Ancien & Nouveau Testament complets (31 102 versets).
   - 🇬🇧 **English** : King James Version (KJV) – Complete Old & New Testaments (31 102 versets).

2. **Comparateur Parallèle de Bibles** :
   - Comparez verset par verset les trois versions simultanément pour approfondir l'étude des Écritures.

3. **Recherche & Concordance Intelligente** :
   - Moteur de recherche plein texte instantané sur l'ensemble des 93 303 versets avec filtres par testament, livre et version.

4. **Écrits & Sermons de Solohery RAKOTOARISOA** :
   - Bibliothèque d'enseignements spirituels, prédications, exhortations et notes d'étude.

5. **Lecteur Audio & Méditations** :
   - Écoute audio intégrée, lecteur avec commandes de lecture, mise en pause et progression.

6. **Marque-pages, Notes & Historique** :
   - Sauvegarde locale Room / SQLite de vos versets favoris, notes personnelles et historique de lecture.

7. **Design Material 3 Moderne & Élégant** :
   - Thème adaptatif (Clair / Sombre), typographie soignée, logo officiel avec verset gravé *Marka 13:31*.

---

## 📲 Téléchargement du fichier APK

Le fichier exécutable Android (`.apk`) est disponible directement :
- **Fichier** : `mihainoa-ny-teny.apk`
- **Taille** : ~34 Mo (inclut la base SQLite locale avec les 3 Bibles complètes)
- **Compatibilité** : Android 8.0 (API 26) et versions supérieures.

Pour l'installer sur votre téléphone :
1. Téléchargez le fichier `mihainoa-ny-teny.apk`.
2. Ouvrez le fichier sur votre smartphone Android.
3. Autorisez l'installation d'applications de sources inconnues si demandé.
4. Profitez de l'application en mode hors-ligne sans connexion Internet requise.

---

## 🛠️ Compilation & Développement

### Prérequis
- Android Studio Ladybug ou version plus récente
- JDK 17 ou 21
- Android SDK 35 (minSdk 26)

### Cloner et compiler
```bash
git clone https://github.com/<votre-nom-d-utilisateur>/mihainoa-ny-teny.git
cd mihainoa-ny-teny

# Compiler l'application en mode Debug
gradle assembleDebug

# Lancer les tests unitaires
gradle :app:testDebugUnitTest
```

L'APK généré se trouvera dans `app/build/outputs/apk/debug/app-debug.apk`.

---

## 📂 Structure du Projet

```text
├── app/
│   ├── src/main/
│   │   ├── assets/database/       # Base de données SQLite prépackagée (3 Bibles complètes)
│   │   ├── java/com/example/
│   │   │   ├── data/              # Entités Room, DAOs, Repositories
│   │   │   ├── ui/                # Écrans Compose (Bible, Recherche, Comparateur, Sermons, etc.)
│   │   │   └── viewmodel/         # Gestion de l'état ViewModel
│   │   └── res/                   # Drawables, icônes adaptatives, strings multilingues
│   └── build.gradle.kts
├── tools/                         # Scripts de validation et création de la base de données
├── mihainoa-ny-teny.apk           # APK prêt à l'emploi
└── README.md
```

---

## 📜 Licence & Droits
Application développée avec dévotion pour la propagation de la Parole de Dieu.
Textes bibliques dans le domaine public (MBP, LSG 1910, KJV).
Écrits et prédications : Droits réservés à l'auteur **Solohery RAKOTOARISOA**.
