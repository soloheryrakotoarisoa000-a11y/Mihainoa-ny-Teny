import os
import re
import json
import sqlite3
import urllib.request

output_db = "app/src/main/assets/database/mihainoa_ny_teny_prepopulated.db"
if os.path.exists(output_db):
    os.remove(output_db)

conn = sqlite3.connect(output_db)
c = conn.cursor()

# 1. Exact schema as expected by Room in AppDatabase_Impl.kt
c.execute("""
CREATE TABLE IF NOT EXISTS `publications` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title` TEXT NOT NULL,
    `summary` TEXT NOT NULL,
    `fullText` TEXT NOT NULL,
    `originalLang` TEXT NOT NULL,
    `author` TEXT NOT NULL,
    `date` TEXT NOT NULL,
    `bibleRefs` TEXT NOT NULL,
    `type` TEXT NOT NULL,
    `categories` TEXT NOT NULL,
    `practicalApplications` TEXT NOT NULL,
    `bibliography` TEXT NOT NULL,
    `audioFileName` TEXT,
    `isOfficial` INTEGER NOT NULL,
    `isPublished` INTEGER NOT NULL,
    `frenchTranslationTitle` TEXT,
    `frenchTranslationText` TEXT,
    `englishTranslationTitle` TEXT,
    `englishTranslationText` TEXT
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `bible_versions` (
    `code` TEXT NOT NULL,
    `name` TEXT NOT NULL,
    `language` TEXT NOT NULL,
    `edition` TEXT NOT NULL,
    `publisher` TEXT NOT NULL,
    `source` TEXT NOT NULL,
    `license` TEXT NOT NULL,
    `isInstalled` INTEGER NOT NULL,
    `isOriginalText` INTEGER NOT NULL,
    PRIMARY KEY(`code`)
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `bible_books` (
    `bookNumber` INTEGER NOT NULL,
    `testament` TEXT NOT NULL,
    `nameMg` TEXT NOT NULL,
    `nameFr` TEXT NOT NULL,
    `nameEn` TEXT NOT NULL,
    `abbrMg` TEXT NOT NULL,
    `abbrFr` TEXT NOT NULL,
    `abbrEn` TEXT NOT NULL,
    `chapterCount` INTEGER NOT NULL,
    PRIMARY KEY(`bookNumber`)
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `bible_verses` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `bookNumber` INTEGER NOT NULL,
    `chapter` INTEGER NOT NULL,
    `verse` INTEGER NOT NULL,
    `versionCode` TEXT NOT NULL,
    `text` TEXT NOT NULL
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `original_words` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `bookNumber` INTEGER NOT NULL,
    `chapter` INTEGER NOT NULL,
    `verse` INTEGER NOT NULL,
    `wordIndex` INTEGER NOT NULL,
    `originalText` TEXT NOT NULL,
    `transliteration` TEXT NOT NULL,
    `lemma` TEXT NOT NULL,
    `strongNumber` TEXT NOT NULL,
    `language` TEXT NOT NULL,
    `morphologyCode` TEXT NOT NULL,
    `morphologyDesc` TEXT NOT NULL,
    `definition` TEXT NOT NULL,
    `theologicalNote` TEXT NOT NULL,
    `isRtl` INTEGER NOT NULL
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `theological_commentaries` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `bookNumber` INTEGER NOT NULL,
    `chapter` INTEGER NOT NULL,
    `verseStart` INTEGER NOT NULL,
    `verseEnd` INTEGER NOT NULL,
    `passageTitle` TEXT NOT NULL,
    `author` TEXT NOT NULL,
    `workTitle` TEXT NOT NULL,
    `license` TEXT NOT NULL,
    `historicalContext` TEXT NOT NULL,
    `literaryStructure` TEXT NOT NULL,
    `verseExposition` TEXT NOT NULL,
    `christocentricFocus` TEXT NOT NULL,
    `homileticPoints` TEXT NOT NULL,
    `studyQuestions` TEXT NOT NULL
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `personal_notes` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title` TEXT NOT NULL,
    `content` TEXT NOT NULL,
    `bibleRef` TEXT,
    `publicationId` INTEGER,
    `createdAt` INTEGER NOT NULL,
    `colorHex` TEXT NOT NULL
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `bookmarks` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `bookNumber` INTEGER NOT NULL,
    `chapter` INTEGER NOT NULL,
    `verse` INTEGER NOT NULL,
    `label` TEXT NOT NULL,
    `createdAt` INTEGER NOT NULL
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS `favorites` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `itemType` TEXT NOT NULL,
    `referenceKey` TEXT NOT NULL,
    `title` TEXT NOT NULL,
    `subtitle` TEXT NOT NULL,
    `excerpt` TEXT NOT NULL,
    `createdAt` INTEGER NOT NULL
)
""")

c.execute("""
CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY, identity_hash TEXT)
""")
c.execute("""
INSERT OR REPLACE INTO room_master_table (id, identity_hash) VALUES(42, 'f319e220d2f7611f0c5dfb11662dd6de')
""")

# 2. Insert Bible Versions
versions_data = [
    ("MBP", "Baiboly Malagasy Protestanta", "mg", "1908", "Fikambanana Mampiely Baiboly Malagasy", "Trano Printy Protestanta Imarivolanitra", "Domaine public", 1, 0),
    ("LSG1910", "Louis Segond 1910", "fr", "1910", "Alliance Biblique Française", "Société Biblique Britannique et Étrangère", "Domaine public", 1, 0),
    ("KJV", "King James Version (Authorized)", "en", "1769/1611", "Church of England / University Press", "Cambridge Standard Text", "Public Domain", 1, 0),
    ("BHS", "Biblia Hebraica Stuttgartensia", "he", "1997", "Deutsche Bibelgesellschaft", "Codex Leningradensis B19A", "Texte académique de recherche", 1, 1),
    ("NA28", "Novum Testamentum Graece (NA28 / TR)", "grc", "28e éd.", "Deutsche Bibelgesellschaft", "Institut für Neutestamentliche Textforschung Münster", "Texte académique de recherche", 1, 1),
    ("DIEM", "Dikan-teny Iombonana Eny Madagasikara", "mg", "2004", "Fikambanana Mampiely Baiboly Malagasy (FMBM)", "FMBM Antananarivo", "Droits réservés - En attente d'accord", 0, 0)
]
c.executemany("INSERT INTO bible_versions VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", versions_data)

# 3. Canonical 66 Books
books_data = [
    (1, "OT", "Genesisy", "Genèse", "Genesis", "Gen", "Gen", "Gen", 50),
    (2, "OT", "Eksodosy", "Exode", "Exodus", "Eks", "Exo", "Exo", 40),
    (3, "OT", "Levitikosy", "Lévitique", "Leviticus", "Lev", "Lév", "Lev", 27),
    (4, "OT", "Nomery", "Nombres", "Numbers", "Nom", "Nom", "Num", 36),
    (5, "OT", "Deotoronomia", "Deutéronome", "Deuteronomy", "Deo", "Deu", "Deu", 34),
    (6, "OT", "Josoa", "Josué", "Joshua", "Jos", "Jos", "Jos", 24),
    (7, "OT", "Mpitsara", "Juges", "Judges", "Mpi", "Jug", "Judg", 21),
    (8, "OT", "Rota", "Ruth", "Ruth", "Rot", "Rut", "Ruth", 4),
    (9, "OT", "1 Samoela", "1 Samuel", "1 Samuel", "1Sa", "1Sa", "1Sam", 31),
    (10, "OT", "2 Samoela", "2 Samuel", "2 Samuel", "2Sa", "2Sa", "2Sam", 24),
    (11, "OT", "1 Mpanjaka", "1 Rois", "1 Kings", "1Mp", "1Ro", "1Kgs", 22),
    (12, "OT", "2 Mpanjaka", "2 Rois", "2 Kings", "2Mp", "2Ro", "2Kgs", 25),
    (13, "OT", "1 Tantara", "1 Chroniques", "1 Chronicles", "1Ta", "1Ch", "1Chr", 29),
    (14, "OT", "2 Tantara", "2 Chroniques", "2 Chronicles", "2Ta", "2Ch", "2Chr", 36),
    (15, "OT", "Ezra", "Esdras", "Ezra", "Ezr", "Esd", "Ezra", 10),
    (16, "OT", "Nehemia", "Néhémie", "Nehemiah", "Neh", "Néh", "Neh", 13),
    (17, "OT", "Estera", "Esther", "Esther", "Est", "Est", "Esth", 10),
    (18, "OT", "Joba", "Job", "Job", "Job", "Job", "Job", 42),
    (19, "OT", "Salamo", "Psaumes", "Psalms", "Sal", "Psa", "Ps", 150),
    (20, "OT", "Ohabolana", "Proverbes", "Proverbs", "Oha", "Pro", "Prov", 31),
    (21, "OT", "Mpitoriteny", "Ecclésiaste", "Ecclesiastes", "Mpt", "Ecc", "Eccl", 12),
    (22, "OT", "Tononkiran'i Solomona", "Cantique des Cantiques", "Song of Solomon", "Ton", "Can", "Song", 8),
    (23, "OT", "Isaia", "Ésaïe", "Isaiah", "Isa", "Ésa", "Isa", 66),
    (24, "OT", "Jeremia", "Jérémie", "Jeremiah", "Jer", "Jér", "Jer", 52),
    (25, "OT", "Fitomaniana", "Lamentations", "Lamentations", "Fit", "Lam", "Lam", 5),
    (26, "OT", "Ezekiela", "Ézéchiel", "Ezekiel", "Ezk", "Ézé", "Ezek", 48),
    (27, "OT", "Daniela", "Daniel", "Daniel", "Dan", "Dan", "Dan", 12),
    (28, "OT", "Hosea", "Osée", "Hosea", "Hos", "Osé", "Hos", 14),
    (29, "OT", "Joela", "Joël", "Joel", "Joe", "Joë", "Joel", 3),
    (30, "OT", "Amosa", "Amos", "Amos", "Amo", "Amo", "Amos", 9),
    (31, "OT", "Obadia", "Abdias", "Obadiah", "Oba", "Abd", "Obad", 1),
    (32, "OT", "Jona", "Jonas", "Jonah", "Jon", "Jon", "Jonah", 4),
    (33, "OT", "Mika", "Michée", "Micah", "Mik", "Mic", "Mic", 7),
    (34, "OT", "Nahoma", "Nahum", "Nahum", "Nah", "Nah", "Nah", 3),
    (35, "OT", "Habakoka", "Habacuc", "Habakkuk", "Hab", "Hab", "Hab", 3),
    (36, "OT", "Zefania", "Sophonie", "Zephaniah", "Zef", "Sop", "Zeph", 3),
    (37, "OT", "Hagay", "Aggée", "Haggai", "Hag", "Agg", "Hag", 2),
    (38, "OT", "Zakaria", "Zacharie", "Zechariah", "Zak", "Zac", "Zech", 14),
    (39, "OT", "Malakia", "Malachie", "Malachi", "Mal", "Mal", "Mal", 4),
    (40, "NT", "Matio", "Matthieu", "Matthew", "Mat", "Mat", "Matt", 28),
    (41, "NT", "Marka", "Marc", "Mark", "Mar", "Mar", "Mark", 16),
    (42, "NT", "Lioka", "Luc", "Luke", "Lio", "Luc", "Luke", 24),
    (43, "NT", "Jaona", "Jean", "John", "Jao", "Jea", "John", 21),
    (44, "NT", "Asan'ny Apostoly", "Actes", "Acts", "Asa", "Act", "Acts", 28),
    (45, "NT", "Romana", "Romains", "Romans", "Rom", "Rom", "Rom", 16),
    (46, "NT", "1 Korintiana", "1 Corinthiens", "1 Corinthians", "1Ko", "1Co", "1Cor", 16),
    (47, "NT", "2 Korintiana", "2 Corinthiens", "2 Corinthians", "2Ko", "2Co", "2Cor", 13),
    (48, "NT", "Galatiana", "Galates", "Galatians", "Gal", "Gal", "Gal", 6),
    (49, "NT", "Efesiana", "Éphésiens", "Ephesians", "Efe", "Éph", "Eph", 6),
    (50, "NT", "Filipiana", "Philippiens", "Philippians", "Fil", "Phi", "Phil", 4),
    (51, "NT", "Kolosiana", "Colossiens", "Colossians", "Kol", "Col", "Col", 4),
    (52, "NT", "1 Tesaloniana", "1 Thessaloniciens", "1 Thessalonians", "1Te", "1Th", "1Thess", 5),
    (53, "NT", "2 Tesaloniana", "2 Thessaloniciens", "2 Thessalonians", "2Te", "2Th", "2Thess", 3),
    (54, "NT", "1 Timoty", "1 Timothée", "1 Timothy", "1Ti", "1Ti", "1Tim", 6),
    (55, "NT", "2 Timoty", "2 Timothée", "2 Timothy", "2Ti", "2Ti", "2Tim", 4),
    (56, "NT", "Titosy", "Tite", "Titus", "Tit", "Tit", "Tit", 3),
    (57, "NT", "Filemona", "Philémon", "Philemon", "Flm", "Phm", "Phlm", 1),
    (58, "NT", "Hebreo", "Hébreux", "Hebrews", "Heb", "Héb", "Heb", 13),
    (59, "NT", "Jakoba", "Jacques", "James", "Jak", "Jac", "Jas", 5),
    (60, "NT", "1 Petera", "1 Pierre", "1 Peter", "1Pe", "1Pi", "1Pet", 5),
    (61, "NT", "2 Petera", "2 Pierre", "2 Peter", "2Pe", "2Pi", "2Pet", 3),
    (62, "NT", "1 Jaona", "1 Jean", "1 John", "1Ja", "1Je", "1Jn", 5),
    (63, "NT", "2 Jaona", "2 Jean", "2 John", "2Ja", "2Je", "2Jn", 1),
    (64, "NT", "3 Jaona", "3 Jean", "3 John", "3Ja", "3Je", "3Jn", 1),
    (65, "NT", "Joda", "Jude", "Jude", "Jod", "Jud", "Jude", 1),
    (66, "NT", "Apokalypsy", "Apocalypse", "Revelation", "Apk", "Apo", "Rev", 22)
]
c.executemany("INSERT INTO bible_books VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", books_data)

# 4. Publications of Solohery RAKOTOARISOA
publications_data = [
    (1, "Ny Hery sy ny Fahasoavan'ny Tenin'Andriamanitra",
     "Fandalinana lalina mikasika ny herin'ny Soratra Masina eo amin'ny fiainan'ny mpino sy ny fiangonana ankehitriny.",
     "# Ny Hery sy ny Fahasoavan'ny Tenin'Andriamanitra\n\n« Fa velona sy mahery ny tenin'Andriamanitra ka maranitra noho ny sabatra roa lela » (Hebreo 4:12).\n\n## 1. Ny Fahefan'ny Soratra Masina\nNy Baiboly dia tsy boky tsotra nosoratan'olombelona, fa tsindrimandrin'Andriamanitra ho an'ny famonjena sy ny fitomboana ara-panahy.\n\n## 2. Ny Fiasan'ny Fanahy Masina\nTsy ampy ny fahalalana ara-tsaina fotsiny; ilaina ny Fanahy Masina manazava ny hevitry ny Soratra Masina ao am-po.\n\n## 3. Fampiharana azo tsapain-tanana\n- Mamakia Baiboly isan'andro am-bavaka.\n- Saintsaino sy ampiharo amin'ny fiainana andavanandro.",
     "mg", "Solohery RAKOTOARISOA", "2026-10-25", "Hebreo 4:12; 2 Timoty 3:16-17", "SERMON",
     "Toriteny, Teolojia, Finoana",
     "1. Manokana fotoana mangina isaky ny maraina.\n2. Mandray an-tsoratra ny teny manohina ny fo.\n3. Mizara ny fahamarinana amin'ny manodidina.",
     "Baiboly Malagasy Protestanta 1908; Calvin, J. Institution de la Religion Chrétienne.",
     None, 1, 1,
     "La Puissance et la Grâce de la Parole de Dieu",
     "Étude biblique approfondie sur l'autorité et la grâce régénératrice des Saintes Écritures dans la vie quotidienne.",
     "The Power and Grace of the Word of God",
     "In-depth biblical study on the authority and transformative grace of the Scriptures in the believer's daily walk."),
    (2, "Kristy, ilay Teny tonga Nofo (Egzeseze Jaona 1:1-14)",
     "Fandalinana teolojika sy ara-pitsipika mikasika ny maha-Andriamanitra an'i Jesosy Kristy sy ny fahatongavany ho nofo.",
     "# Kristy, ilay Teny tonga Nofo\n\n« Tamin'ny voalohany ny Teny, ary ny Teny tao amin'Andriamanitra, ary ny Teny dia Andriamanitra » (Jaona 1:1).\n\n## 1. Ny Maha-Mandrakizay ny Logos\nNy teny hoe 'Logos' amin'ny teny grika dia manambara ny Fahendrena sy ny Fisian'Andriamanitra nialoha ny nahariana izao tontolo izao.\n\n## 2. Ny Fahatongavana ho Nofo\n« Ary ny Teny dia tonga nofo ka nonina tamintsika » (v.14). Tsy nofinofy na sarinteny izany fa zava-misy ara-tantara.",
     "mg", "Solohery RAKOTOARISOA", "2026-10-20", "Jaona 1:1-14; Kolosiana 1:15-20", "DOCTRINE",
     "Teolojia, Kristolojia, Fampianarana",
     "1. Fankatoavana an'i Kristy ho Tompo sy Mpamonjy tokana.\n2. Fankalazana ny fitiavan'Andriamanitra tonga teo anivontsika.",
     "Novum Testamentum Graece (NA28); Carson, D.A., The Gospel According to John.",
     None, 1, 1,
     "Christ, la Parole faite Chair (Exégèse Jean 1:1-14)",
     "Étude théologique et christologique rigoureuse sur l'incarnation et la divinité éternelle du Fils de Dieu.",
     "Christ, the Word Made Flesh (Exegesis John 1:1-14)",
     "Rigorous theological and christological study on the incarnation and eternal deity of the Son of God.")
]
c.executemany("INSERT INTO publications VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", publications_data)

# 5. Original Hebrew and Greek Words
words_data = [
    (1, 1, 1, 1, 1, "בְּרֵאשִׁית", "bə-rê-šîṯ", "רֵאשִׁית", "H7225", "HEBREW", "Prep-b | N-fs", "Préposition בְּ + Nom féminin singulier", "Au commencement, dans le principe premier", "Désigne le commencement absolu du temps et de l'univers matériel par la parole créatrice.", 1),
    (2, 1, 1, 1, 2, "בָּרָא", "bā-rā", "בָּרָא", "H1254", "HEBREW", "V-Qal-Perf-3ms", "Verbe Qal accompli 3e pers masc singulier", "Créa, tira du néant (ex nihilo)", "Ce verbe a exclusivement Dieu pour sujet dans tout le texte biblique.", 1),
    (3, 1, 1, 1, 3, "אֱלֹהִים", "’ĕ-lō-hîm", "אֱלוֹהַּ", "H430", "HEBREW", "N-mp", "Nom masculin pluriel de majesté", "Dieu, le Souverain Créateur", "Pluriel de plénitude et de transcendance divine.", 1),
    (4, 43, 1, 1, 1, "Ἐν", "En", "ἐν", "G1722", "GREEK", "Prep", "Préposition régissant le datif", "Dans, en, au sein de", "Fait écho direct au Bereshit de la Genèse.", 0),
    (5, 43, 1, 1, 2, "ἀρχῇ", "archē", "ἀρχή", "G746", "GREEK", "N-DSF", "Nom Datif Singulier Féminin", "Commencement, principe premier intemporel", "Sans article en grec pour souligner la qualité de ce commencement absolu.", 0),
    (6, 43, 1, 1, 3, "ἦν", "ēn", "εἰμί", "G1510", "GREEK", "V-IAI-3S", "Verbe indicatif imparfait actif 3e pers sing", "Était continuellement, existait", "L'imparfait indique une existence continue sans commencement temporel : le Fils n'a pas été créé.", 0),
    (7, 43, 1, 1, 5, "λόγος", "logos", "λόγος", "G3056", "GREEK", "N-NMS", "Nom Nominatif Masculin Singulier", "La Parole, le Verbe, la révélation vivante", "Jésus-Christ est l'expression parfaite, éternelle et vivante du Père.", 0),
    (8, 43, 1, 1, 7, "θεὸς", "theos", "θεός", "G2316", "GREEK", "N-NMS", "Nom prédicat sans article", "Dieu par nature, divin dans son essence", "Souligne la pleine nature divine du Logos tout en le distinguant personnellement du Père.", 0)
]
c.executemany("INSERT INTO original_words VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", words_data)

# 6. Theological Commentaries
commentaries_data = [
    (1, 1, 1, 1, 5, "Ny Famoronana sy ny Fahefan'Andriamanitra", "Solohery RAKOTOARISOA", "Fandalinana ny Genesisy", "Domaine public",
     "Tontolo tany Atsinanana Akaiky fahiny teo anivon'ny finoana maro sampy.",
     "1. Ny foto-piandohana (v.1)\n2. Ny toe-javatra talohan'ny famolavolana (v.2)\n3. Ny herin'ny Teny miteny mazava (v.3-5)",
     "Ny teny hoe 'Bara' dia mampiseho fa Andriamanitra irery ihany no manana fahefana mahary avy amin'ny tsi-fisian-javatra (ex nihilo).",
     "I Kristy no ilay Teny nahariana ny zavatra rehetra (Jaona 1:3; Kolosiana 1:16).",
     "1. Matokia an'ilay Andriamanitra mahary sy manavao.\n2. Ny Teniny no mitondra fahazavana ao anaty haizim-pito.",
     "Ahoana no ampiasan'Andriamanitra ny Teniny hanovana ny fonao ankehitriny?"),
    (2, 43, 1, 1, 14, "Ny Logos sy ny Fahatongavana ho Nofo", "Solohery RAKOTOARISOA", "Fandalinana ny Filazantsaran'i Jaona", "Domaine public",
     "Fiandohan'ny fiangonana tamin'ny taonjato voalohany nanoloana ny gnosisy.",
     "1. Ny Logos mandrakizay (v.1-5)\n2. Ny vavolombelona Jaona Mpanao Batisa (v.6-8)\n3. Ny fahazavana tonga amin'izao tontolo izao (v.9-13)\n4. Ny Teny tonga nofo (v.14)",
     "Ny 'Logos' dia manambara fa tsy hevitra tsy azo tsapain-tanana Andriamanitra fa niseho tamin'ny alalan'ny Zanany.",
     "Jesosy no fahatanterahan'ny fanambaran-tenan'Andriamanitra.",
     "1. Raiso amin'ny finoana marina Izy.\n2. Aoka hiseho amin'ny fiainana ny fahazavany.",
     "Inona no dikan'ny fahatongavan'i Kristy ho nofo ho an'ny fiainanao manokana?")
]
c.executemany("INSERT INTO theological_commentaries VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", commentaries_data)

conn.commit()

# 7. DOWNLOAD AND POPULATE FULL BIBLES
print("Downloading complete Malagasy Protestant Bible (MBP)...")
req = urllib.request.Request("https://raw.githubusercontent.com/Rohan29-AN/MG-Bible-65/main/Bible_MG65.json", headers={"User-Agent": "Mozilla/5.0"})
with urllib.request.urlopen(req) as resp:
    mg_data = json.loads(resp.read().decode())

# Map MG65 book ids (10, 20, 30... 230, etc.) to 1..66 based on books order
mg_id_to_canon = {}
for obj in mg_data.get("objects", []):
    if obj.get("name") == "books":
        for idx, row in enumerate(obj.get("rows", [])):
            mg_id_to_canon[row[1]] = idx + 1

mg_verses = []
tag_regex = re.compile(r'<[^>]+>')
for obj in mg_data.get("objects", []):
    if obj.get("name") == "verses":
        for row in obj.get("rows", []):
            b_id = row[0]
            canon_book = mg_id_to_canon.get(b_id)
            if canon_book is not None and 1 <= canon_book <= 66:
                ch = row[1]
                v = row[2]
                txt = tag_regex.sub("", row[3]).strip()
                mg_verses.append((canon_book, ch, v, "MBP", txt))

print(f"Inserting {len(mg_verses)} Malagasy verses...")
c.executemany("INSERT INTO bible_verses(bookNumber, chapter, verse, versionCode, text) VALUES (?, ?, ?, ?, ?)", mg_verses)
conn.commit()

print("Downloading complete French Louis Segond 1910 (LSG)...")
req = urllib.request.Request("https://raw.githubusercontent.com/Similarly1/lsg/master/segond_1910.json", headers={"User-Agent": "Mozilla/5.0"})
with urllib.request.urlopen(req) as resp:
    lsg_data = json.loads(resp.read().decode())

lsg_verses = []
for v in lsg_data.get("verses", []):
    b_num = v["book"]
    if 1 <= b_num <= 66:
        ch = v["chapter"]
        ver = v["verse"]
        txt = v["text"].replace("¶ ", "").replace("¶", "").strip()
        lsg_verses.append((b_num, ch, ver, "LSG1910", txt))

print(f"Inserting {len(lsg_verses)} French verses...")
c.executemany("INSERT INTO bible_verses(bookNumber, chapter, verse, versionCode, text) VALUES (?, ?, ?, ?, ?)", lsg_verses)
conn.commit()

print("Downloading complete English King James Version (KJV)...")
req = urllib.request.Request("https://raw.githubusercontent.com/thiagobodruk/bible/master/json/en_kjv.json", headers={"User-Agent": "Mozilla/5.0"})
with urllib.request.urlopen(req) as resp:
    kjv_data = json.loads(resp.read().decode())

kjv_verses = []
for b_idx, book in enumerate(kjv_data):
    b_num = b_idx + 1
    if 1 <= b_num <= 66:
        for ch_idx, chapter in enumerate(book["chapters"]):
            ch_num = ch_idx + 1
            for v_idx, v_text in enumerate(chapter):
                v_num = v_idx + 1
                kjv_verses.append((b_num, ch_num, v_num, "KJV", v_text.strip()))

print(f"Inserting {len(kjv_verses)} English verses...")
c.executemany("INSERT INTO bible_verses(bookNumber, chapter, verse, versionCode, text) VALUES (?, ?, ?, ?, ?)", kjv_verses)
conn.commit()

# Add BHS & NA28 original language key verses
original_verses = [
    (1, 1, 1, "BHS", "בְּרֵאשִׁ֖ית בָּרָ֣א אֱלֹהִ֑ים אֵ֥ת הַשָּׁמַ֖יִם וְאֵ֥ת הָאָֽרֶץ׃"),
    (1, 1, 2, "BHS", "וְהָאָ֗רֶץ הָיְתָ֥ה תֹ֙הוּ֙ וָבֹ֔הוּ וְחֹ֖שֶׁךְ עַל־פְּנֵ֣י תְה֑וֹם וְר֣וּחַ אֱלֹהִ֔ים מְרַחֶ֖פֶת עַל־פְּנֵ֥י הַמָּֽיִם׃"),
    (1, 1, 3, "BHS", "וַיֹּ֥אמֶר אֱלֹהִ֖ים יְהִ֣י א֑וֹר וַֽיְהִי־אֽוֹר׃"),
    (19, 23, 1, "BHS", "מִזְמ֥וֹר לְדָוִ֑ד יְהוָ֥ה רֹ֝עִ֗י לֹ֣א אֶחְסָֽר׃"),
    (19, 23, 2, "BHS", "בִּנְא֣וֹת דֶּ֭שֶׁא יַרְבִּיצֵ֑נִי עַל־מֵ֖י מְנֻח֣וֹת יְנַהֲלֵֽנִי׃"),
    (43, 1, 1, "NA28", "Ἐν ἀρχῇ ἦν ὁ λόγος, καὶ ὁ λόγος ἦν πρὸς τὸν θεόν, καὶ θεὸς ἦν ὁ λόγος."),
    (43, 1, 2, "NA28", "οὗτος ἦν ἐν ἀρχῇ πρὸς τὸν θεόν."),
    (43, 1, 14, "NA28", "Καὶ ὁ λόγος σὰρξ ἐγένετο καὶ ἐσκήνωσεν ἐν ἡμῖν, καὶ ἐθεασάμεθα τὴν δόξαν αὐτοῦ, δόξαν ὡς μονογενοῦς παρὰ πατρός, πλήρης χάριτος καὶ ἀληθείας.")
]
c.executemany("INSERT INTO bible_verses(bookNumber, chapter, verse, versionCode, text) VALUES (?, ?, ?, ?, ?)", original_verses)

conn.commit()

c.execute("SELECT COUNT(*) FROM bible_verses")
total_verses = c.fetchone()[0]
print(f"Total verses successfully stored: {total_verses}")

# Validation tests
for code, name in [("MBP", "Salamo 23:1"), ("LSG1910", "Psaume 23:1"), ("KJV", "Psalm 23:1")]:
    c.execute("SELECT text FROM bible_verses WHERE bookNumber=19 AND chapter=23 AND verse=1 AND versionCode=?", (code,))
    row = c.fetchone()
    print(f"{name} ({code}): {row[0] if row else 'NOT FOUND'}")

for code, name in [("MBP", "Jaona 3:16"), ("LSG1910", "Jean 3:16"), ("KJV", "John 3:16")]:
    c.execute("SELECT text FROM bible_verses WHERE bookNumber=43 AND chapter=3 AND verse=16 AND versionCode=?", (code,))
    row = c.fetchone()
    print(f"{name} ({code}): {row[0] if row else 'NOT FOUND'}")

c.execute("SELECT COUNT(*) FROM bible_books")
print("Total books:", c.fetchone()[0])

conn.close()
print(f"Database successfully generated at: {output_db}")
