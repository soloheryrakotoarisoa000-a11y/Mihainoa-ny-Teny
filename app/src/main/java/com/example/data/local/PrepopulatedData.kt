package com.example.data.local

import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleVerseEntity
import com.example.data.model.BibleVersionEntity
import com.example.data.model.OriginalWordEntity
import com.example.data.model.PublicationEntity
import com.example.data.model.TheologicalCommentaryEntity

object PrepopulatedData {

    val versions = listOf(
        BibleVersionEntity(
            code = "MBP",
            name = "Baiboly Malagasy Protestanta (1865/1908)",
            language = "mg",
            edition = "Edisiona 1908 / FFMA - BFBS",
            publisher = "British and Foreign Bible Society",
            source = "Sora-tanana sy lahatsoratra ara-tantara",
            license = "Domaine public (Afaka ampiasaina malalaka)",
            isInstalled = true,
            isOriginalText = false
        ),
        BibleVersionEntity(
            code = "LSG1910",
            name = "Bible Louis Segond (1910)",
            language = "fr",
            edition = "Révision 1910 classique",
            publisher = "Société Biblique de France / Alliance Biblique",
            source = "Corpus textuel historique francophone",
            license = "Domaine public",
            isInstalled = true,
            isOriginalText = false
        ),
        BibleVersionEntity(
            code = "KJV",
            name = "King James Version (1611/1769)",
            language = "en",
            edition = "Oxford Standard 1769 Edition",
            publisher = "Cambridge / Oxford University Press",
            source = "English Authorized Historic Scriptures",
            license = "Public Domain (Domaine public)",
            isInstalled = true,
            isOriginalText = false
        ),
        BibleVersionEntity(
            code = "BHS",
            name = "Biblia Hebraica Stuttgartensia (Hébreu biblique)",
            language = "he",
            edition = "Texte massorétique du Codex de Léningrad B19A",
            publisher = "Deutsche Bibelgesellschaft (Corpus d'étude)",
            source = "Corpus exégétique d'étude de l'Ancien Testament",
            license = "Usage d'étude exégétique / Domaine public massorétique",
            isInstalled = true,
            isOriginalText = true
        ),
        BibleVersionEntity(
            code = "NA28",
            name = "Novum Testamentum Graece / Textus Receptus (Grec koinè)",
            language = "grc",
            edition = "Institut für Neutestamentliche Textforschung / TR",
            publisher = "Corpus d'étude biblique du Nouveau Testament",
            source = "Textes originaux grecs du Nouveau Testament",
            license = "Usage d'étude et de formation théologique",
            isInstalled = true,
            isOriginalText = true
        ),
        BibleVersionEntity(
            code = "DIEM",
            name = "Dikanteny Iombonana Eto Madagasikara (DIEM)",
            language = "mg",
            edition = "Fandikan-teny iombonana ekimenika",
            publisher = "Fikambanana Mampiely Baiboly Malagasy (FMBM)",
            source = "FMBM",
            license = "Texte sous droits d'auteur réservés (En attente d'autorisation formelle)",
            isInstalled = false,
            isOriginalText = false
        )
    )

    val books = listOf(
        // Ancien Testament (1-39)
        BibleBookEntity(1, "OT", "Genesisy", "Genèse", "Genesis", "Gen", "Gen", "Gen", 50),
        BibleBookEntity(2, "OT", "Eksodosy", "Exode", "Exodus", "Eks", "Ex", "Exod", 40),
        BibleBookEntity(3, "OT", "Levitikosy", "Lévitique", "Leviticus", "Lev", "Lév", "Lev", 27),
        BibleBookEntity(4, "OT", "Nomery", "Nombres", "Numbers", "Nom", "Nom", "Num", 36),
        BibleBookEntity(5, "OT", "Deoteronomia", "Deutéronome", "Deuteronomy", "Deo", "Deut", "Deut", 34),
        BibleBookEntity(6, "OT", "Josoa", "Josué", "Joshua", "Jos", "Jos", "Josh", 24),
        BibleBookEntity(7, "OT", "Mpitsara", "Juges", "Judges", "Mpit", "Jug", "Judg", 21),
        BibleBookEntity(8, "OT", "Rota", "Ruth", "Ruth", "Rot", "Ruth", "Ruth", 4),
        BibleBookEntity(9, "OT", "1 Samoela", "1 Samuel", "1 Samuel", "1Sam", "1Sam", "1Sam", 31),
        BibleBookEntity(10, "OT", "2 Samoela", "2 Samuel", "2 Samuel", "2Sam", "2Sam", "2Sam", 24),
        BibleBookEntity(11, "OT", "1 Mpanjaka", "1 Rois", "1 Kings", "1Mpan", "1Rois", "1Kgs", 22),
        BibleBookEntity(12, "OT", "2 Mpanjaka", "2 Rois", "2 Kings", "2Mpan", "2Rois", "2Kgs", 25),
        BibleBookEntity(13, "OT", "1 Tantara", "1 Chroniques", "1 Chronicles", "1Tan", "1Chr", "1Chr", 29),
        BibleBookEntity(14, "OT", "2 Tantara", "2 Chroniques", "2 Chronicles", "2Tan", "2Chr", "2Chr", 36),
        BibleBookEntity(15, "OT", "Ezra", "Esdras", "Ezra", "Ezr", "Esd", "Ezra", 10),
        BibleBookEntity(16, "OT", "Nehemia", "Néhémie", "Nehemiah", "Neh", "Néh", "Neh", 13),
        BibleBookEntity(17, "OT", "Estera", "Esther", "Esther", "Est", "Est", "Esth", 10),
        BibleBookEntity(18, "OT", "Joba", "Job", "Job", "Job", "Job", "Job", 42),
        BibleBookEntity(19, "OT", "Salamo", "Psaumes", "Psalms", "Sal", "Ps", "Ps", 150),
        BibleBookEntity(20, "OT", "Ohabolana", "Proverbes", "Proverbs", "Ohab", "Prov", "Prov", 31),
        BibleBookEntity(21, "OT", "Mpitoriteny", "Ecclésiaste", "Ecclesiastes", "Mpitor", "Eccl", "Eccl", 12),
        BibleBookEntity(22, "OT", "Tononkiran'i Solomona", "Cantique des cantiques", "Song of Solomon", "Ton", "Cant", "Song", 8),
        BibleBookEntity(23, "OT", "Isaia", "Ésaïe", "Isaiah", "Isa", "És", "Isa", 66),
        BibleBookEntity(24, "OT", "Jeremia", "Jérémie", "Jeremiah", "Jer", "Jér", "Jer", 52),
        BibleBookEntity(25, "OT", "Fitomaniana", "Lamentations", "Lamentations", "Fit", "Lam", "Lam", 5),
        BibleBookEntity(26, "OT", "Ezekiela", "Ézéchiel", "Ezekiel", "Ezek", "Éz", "Ezek", 48),
        BibleBookEntity(27, "OT", "Daniela", "Daniel", "Daniel", "Dan", "Dan", "Dan", 12),
        BibleBookEntity(28, "OT", "Hosea", "Osée", "Hosea", "Hos", "Os", "Hos", 14),
        BibleBookEntity(29, "OT", "Joela", "Joël", "Joel", "Joel", "Joël", "Joel", 3),
        BibleBookEntity(30, "OT", "Amosa", "Amos", "Amos", "Amos", "Amos", "Amos", 9),
        BibleBookEntity(31, "OT", "Obadia", "Abdias", "Obadiah", "Obad", "Abd", "Obad", 1),
        BibleBookEntity(32, "OT", "Jona", "Jonas", "Jonah", "Jon", "Jon", "Jonah", 4),
        BibleBookEntity(33, "OT", "Mika", "Michée", "Micah", "Mik", "Mich", "Mic", 7),
        BibleBookEntity(34, "OT", "Nahoma", "Nahum", "Nahum", "Nah", "Nah", "Nah", 3),
        BibleBookEntity(35, "OT", "Habakoka", "Habacuc", "Habakkuk", "Hab", "Hab", "Hab", 3),
        BibleBookEntity(36, "OT", "Zefania", "Sophonie", "Zephaniah", "Zef", "Soph", "Zeph", 3),
        BibleBookEntity(37, "OT", "Hagay", "Aggée", "Haggai", "Hag", "Agg", "Hag", 2),
        BibleBookEntity(38, "OT", "Zakaria", "Zacharie", "Zechariah", "Zak", "Zach", "Zech", 14),
        BibleBookEntity(39, "OT", "Malakia", "Malachie", "Malachi", "Mal", "Mal", "Mal", 4),

        // Nouveau Testament (40-66)
        BibleBookEntity(40, "NT", "Matio", "Matthieu", "Matthew", "Mat", "Matt", "Matt", 28),
        BibleBookEntity(41, "NT", "Marka", "Marc", "Mark", "Mar", "Marc", "Mark", 16),
        BibleBookEntity(42, "NT", "Lioka", "Luc", "Luke", "Lio", "Luc", "Luke", 24),
        BibleBookEntity(43, "NT", "Jaona", "Jean", "John", "Jao", "Jean", "John", 21),
        BibleBookEntity(44, "NT", "Asan'ny Apostoly", "Actes des Apôtres", "Acts", "Asa", "Act", "Acts", 28),
        BibleBookEntity(45, "NT", "Romana", "Romains", "Romans", "Rom", "Rom", "Rom", 16),
        BibleBookEntity(46, "NT", "1 Korintiana", "1 Corinthiens", "1 Corinthians", "1Kor", "1Cor", "1Cor", 16),
        BibleBookEntity(47, "NT", "2 Korintiana", "2 Corinthiens", "2 Corinthians", "2Kor", "2Cor", "2Cor", 13),
        BibleBookEntity(48, "NT", "Galatiana", "Galates", "Galatians", "Gal", "Gal", "Gal", 6),
        BibleBookEntity(49, "NT", "Efesiana", "Éphésiens", "Ephesians", "Efe", "Éph", "Eph", 6),
        BibleBookEntity(50, "NT", "Filipiana", "Philippiens", "Philippians", "Fil", "Phil", "Phil", 4),
        BibleBookEntity(51, "NT", "Kolosiana", "Colossiens", "Colossians", "Kol", "Col", "Col", 4),
        BibleBookEntity(52, "NT", "1 Tesaloniana", "1 Thessaloniciens", "1 Thessalonians", "1Tes", "1Thess", "1Thess", 5),
        BibleBookEntity(53, "NT", "2 Tesaloniana", "2 Thessaloniciens", "2 Thessalonians", "2Tes", "2Thess", "2Thess", 3),
        BibleBookEntity(54, "NT", "1 Timoty", "1 Timothée", "1 Timothy", "1Tim", "1Tim", "1Tim", 6),
        BibleBookEntity(55, "NT", "2 Timoty", "2 Timothée", "2 Timothy", "2Tim", "2Tim", "2Tim", 4),
        BibleBookEntity(56, "NT", "Titosy", "Tite", "Titus", "Tit", "Tite", "Tit", 3),
        BibleBookEntity(57, "NT", "Filemona", "Philémon", "Philemon", "Filem", "Phm", "Phlm", 1),
        BibleBookEntity(58, "NT", "Hebreo", "Hébreux", "Hebrews", "Heb", "Héb", "Heb", 13),
        BibleBookEntity(59, "NT", "Jakoba", "Jacques", "James", "Jak", "Jacq", "Jas", 5),
        BibleBookEntity(60, "NT", "1 Petera", "1 Pierre", "1 Peter", "1Pet", "1Pi", "1Pet", 5),
        BibleBookEntity(61, "NT", "2 Petera", "2 Pierre", "2 Peter", "2Pet", "2Pi", "2Pet", 3),
        BibleBookEntity(62, "NT", "1 Jaona", "1 Jean", "1 John", "1Jao", "1Jean", "1John", 5),
        BibleBookEntity(63, "NT", "2 Jaona", "2 Jean", "2 John", "2Jao", "2Jean", "2John", 1),
        BibleBookEntity(64, "NT", "3 Jaona", "3 Jean", "3 John", "3Jao", "3Jean", "3John", 1),
        BibleBookEntity(65, "NT", "Joda", "Jude", "Jude", "Jod", "Jude", "Jude", 1),
        BibleBookEntity(66, "NT", "Apokalypsy", "Apocalypse", "Revelation", "Apok", "Apoc", "Rev", 22)
    )

    val verses = listOf(
        // Genesis 1:1-8 (Creation)
        // MBP
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 1, versionCode = "MBP", text = "Tamin'ny voalohany Andriamanitra nahary ny lanitra sy ny tany."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 2, versionCode = "MBP", text = "Ary ny tany dia tsy nisy endrika sady foana; ary aizina no tambonin'ny lalina; ary ny Fanahin'Andriamanitra nanomba teo ambonin'ny rano."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 3, versionCode = "MBP", text = "Ary Andriamanitra nanao hoe: Misià mazava; dia nisy mazava."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 4, versionCode = "MBP", text = "Ary hitan'Andriamanitra fa tsara ny mazava; ary nampisarahin'Andriamanitra ny mazava sy ny aizina."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 5, versionCode = "MBP", text = "Ary Andriamanitra nanao ny mazava hoe andro; ary ny aizina nataony hoe alina. Dia nisy hariva, ary nisy maraina, andro voalohany izany."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 6, versionCode = "MBP", text = "Ary Andriamanitra nanao hoe: Misià habakabaka eo anelanelan'ny rano, hizara ny rano amin'ny rano."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 7, versionCode = "MBP", text = "Ary Andriamanitra nanao ny habakabaka ka nampisaraka ny rano ambanin'ny habakabaka tamin'ny rano ambonin'ny habakabaka; dia nisy izany."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 8, versionCode = "MBP", text = "Ary Andriamanitra nanao ny habakabaka hoe lanitra. Dia nisy hariva, ary nisy maraina, andro faharoa izany."),

        // LSG1910
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 1, versionCode = "LSG1910", text = "Au commencement, Dieu créa les cieux et la terre."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 2, versionCode = "LSG1910", text = "La terre était informe et vide: il y avait des ténèbres à la surface de l'abîme, et l'esprit de Dieu se mouvait au-dessus des eaux."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 3, versionCode = "LSG1910", text = "Dieu dit: Que la lumière soit! Et la lumière fut."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 4, versionCode = "LSG1910", text = "Dieu vit que la lumière était bonne; et Dieu sépara la lumière d'avec les ténèbres."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 5, versionCode = "LSG1910", text = "Dieu appela la lumière jour, et il appela les ténèbres nuit. Ainsi, il y eut un soir, et il y eut un matin: ce fut le premier jour."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 6, versionCode = "LSG1910", text = "Dieu dit: Qu'il y ait une étendue entre les eaux, et qu'elle sépare les eaux d'avec les eaux."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 7, versionCode = "LSG1910", text = "Et Dieu fit l'étendue, et il sépara les eaux qui sont au-dessous de l'étendue d'avec les eaux qui sont au-dessus de l'étendue. Et cela fut ainsi."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 8, versionCode = "LSG1910", text = "Dieu appela l'étendue ciel. Ainsi, il y eut un soir, et il y eut un matin: ce fut le second jour."),

        // KJV
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 1, versionCode = "KJV", text = "In the beginning God created the heaven and the earth."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 2, versionCode = "KJV", text = "And the earth was without form, and void; and darkness was upon the face of the deep. And the Spirit of God moved upon the face of the waters."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 3, versionCode = "KJV", text = "And God said, Let there be light: and there was light."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 4, versionCode = "KJV", text = "And God saw the light, that it was good: and God divided the light from the darkness."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 5, versionCode = "KJV", text = "And God called the light Day, and the darkness he called Night. And the evening and the morning were the first day."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 6, versionCode = "KJV", text = "And God said, Let there be a firmament in the midst of the waters, and let it divide the waters from the waters."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 7, versionCode = "KJV", text = "And God made the firmament, and divided the waters which were under the firmament from the waters which were above the firmament: and it was so."),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 8, versionCode = "KJV", text = "And God called the firmament Heaven. And the evening and the morning were the second day."),

        // BHS Hebrew Gen 1:1-5
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 1, versionCode = "BHS", text = "בְּרֵאשִׁ֖ית בָּרָ֣א אֱלֹהִ֑ים אֵ֥ת הַשָּׁמַ֖יִם וְאֵ֥ת הָאָֽרֶץ׃"),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 2, versionCode = "BHS", text = "וְהָאָ֗רֶץ הָיְתָ֥ה תֹ֙הוּ֙ וָבֹ֔הוּ וְחֹ֖שֶׁךְ עַל־פְּנֵ֣י תְה֑וֹם וְר֣וּחַ אֱלֹהִ֔ים מְרַחֶ֖פֶת עַל־פְּנֵ֥י הַמָּֽיִם׃"),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 3, versionCode = "BHS", text = "וַיֹּ֥אמֶר אֱלֹהִ֖ים יְהִ֣י א֑וֹר וַֽיְהִי־אֽוֹר׃"),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 4, versionCode = "BHS", text = "וַיַּ֧רְא אֱלֹהִ֛ים אֶת־הָא֖וֹר כִּי־ט֑וֹב וַיַּבְדֵּ֣ל אֱלֹהִ֔ים בֵּ֥ין הָא֖וֹר וּבֵ֥ין הַחֹֽשֶׁךְ׃"),
        BibleVerseEntity(bookNumber = 1, chapter = 1, verse = 5, versionCode = "BHS", text = "וַיִּקְרָ֨א אֱלֹהִ֤ים ׀ לָאוֹר֙ י֔וֹם וְלַחֹ֖שֶׁךְ קָ֣רָא לָ֑יְלָה וַֽיְהִי־עֶ֥רֶב וַֽיְהִי־בֹ֖קֶר י֥וֹם אֶחָֽד׃ פ"),

        // Salamo 1:1-3 (Blessed man)
        // MBP
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 1, versionCode = "MBP", text = "Sambatra ny olona izay tsy mandeha eo amin'ny fisainan'ny ratsy fanahy, ary tsy mijanona eo amin'ny lalan'ny mpanota, ary tsy mipetraka eo amin'ny fipetrahan'ny mpaniratsira;"),
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 2, versionCode = "MBP", text = "Fa ny lalàn'i Jehovah no sitrany; eny, ny lalàny no saintsaininy andro aman'alina."),
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 3, versionCode = "MBP", text = "Dia tahaka ny hazo ambolena eo amoron'ny rano mandeha izy, izay mamoa amin'ny fotoany, ny raviny koa tsy mba malazo; ary ny asany rehetra dia hambinina avokoa."),
        // LSG1910
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 1, versionCode = "LSG1910", text = "Heureux l'homme qui ne marche pas selon le conseil des méchants, qui ne s'arrête pas sur la voie des pécheurs, et qui ne s'assied pas en compagnie des moqueurs,"),
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 2, versionCode = "LSG1910", text = "Mais qui trouve son plaisir dans la loi de l'Éternel, et qui la médite jour et nuit!"),
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 3, versionCode = "LSG1910", text = "Il est comme un arbre planté près d'un courant d'eau, qui donne son fruit en sa saison, et dont le feuillage ne se flétrit point: tout ce qu'il fait lui réussit."),
        // KJV
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 1, versionCode = "KJV", text = "Blessed is the man that walketh not in the counsel of the ungodly, nor standeth in the way of sinners, nor sitteth in the seat of the scornful."),
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 2, versionCode = "KJV", text = "But his delight is in the law of the LORD; and in his law doth he meditate day and night."),
        BibleVerseEntity(bookNumber = 19, chapter = 1, verse = 3, versionCode = "KJV", text = "And he shall be like a tree planted by the rivers of water, that bringeth forth his fruit in his season; his leaf also shall not wither; and whatsoever he doeth shall prosper."),

        // Psaume 23:1-6 (Complete Shepherd Psalm)
        // MBP
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 1, versionCode = "MBP", text = "Jehovah no Mpiandry ahy, tsy hanan-java-mahory aho."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 2, versionCode = "MBP", text = "Mampandry ahy amin'ny ahi-maitso Izy; Mitondra ahy eo amoron'ny rano fialan-tsasatra Izy."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 3, versionCode = "MBP", text = "Mamelombelona ny fanahiko Izy; Mitarika ahy amin'ny lalan'ny fahamarinana noho ny anarany Izy."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 4, versionCode = "MBP", text = "Eny, na dia mandeha mamaky ny lohasaha aloky ny fahafatesana aza aho, dia tsy hatahotra ny loza aho, satria Hianao no amiko; Ny tsorakazonao sy ny tehinao, ireo no mampionona ahy."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 5, versionCode = "MBP", text = "Mamelatra latabatra eo anoloako eo anatrehan'ny fahavaloko Hianao; Manosotra diloilo ny lohako Hianao; Feno mandriaka ny kapoakako."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 6, versionCode = "MBP", text = "Eny tokoa, fahasoavana sy famindram-po no hanaraka ahy amin'ny andro rehetra hiainako; Ary hitoetra ao an-tranon'i Jehovah andro lava aho."),

        // LSG1910
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 1, versionCode = "LSG1910", text = "Cantique de David. L'Éternel est mon berger: je ne manquerai de rien."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 2, versionCode = "LSG1910", text = "Il me fait reposer dans de verts pâturages, Il me dirige près des eaux paisibles."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 3, versionCode = "LSG1910", text = "Il restaure mon âme, Il me conduit dans les sentiers de la justice, À cause de son nom."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 4, versionCode = "LSG1910", text = "Quand je marche dans la vallée de l'ombre de la mort, Je ne crains aucun mal, car tu es avec moi: Ta houlette et ton bâton me rassurent."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 5, versionCode = "LSG1910", text = "Tu dresses devant moi une table, en face de mes adversaires; Tu oins d'huile ma tête, et ma coupe déborde."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 6, versionCode = "LSG1910", text = "Oui, le bonheur et la grâce m'accompagneront tous les jours de ma vie, et j'habiterai dans la maison de l'Éternel jusqu'à la fin de mes jours."),

        // KJV
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 1, versionCode = "KJV", text = "The LORD is my shepherd; I shall not want."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 2, versionCode = "KJV", text = "He maketh me to lie down in green pastures: he leadeth me beside the still waters."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 3, versionCode = "KJV", text = "He restoreth my soul: he leadeth me in the paths of righteousness for his name's sake."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 4, versionCode = "KJV", text = "Yea, though I walk through the valley of the shadow of death, I will fear no evil: for thou art with me; thy rod and thy staff they comfort me."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 5, versionCode = "KJV", text = "Thou preparest a table before me in the presence of mine enemies: thou anointest my head with oil; my cup runneth over."),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 6, versionCode = "KJV", text = "Surely goodness and mercy shall follow me all the days of my life: and I will dwell in the house of the LORD for ever."),

        // BHS Hebrew Psalm 23:1-3
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 1, versionCode = "BHS", text = "מִזְמ֥וֹר לְדָוִ֑ד יְהוָ֥ה רֹ֝עִ֗י לֹ֣א אֶחְסָֽר׃"),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 2, versionCode = "BHS", text = "בִּנְא֣וֹת דֶּ֭שֶׁא יַרְבִּיצֵ֑נִי עַל־מֵ֖י מְנֻח֣וֹת יְנַהֲלֵֽנִי׃"),
        BibleVerseEntity(bookNumber = 19, chapter = 23, verse = 3, versionCode = "BHS", text = "נַפְשִׁ֥י יְשׁוֹבֵ֑ב יַֽנְחֵ֥נִי בְמַעְגְּלֵי־צֶ֝֗דֶק לְמַ֣עַן שְׁמֽוֹ׃"),

        // Matio 5:1-6 (Beatitudes)
        // MBP
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 1, versionCode = "MBP", text = "Ary nony nahita ny vahoaka Jesosy, dia niakatra teo an-tendrombohitra Izy; ary rehefa tafapetraka Izy, dia nanatona Azy ny mpianany."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 2, versionCode = "MBP", text = "Dia niloa-bava Izy ka nampianatra azy hoe:"),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 3, versionCode = "MBP", text = "Sambatra ny malahelo am-panahy; fa azy ny fanjakan'ny lanitra."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 4, versionCode = "MBP", text = "Sambatra ny ory; fa izy no hampiononina."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 5, versionCode = "MBP", text = "Sambatra ny malemy fanahy; fa izy no handova ny tany."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 6, versionCode = "MBP", text = "Sambatra ny noana sy mangetaheta ny fahamarinana; fa izy no hovokisana."),

        // LSG1910
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 1, versionCode = "LSG1910", text = "Voyant la foule, Jésus monta sur la montagne; et, après qu'il se fut assis, ses disciples s'approchèrent de lui."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 2, versionCode = "LSG1910", text = "Puis, ayant ouvert la bouche, il les enseigna, et dit:"),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 3, versionCode = "LSG1910", text = "Heureux les pauvres en esprit, car le royaume des cieux est à eux!"),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 4, versionCode = "LSG1910", text = "Heureux les affligés, car ils seront consolés!"),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 5, versionCode = "LSG1910", text = "Heureux les débonnaires, car ils hériteront la terre!"),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 6, versionCode = "LSG1910", text = "Heureux ceux qui ont faim et soif de la justice, car ils seront rassasiés!"),

        // KJV
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 1, versionCode = "KJV", text = "And seeing the multitudes, he went up into a mountain: and when he was set, his disciples came unto him:"),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 2, versionCode = "KJV", text = "And he opened his mouth, and taught them, saying,"),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 3, versionCode = "KJV", text = "Blessed are the poor in spirit: for theirs is the kingdom of heaven."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 4, versionCode = "KJV", text = "Blessed are they that mourn: for they shall be comforted."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 5, versionCode = "KJV", text = "Blessed are the meek: for they shall inherit the earth."),
        BibleVerseEntity(bookNumber = 40, chapter = 5, verse = 6, versionCode = "KJV", text = "Blessed are they which do hunger and thirst after righteousness: for they shall be filled."),

        // Matio 6:9-13 (Vavaka nampianarin'ny Tompo / Notre Père / Lord's Prayer)
        // MBP
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 9, versionCode = "MBP", text = "Koa amin'izany mivavaha toy izao ianareo: Rainay Izay any an-danitra, Hanamasinana ny anaranao anie."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 10, versionCode = "MBP", text = "Ho tonga anie ny fanjakanao. Hatao anie ny sitraponao etỳ an-tany tahaka ny any an-danitra."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 11, versionCode = "MBP", text = "Omeo anay anio izay hanina sahaza ho anay."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 12, versionCode = "MBP", text = "Ary mamelà ny helokay tahaka ny namelanay izay meloka taminay."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 13, versionCode = "MBP", text = "Ary aza mitondra anay ho amin'ny fakam-panahy, fa manafaha anay amin'ny ratsy; fa Anao ny fanjakana sy ny hery ary ny voninahitra mandrakizay. Amena."),

        // LSG1910
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 9, versionCode = "LSG1910", text = "Voici donc comment vous devez prier: Notre Père qui es aux cieux! Que ton nom soit sanctifié;"),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 10, versionCode = "LSG1910", text = "Que ton règne vienne; que ta volonté soit faite sur la terre comme au ciel."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 11, versionCode = "LSG1910", text = "Donne-nous aujourd'hui notre pain quotidien;"),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 12, versionCode = "LSG1910", text = "Pardonne-nous nos offenses, comme nous aussi nous pardonnons à ceux qui nous ont offensés;"),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 13, versionCode = "LSG1910", text = "Ne nous induis pas en tentation, mais délivre-nous du malin. Car c'est à toi qu'appartiennent, dans tous les siècles, le règne, la puissance et la gloire. Amen!"),

        // KJV
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 9, versionCode = "KJV", text = "After this manner therefore pray ye: Our Father which art in heaven, Hallowed be thy name."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 10, versionCode = "KJV", text = "Thy kingdom come. Thy will be done in earth, as it is in heaven."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 11, versionCode = "KJV", text = "Give us this day our daily bread."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 12, versionCode = "KJV", text = "And forgive us our debts, as we forgive our debtors."),
        BibleVerseEntity(bookNumber = 40, chapter = 6, verse = 13, versionCode = "KJV", text = "And lead us not into temptation, but deliver us from evil: For thine is the kingdom, and the power, and the glory, for ever. Amen."),

        // Jean 1:1-14
        // MBP
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 1, versionCode = "MBP", text = "Tamin'ny voalohany ny Teny, ary ny Teny tao amin'Andriamanitra, ary ny Teny dia Andriamanitra."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 2, versionCode = "MBP", text = "Izy tamin'ny voalohany tao amin'Andriamanitra."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 3, versionCode = "MBP", text = "Izy no nahariana ny zavatra rehetra; ary raha tsy Izy dia tsy nisy nahariana na inona na inona izay efa ary."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 4, versionCode = "MBP", text = "Tao aminy ny fiainana; ary ny fiainana no fahazavan'ny olona."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 5, versionCode = "MBP", text = "Ary ny mazava mahazava ao amin'ny maizina; fa ny maizina tsy nandray azy."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 14, versionCode = "MBP", text = "Ary ny Teny dia tonga nofo ka nonina tamintsika; ary hitantsika ny voninahiny, dia voninahitra mendrika ho an'ny Lahitokan'ny Ray, feno fahasoavana sy fahamarinana."),

        // LSG1910
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 1, versionCode = "LSG1910", text = "Au commencement était la Parole, et la Parole était avec Dieu, et la Parole était Dieu."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 2, versionCode = "LSG1910", text = "Elle était au commencement avec Dieu."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 3, versionCode = "LSG1910", text = "Toutes choses ont été faites par elle, et rien de ce qui a été fait n'a été fait sans elle."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 4, versionCode = "LSG1910", text = "En elle était la vie, et la vie était la lumière des hommes."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 5, versionCode = "LSG1910", text = "La lumière luit dans les ténèbres, et les ténèbres ne l'ont point reçue."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 14, versionCode = "LSG1910", text = "Et la parole a été faite chair, et elle a habité parmi nous, pleine de grâce et de vérité; et nous avons contemplé sa gloire, une gloire comme la gloire du Fils unique venu du Père."),

        // KJV
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 1, versionCode = "KJV", text = "In the beginning was the Word, and the Word was with God, and the Word was God."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 2, versionCode = "KJV", text = "The same was in the beginning with God."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 3, versionCode = "KJV", text = "All things were made by him; and without him was not any thing made that was made."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 4, versionCode = "KJV", text = "In him was life; and the life was the light of men."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 5, versionCode = "KJV", text = "And the light shineth in darkness; and the darkness comprehended it not."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 14, versionCode = "KJV", text = "And the Word was made flesh, and dwelt among us, (and we beheld his glory, the glory as of the only begotten of the Father,) full of grace and truth."),

        // NA28 Greek John 1:1-5, 14
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 1, versionCode = "NA28", text = "Ἐν ἀρχῇ ἦν ὁ λόγος, καὶ ὁ λόγος ἦν πρὸς τὸν θεόν, καὶ θεὸς ἦν ὁ λόγος."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 2, versionCode = "NA28", text = "οὗτος ἦν ἐν ἀρχῇ πρὸς τὸν θεόν."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 3, versionCode = "NA28", text = "πάντα δι' αὐτοῦ ἐγένετο, καὶ χωρὶς αὐτοῦ ἐγένετο οὐδὲ ἕν ὃ γέγονεν."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 4, versionCode = "NA28", text = "ἐν αὐτῷ ζωὴ ἦν, καὶ ἡ ζωὴ ἦν τὸ φῶς τῶν ἀνθρώπων·"),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 5, versionCode = "NA28", text = "καὶ τὸ φῶς ἐν τῇ σκοτίᾳ φαίνει, καὶ ἡ σκοτία αὐτὸ οὐ κατέλαβεν."),
        BibleVerseEntity(bookNumber = 43, chapter = 1, verse = 14, versionCode = "NA28", text = "Καὶ ὁ λόγος σὰρξ ἐγένετο καὶ ἐσκήνωσεν ἐν ἡμῖν, καὶ ἐθεασάμεθα τὴν δόξαν αὐτοῦ, δόξαν ὡς μονογενοῦς παρὰ πατρός, πλήρης χάριτος καὶ ἀληθείας."),

        // John 3:16-17
        // MBP
        BibleVerseEntity(bookNumber = 43, chapter = 3, verse = 16, versionCode = "MBP", text = "Fa toy izany no nitiavan'Andriamanitra izao tontolo izao: nomeny ny Zanani-lahy Tokana, mba tsy ho very izay rehetra mino Azy, fa hanana fiainana mandrakizay."),
        BibleVerseEntity(bookNumber = 43, chapter = 3, verse = 17, versionCode = "MBP", text = "Fa Andriamanitra tsy naniraka ny Zanaka ho amin'izao tontolo izao hanameloka izao tontolo izao, fa mba hamonjena izao tontolo izao amin'ny alalany."),
        // LSG1910
        BibleVerseEntity(bookNumber = 43, chapter = 3, verse = 16, versionCode = "LSG1910", text = "Car Dieu a tant aimé le monde qu'il a donné son Fils unique, afin que quiconque croit en lui ne périsse point, mais qu'il ait la vie éternelle."),
        BibleVerseEntity(bookNumber = 43, chapter = 3, verse = 17, versionCode = "LSG1910", text = "Dieu, en effet, n'a pas envoyé son Fils dans le monde pour qu'il juge le monde, mais pour que le monde soit sauvé par lui."),
        // KJV
        BibleVerseEntity(bookNumber = 43, chapter = 3, verse = 16, versionCode = "KJV", text = "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life."),
        BibleVerseEntity(bookNumber = 43, chapter = 3, verse = 17, versionCode = "KJV", text = "For God sent not his Son into the world to condemn the world; but that the world through him might be saved."),

        // Romana 8:1-2, 28, 31, 38-39
        // MBP
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 1, versionCode = "MBP", text = "Koa amin'izany dia tsy misy fanamelohana ho an'izay ao amin'i Kristy Jesosy."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 2, versionCode = "MBP", text = "Fa ny lalàn'ny Fanahin'aina ao amin'i Kristy Jesosy no nahafaka ahy tamin'ny lalàn'ny fahotana sy ny fahafatesana."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 28, versionCode = "MBP", text = "Ary fantatsika fa ny zavatra rehetra dia miara-miasa hahasoa izay tia an'Andriamanitra, dia izay voantso araka ny fikasany."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 31, versionCode = "MBP", text = "Inona ary no holaintsika ny amin'izany zavatra izany? Raha Andriamanitra no momba antsika, iza no hahatohitra antsika?"),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 38, versionCode = "MBP", text = "Fa matoky aho fa na fahafatesana, na fiainana, na anjely, na andriana, na zavatra ankehitriny, na zavatra ho avy, na hery,"),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 39, versionCode = "MBP", text = "na ny ambony, na ny ambany, na inona na inona amin'izao zavatra ary rehetra izao, dia tsy hahasaraka antsika amin'ny fitiavan'Andriamanitra izay ao amin'i Kristy Jesosy Tompontsika."),

        // LSG1910
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 1, versionCode = "LSG1910", text = "Il n'y a donc maintenant aucune condamnation pour ceux qui sont en Jésus-Christ."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 2, versionCode = "LSG1910", text = "En effet, la loi de l'esprit de vie en Jésus-Christ m'a affranchi de la loi du péché et de la mort."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 28, versionCode = "LSG1910", text = "Nous savons, du reste, que toutes choses concourent au bien de ceux qui aiment Dieu, de ceux qui sont appelés selon son dessein."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 31, versionCode = "LSG1910", text = "Que dirons-nous donc à l'égard de ces choses? Si Dieu est pour nous, qui sera contre nous?"),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 38, versionCode = "LSG1910", text = "Car j'ai l'assurance que ni la mort ni la vie, ni les anges ni les dominations, ni les choses présentes ni les choses à venir,"),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 39, versionCode = "LSG1910", text = "ni les puissances, ni la hauteur, ni la profondeur, ni aucune autre créature ne pourra nous séparer de l'amour de Dieu manifesté en Jésus-Christ notre Seigneur."),

        // KJV
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 1, versionCode = "KJV", text = "There is therefore now no condemnation to them which are in Christ Jesus, who walk not after the flesh, but after the Spirit."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 2, versionCode = "KJV", text = "For the law of the Spirit of life in Christ Jesus hath made me free from the law of sin and death."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 28, versionCode = "KJV", text = "And we know that all things work together for good to them that love God, to them who are the called according to his purpose."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 31, versionCode = "KJV", text = "What shall we then say to these things? If God be for us, who can be against us?"),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 38, versionCode = "KJV", text = "For I am persuaded, that neither death, nor life, nor angels, nor principalities, nor powers, nor things present, nor things to come,"),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 39, versionCode = "KJV", text = "Nor height, nor depth, nor any other creature, shall be able to separate us from the love of God, which is in Christ Jesus our Lord."),

        // NA28 Greek Romans 8:28, 31
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 28, versionCode = "NA28", text = "Οἴδαμεν δὲ ὅτι τοῖς ἀγαπῶσιν τὸν θεὸν πάντα συνεργεῖ εἰς ἀγαθόν, τοῖς κατὰ πρόθεσιν κλητοῖς οὖσιν."),
        BibleVerseEntity(bookNumber = 45, chapter = 8, verse = 31, versionCode = "NA28", text = "Τί οὖν ἐροῦμεν πρὸς ταῦτα; εἰ ὁ θεὸς ὑπὲρ ἡμῶν, τίς καθ' ἡμῶν;"),

        // Efesiana 2:8-10 (Grace and Faith)
        // MBP
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 8, versionCode = "MBP", text = "Fa fahasoavana no namonjena anareo amin'ny finoana; ary tsy avy aminareo izany, fa fanomezan'Andriamanitra;"),
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 9, versionCode = "MBP", text = "tsy avy amin'ny asa, fandrao hisy hirehareha."),
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 10, versionCode = "MBP", text = "Fa asany isika, voaforona tao amin'i Kristy Jesosy mba hanao asa tsara, izay namboarin'Andriamanitra mialoha mba handehanantsika eo aminy."),
        // LSG1910
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 8, versionCode = "LSG1910", text = "Car c'est par la grâce que vous êtes sauvés, par le moyen de la foi. Et cela ne vient pas de vous, c'est le don de Dieu."),
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 9, versionCode = "LSG1910", text = "Ce n'est point par les œuvres, afin que personne ne se glorifie."),
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 10, versionCode = "LSG1910", text = "Car nous sommes son ouvrage, ayant été créés en Jésus-Christ pour de bonnes œuvres, que Dieu a préparées d'avance, afin que nous les pratiquions."),
        // KJV
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 8, versionCode = "KJV", text = "For by grace are ye saved through faith; and that not of yourselves: it is the gift of God:"),
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 9, versionCode = "KJV", text = "Not of works, lest any man should boast."),
        BibleVerseEntity(bookNumber = 49, chapter = 2, verse = 10, versionCode = "KJV", text = "For we are his workmanship, created in Christ Jesus unto good works, which God hath before ordained that we should walk in them."),

        // 1 Korintiana 13:4-8, 13 (Love / Fitiavana)
        // MBP
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 4, versionCode = "MBP", text = "Ny fitiavana maharitra ela sady mora fanahy; ny fitiavana tsy mialona, ny fitiavana tsy mirehareha, tsy mieboebo,"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 5, versionCode = "MBP", text = "tsy manao izay tsy mahamendrika, tsy mitady ny azy, tsy mora sosotra, tsy manao ratsy ho eritreritra,"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 6, versionCode = "MBP", text = "tsy mifaly amin'ny tsy fahamarinana, fa miara-mifaly amin'ny fahamarinana kosa,"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 7, versionCode = "MBP", text = "mandefitra ny zavatra rehetra, mino ny zavatra rehetra, manantena ny zavatra rehetra, maharitra ny zavatra rehetra."),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 8, versionCode = "MBP", text = "Ny fitiavana tsy ho levona mandrakizay; fa na ny faminaniana aza dia ho foana; na ny fiteny maro dia hitsahatra; na ny fahalalana dia ho foana."),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 13, versionCode = "MBP", text = "Ary ankehitriny dia ireto telo ireto no mitoetra: ny finoana, ny fanantenana, ny fitiavana; fa ny fitiavana no lehibe indrindra amin'ireo."),
        // LSG1910
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 4, versionCode = "LSG1910", text = "La charité est patiente, elle est pleine de bonté; la charité n'est point envieuse; la charité ne se vante point, elle ne s'enfle point d'orgueil,"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 5, versionCode = "LSG1910", text = "elle ne fait rien de malhonnête, elle ne cherche point son intérêt, elle ne s'irrite point, elle ne soupçonne point le mal,"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 6, versionCode = "LSG1910", text = "elle ne se réjouit point de l'injustice, mais elle se réjouit de la vérité;"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 7, versionCode = "LSG1910", text = "elle excuse tout, elle croit tout, elle espère tout, elle supporte tout."),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 8, versionCode = "LSG1910", text = "La charité ne périt jamais. Les prophéties prendront fin, les langues cesseront, la connaissance disparaîtra."),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 13, versionCode = "LSG1910", text = "Maintenant donc ces trois choses demeurent: la foi, l'espérance, la charité; mais la plus grande de ces choses, c'est la charité."),
        // KJV
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 4, versionCode = "KJV", text = "Charity suffereth long, and is kind; charity envieth not; charity vaunteth not itself, is not puffed up,"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 5, versionCode = "KJV", text = "Doth not behave itself unseemly, seeketh not her own, is not easily provoked, thinketh no evil;"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 6, versionCode = "KJV", text = "Rejoiceth not in iniquity, but rejoiceth in the truth;"),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 7, versionCode = "KJV", text = "Beareth all things, believeth all things, hopeth all things, endureth all things."),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 8, versionCode = "KJV", text = "Charity never faileth: but whether there be prophecies, they shall fail; whether there be tongues, they shall cease; whether there be knowledge, it shall vanish away."),
        BibleVerseEntity(bookNumber = 46, chapter = 13, verse = 13, versionCode = "KJV", text = "And now abideth faith, hope, charity, these three; but the greatest of these is charity."),

        // Apokalypsy 21:1-4 (New Heaven and New Earth)
        // MBP
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 1, versionCode = "MBP", text = "Ary nahita lanitra vaovao sy tany vaovao aho; fa efa lasa ny lanitra voalohany sy ny tany voalohany, ary ny ranomasina dia tsy misy intsony."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 2, versionCode = "MBP", text = "Ary nahita ny tanàna masina aho, dia Jerosalema vaovao, nidina avy any an-danitra tamin'Andriamanitra, voaomana tahaka ny ampakarina voaravaka ho an'ny lahy."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 3, versionCode = "MBP", text = "Ary nahare feo mahery avy teo amin'ny seza fiandrianana aho nanao hoe: Indro, ny tabernakelin'Andriamanitra dia eo amin'ny olona, ary Izy hitoetra eo aminy, ary ireo ho olony; ary Andriamanitra no hitoetra eo aminy, dia Andriamaniny."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 4, versionCode = "MBP", text = "Ary hofafany ny ranomaso rehetra amin'ny masony; ary tsy hisy fahafatesana intsony, sady tsy hisy alahelo, na fitomaniana, na fanaintainana intsony; fa efa lasa ny zavatra taloha."),
        // LSG1910
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 1, versionCode = "LSG1910", text = "Puis je vis un nouveau ciel et une nouvelle terre; car le premier ciel et la première terre avaient disparu, et la mer n'était plus."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 2, versionCode = "LSG1910", text = "Et je vis descendre du ciel, d'auprès de Dieu, la ville sainte, la nouvelle Jérusalem, préparée comme une épouse qui s'est parée pour son époux."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 3, versionCode = "LSG1910", text = "Et j'entendis du trône une forte voix qui disait: Voici le tabernacle de Dieu avec les hommes! Il habitera avec eux, et ils seront son peuple, et Dieu lui-même sera avec eux."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 4, versionCode = "LSG1910", text = "Il essuiera toute larme de leurs yeux, et la mort ne sera plus, et il n'y aura plus ni deuil, ni cri, ni douleur, car les premières choses ont disparu."),
        // KJV
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 1, versionCode = "KJV", text = "And I saw a new heaven and a new earth: for the first heaven and the first earth were passed away; and there was no more sea."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 2, versionCode = "KJV", text = "And I John saw the holy city, new Jerusalem, coming down from God out of heaven, prepared as a bride adorned for her husband."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 3, versionCode = "KJV", text = "And I heard a great voice out of heaven saying, Behold, the tabernacle of God is with men, and he will dwell with them, and they shall be his people, and God himself shall be with them, and be their God."),
        BibleVerseEntity(bookNumber = 66, chapter = 21, verse = 4, versionCode = "KJV", text = "And God shall wipe away all tears from their eyes; and there shall be no more death, neither sorrow, nor crying, neither shall there be any more pain: for the former things are passed away.")
    )

    val originalWords = listOf(
        // Gen 1:1 Hebrew (RTL)
        OriginalWordEntity(
            bookNumber = 1, chapter = 1, verse = 1, wordIndex = 1,
            originalText = "בְּרֵאשִׁית",
            transliteration = "bə-rê-šîṯ",
            lemma = "רֵאשִׁית (reshith)",
            strongNumber = "H7225",
            language = "HEBREW",
            morphologyCode = "Prep-b | N-fs",
            morphologyDesc = "Préposition בְּ (dans, au) + Nom féminin singulier absolu (commencement)",
            definition = "Au commencement, point d'origine absolu du temps et du cosmos.",
            theologicalNote = "Indique que Dieu préexiste au temps. Jean 1:1 reprend ce concept en révélant que le Logos était là.",
            isRtl = true
        ),
        OriginalWordEntity(
            bookNumber = 1, chapter = 1, verse = 1, wordIndex = 2,
            originalText = "בָּרָא",
            transliteration = "bā-rā",
            lemma = "בָּרָא (bara)",
            strongNumber = "H1254",
            language = "HEBREW",
            morphologyCode = "V-Qal-Perf-3ms",
            morphologyDesc = "Verbe racine Qal, parfait (aspect accompli), 3e personne masculin singulier",
            definition = "Créer à partir de rien (creatio ex nihilo). Utilisé dans la Bible exclusivement avec Dieu comme sujet.",
            theologicalNote = "Aucun être humain ne peut 'bara'. Seul Dieu appelle à l'existence ce qui n'existait point.",
            isRtl = true
        ),
        OriginalWordEntity(
            bookNumber = 1, chapter = 1, verse = 1, wordIndex = 3,
            originalText = "אֱלֹהִים",
            transliteration = "’ĕ-lō-hîm",
            lemma = "אֱלוֹהַּ (Eloah / Elohim)",
            strongNumber = "H430",
            language = "HEBREW",
            morphologyCode = "N-mp",
            morphologyDesc = "Nom masculin pluriel de majesté et plénitude divine",
            definition = "Dieu Créateur suprême, plénitude de la divinité.",
            theologicalNote = "Forme plurielle avec un verbe singulier (bara), préfigurant la communion trinitaire du Dieu unique.",
            isRtl = true
        ),
        OriginalWordEntity(
            bookNumber = 1, chapter = 1, verse = 1, wordIndex = 4,
            originalText = "אֵת",
            transliteration = "’êṯ",
            lemma = "אֵת (eth)",
            strongNumber = "H853",
            language = "HEBREW",
            morphologyCode = "Dir-Obj-Marker",
            morphologyDesc = "Particule marquant le complément d'objet direct défini (non traduite)",
            definition = "Signe de l'accusatif hébreu.",
            theologicalNote = "Désigne avec précision l'objet de l'action divine : le ciel et la terre.",
            isRtl = true
        ),
        OriginalWordEntity(
            bookNumber = 1, chapter = 1, verse = 1, wordIndex = 5,
            originalText = "הַשָּׁמַיִם",
            transliteration = "haš-šā-ma-yim",
            lemma = "שָׁמַיִם (shamayim)",
            strongNumber = "H8064",
            language = "HEBREW",
            morphologyCode = "Art | N-md",
            morphologyDesc = "Article défini הַ + Nom masculin duel/pluriel",
            definition = "Les cieux, l'étendue céleste, la sphère supérieure.",
            theologicalNote = "Désigne l'univers invisible et les corps célestes créés par la seule Parole.",
            isRtl = true
        ),
        OriginalWordEntity(
            bookNumber = 1, chapter = 1, verse = 1, wordIndex = 6,
            originalText = "וְאֵת",
            transliteration = "wə-’êṯ",
            lemma = "וְ + אֵת",
            strongNumber = "H853",
            language = "HEBREW",
            morphologyCode = "Conj-w | Dir-Obj-Marker",
            morphologyDesc = "Conjonction 'et' + Particule de l'objet direct défini",
            definition = "Et [l'objet direct qui suit].",
            theologicalNote = "Lie le monde invisible céleste et le monde physique terrestre dans l'ordonnance divine.",
            isRtl = true
        ),
        OriginalWordEntity(
            bookNumber = 1, chapter = 1, verse = 1, wordIndex = 7,
            originalText = "הָאָרֶץ",
            transliteration = "hā-’ā-reṣ",
            lemma = "אֶרֶץ (erets)",
            strongNumber = "H776",
            language = "HEBREW",
            morphologyCode = "Art | N-fs",
            morphologyDesc = "Article défini + Nom féminin singulier avec allongement de pause",
            definition = "La terre, le globe terrestre habité.",
            theologicalNote = "Le théâtre de l'histoire du salut et de la rédemption en Jésus-Christ.",
            isRtl = true
        ),

        // Jean 1:1 Greek
        OriginalWordEntity(
            bookNumber = 43, chapter = 1, verse = 1, wordIndex = 1,
            originalText = "Ἐν",
            transliteration = "En",
            lemma = "ἐν (en)",
            strongNumber = "G1722",
            language = "GREEK",
            morphologyCode = "Prep",
            morphologyDesc = "Préposition régissant le datif (dans, en)",
            definition = "Dans, au sein de, marquant une sphère d'état ou de temps.",
            theologicalNote = "Fait écho direct au Bereshit de la Genèse.",
            isRtl = false
        ),
        OriginalWordEntity(
            bookNumber = 43, chapter = 1, verse = 1, wordIndex = 2,
            originalText = "ἀρχῇ",
            transliteration = "archē",
            lemma = "ἀρχή (archē)",
            strongNumber = "G746",
            language = "GREEK",
            morphologyCode = "N-DSF",
            morphologyDesc = "Nom commun, Datif Singulier Féminin",
            definition = "Commencement, principe premier, origine intemporelle.",
            theologicalNote = "Sans article en grec pour souligner la qualité de ce commencement absolu : avant toute création.",
            isRtl = false
        ),
        OriginalWordEntity(
            bookNumber = 43, chapter = 1, verse = 1, wordIndex = 3,
            originalText = "ἦν",
            transliteration = "ēn",
            lemma = "εἰμί (eimi)",
            strongNumber = "G1510",
            language = "GREEK",
            morphologyCode = "V-IAI-3S",
            morphologyDesc = "Verbe indicatif imparfait actif, 3e personne du singulier",
            definition = "Était continuellement, existait.",
            theologicalNote = "L'imparfait indique une existence continue sans commencement dans le temps : le Fils n'a pas été créé, Il existait déjà.",
            isRtl = false
        ),
        OriginalWordEntity(
            bookNumber = 43, chapter = 1, verse = 1, wordIndex = 4,
            originalText = "ὁ",
            transliteration = "ho",
            lemma = "ὁ (ho)",
            strongNumber = "G3588",
            language = "GREEK",
            morphologyCode = "Art-NMS",
            morphologyDesc = "Article défini, Nominatif Masculin Singulier",
            definition = "Le, la [personne bien définie].",
            theologicalNote = "Identifie précisément la Personne du Verbe.",
            isRtl = false
        ),
        OriginalWordEntity(
            bookNumber = 43, chapter = 1, verse = 1, wordIndex = 5,
            originalText = "λόγος",
            transliteration = "logos",
            lemma = "λόγος (logos)",
            strongNumber = "G3056",
            language = "GREEK",
            morphologyCode = "N-NMS",
            morphologyDesc = "Nom, Nominatif Masculin Singulier",
            definition = "La Parole, le Verbe, la pensée divine incarnée et révélatrice.",
            theologicalNote = "Jésus-Christ est l'expression parfaite, éternelle et vivante du Père. 'Mihainoa ny Teny' tire sa racine de cette réalité.",
            isRtl = false
        ),
        OriginalWordEntity(
            bookNumber = 43, chapter = 1, verse = 1, wordIndex = 6,
            originalText = "καὶ",
            transliteration = "kai",
            lemma = "καί (kai)",
            strongNumber = "G2532",
            language = "GREEK",
            morphologyCode = "Conj",
            morphologyDesc = "Conjonction copulative (et)",
            definition = "Et, aussi, de plus.",
            theologicalNote = "Relie l'éternité du Logos à Sa relation personnelle intime avec le Père.",
            isRtl = false
        ),
        OriginalWordEntity(
            bookNumber = 43, chapter = 1, verse = 1, wordIndex = 7,
            originalText = "θεὸς",
            transliteration = "theos",
            lemma = "θεός (theos)",
            strongNumber = "G2316",
            language = "GREEK",
            morphologyCode = "N-NMS",
            morphologyDesc = "Nom prédicat au nominatif masculin singulier (anarthre)",
            definition = "Dieu par nature, divin dans son essence même.",
            theologicalNote = "Règle de Colwell : le prédicat 'theos' placé avant le verbe sans article souligne la nature divine du Logos tout en le distinguant de la Personne du Père (ton theon).",
            isRtl = false
        )
    )

    val publications = listOf(
        PublicationEntity(
            title = "Ny Hery sy ny Fahefan'ny Tenin'Andriamanitra",
            summary = "Fandalinana lalina ny amin'ny herin'ny Tenin'Andriamanitra izay manova fiainana, manitsy, ary manome hery ny mpino ao anatin'ny fisedrana.",
            fullText = """
                # Ny Hery sy ny Fahefan'ny Tenin'Andriamanitra
                *Mpitondra hafatra : Solohery RAKOTOARISOA*
                *Andininy fototra : Hebreo 4:12 ; 2 Timoty 3:16-17*

                ## Fampidirana
                Tsy boky tsotra tahaka ny boky rehetra ny Baiboly. Ny Tenin'Andriamanitra dia Teny velona, miasa, ary manana fahefana mahagaga hanova ny fontsika sy ny fiarahamonintsika. Rehefa manokatra ny Baiboly isika, dia tsy fitantarana taloha fotsiny no vakintsika, fa ny feon'Ilay Andriamanitra velona izay miteny amintsika ankehitriny.

                ## I. Ny toetran'ny Tenin'Andriamanitra
                Araka ny voasoratra ao amin'ny Hebreo 4:12 : « Fa velona sy mahery ny tenin'Andriamanitra ka maranitra noho ny sabatra roa lela ».
                1. **Velona (Zôn)** : Tsy teny maty na fomba amam-panao lany andro, fa manana aina sy mamelona ny fanahy torotoro.
                2. **Mahery (Energês)** : Manana hery miasa mivaingana. Rehefa miteny Andriamanitra, dia mihetsika ny zavatra rehetra.
                3. **Maranitra** : Mahay mamantatra ny hevitry ny fo sy ny fisainan'ny olona. Tsy misy takona eo anatrehany.

                ## II. Ny andraikitry ny Teny eo amin'ny fiainan'ny mpino
                Araka ny 2 Timoty 3:16 : « Izay soratra rehetra nomen'ny tsindrimandrin'Andriamanitra dia mahasoa koa ho fampianarana, ho fandresen-dahatra, ho fanitsiana izay diso, ho fitaizana amin'ny fahamarinana ».
                - Mampianatra antsika ny sitrapon'Andriamanitra.
                - Mandresy lahatra ny amin'ny fahotana sy ny filantsika ny Mpamonjy.
                - Manitsy ny diantsika rehefa mania isika.
                - Mitaiza antsika hitombo amin'ny toetra tahaka an'i Kristy.

                ## III. Fampiharana azo tsapain-tanana
                Ahoana no hananantsika izany hery izany eo amin'ny fiainana andavanandro?
                1. **Mihainoa am-bavaka** : Tsy famakiana maimaika fa fifantohana sy fanetren-tena eo anatrehan'ny Tompo.
                2. **Fandinihana sy fandalinana** : Fampiasana ireo fitaovana fandalinana, fampitahana ny dikan-teny ary fandalinana ny fototry ny teny.
                3. **Fankatoavana** : Tsy ny mpihaino ihany no ankasitrahana, fa ny mpankatò ny Teny (Jakoba 1:22).

                ## Famaranana sy Vavaka
                Katsaho isan'andro ny hihaino an'Andriamanitra amin'ny alalan'ny Soratra Masina. Izy no fahazavana ho an'ny lalantsika sy fitarihana azo antoka ho an'ny fiainantsika manontolo.
            """.trimIndent(),
            originalLang = "mg",
            author = "Solohery RAKOTOARISOA",
            date = "2026-05-14",
            bibleRefs = "Hebreo 4:12 ; 2 Timoty 3:16-17",
            type = "SERMON",
            categories = "Toriteny, Finoana, Ny Tenin'Andriamanitra",
            practicalApplications = "1. Manokana fotoana mangina 20 minitra isan'andro hamakiana sy hisaintsainana ny Baiboly. 2. Manoratra an-tsoratra ny lesona azo. 3. Mankatò avy hatrany izay ambaran'ny Fanahy Masina.",
            bibliography = "Baiboly Malagasy Protestanta (1908) ; Novum Testamentum Graece (NA28) ; Calvin, Jean - Commentaires sur l'Épître aux Hébreux.",
            audioFileName = "sermon_teny_velona.mp3",
            isOfficial = true,
            isPublished = true,
            frenchTranslationTitle = "La puissance et l'autorité vivante de la Parole de Dieu",
            frenchTranslationText = "Exposition biblique sur Hébreux 4:12 et 2 Timothée 3:16. La Parole n'est pas une lettre morte mais la voix vivante du Dieu Souverain qui discerne, restaure et fortifie Son peuple.",
            englishTranslationTitle = "The Power and Authority of the Word of God",
            englishTranslationText = "Biblical exposition on Hebrews 4:12 and 2 Timothy 3:16. The Word of God is living, active, sharper than any double-edged sword, transforming hearts."
        ),
        PublicationEntity(
            title = "Ny Fahasoavana sy ny Famonjena ao amin'i Kristy",
            summary = "Fampianarana teolojika fototra mampiseho fa ny famonjena dia fanomezam-pahasoavana maimaimpoana avy amin'Andriamanitra amin'ny alalan'ny finoana an'i Jesosy Kristy.",
            fullText = """
                # Ny Fahasoavana sy ny Famonjena ao amin'i Kristy
                *Mpanoratra : Solohery RAKOTOARISOA*
                *Andininy fototra : Efesiana 2:8-10 ; Romana 3:21-26*

                ## Fampidirana
                Ny fototry ny filazantsara kristiana dia ny fahasoavana. Tsy misy olona afaka mirehareha eo anatrehan'Andriamanitra noho ny asany na ny fahamendrehany manokana.

                ## I. Ny toetry ny olombelona tsy misy an'i Kristy
                Araka ny voalaza eo am-piandohan'ny Efesiana 2, dia « maty tamin'ny fahadisoana sy ny fahotana » ny olona. Tsy marary fotsiny, fa maty ara-panahy. Ny olona maty dia tsy afaka manavotra ny tenany na manao asa mendrika fiainana mandrakizay.

                ## II. Ny hevitry ny Fahasoavana (Charis)
                Ny teny grika *Charis* dia manondro fitiavana tsy mendrika, fanomezana maimaimpoana omena ho an'izay mendrika fitsarana.
                - Tsy valin'asa : Raha valin'asa izany dia tsy fahasoavana intsony.
                - Avy amin'ny fitiavan'Andriamanitra : « Fa Andriamanitra, Izay manan-karena amin'ny famindram-po... » (Efe 2:4).
                - Amin'ny alalan'ny finoana : Ny finoana no tanana mandray ny famonjena efa vitan'i Kristy teo amin'ny hazo fijaliana.

                ## III. Ny asan'ny mino efa voavonjy
                Mariho tsara ny andininy faha-10 : « Fa asany isika, voaforona tao amin'i Kristy Jesosy mba hanao asa tsara ».
                Tsy manao asa tsara isika mba ho voavonjy, fa manao asa tsara isika satria efa voavonjy! Ny asa tsara no vokatra voajanahary an'ny finoana velona.
            """.trimIndent(),
            originalLang = "mg",
            author = "Solohery RAKOTOARISOA",
            date = "2026-06-20",
            bibleRefs = "Efesiana 2:8-10 ; Romana 3:21-26",
            type = "DOCTRINE",
            categories = "Fampianarana, Soteriôlôjia, Fahasoavana",
            practicalApplications = "1. Misaotra an'Andriamanitra isan'andro noho ny famonjena tsy nividianana. 2. Miala amin'ny fiezahana hanamarina tena amin'ny asa. 3. Manompo ny hafa amin'ny fitiavana marina.",
            bibliography = "Spurgeon, C.H. - All of Grace ; Luther, Martin - Préface à l'Épître aux Romains.",
            isOfficial = true,
            isPublished = true
        ),
        PublicationEntity(
            title = "Fandalinana ny Romana 8 : Ny Fandresena ao amin'ny Tompo",
            summary = "Fandalinana teolojika sy fampaherezana momba ny fiainan'ny Fanahy Masina sy ny fitiavan'Andriamanitra tsy mety lefy na inona na inona fisedrana.",
            fullText = """
                # Fandalinana ny Romana 8 : Ny Fandresena ao amin'ny Tompo
                *Fandalinana nataon'i : Solohery RAKOTOARISOA*
                *Andininy : Romana 8:28-39*

                ## Rafitry ny toko
                Ny Romana 8 no tendron'ny teolojian'ny Apostoly Paoly momba ny fahamarinana avy amin'ny finoana.
                1. Tsy misy fanamelohana (and. 1-4)
                2. Ny fiainana araka ny Fanahy (and. 5-17)
                3. Ny voninahitra ho avy sy ny fanantenana (and. 18-27)
                4. Ny fikasana mandrakizay an'Andriamanitra (and. 28-30)
                5. Ny hiram-pandresen'ny fitiavan'Andriamanitra (and. 31-39)

                ## Fanadihadiana ny andininy faha-28
                « Fantatsika fa ny zavatra rehetra dia miara-miasa hahasoa izay tia an'Andriamanitra ».
                Ao amin'ny teny grika : *panta synergei eis agathon*. Tsy midika akory izany fa tsara avokoa ny zava-mitranga rehetra (misy aretina, fahoriana, fanenjehana), fa Andriamanitra dia mifehy ny toe-javatra rehetra mba hampitodika azy ireo ho amin'ny famolavolana antsika hitovy endrika amin'ny Zanany.
            """.trimIndent(),
            originalLang = "mg",
            author = "Solohery RAKOTOARISOA",
            date = "2026-07-08",
            bibleRefs = "Romana 8:28-39",
            type = "BIBLE_STUDY",
            categories = "Fandalinana, Fanantenana, Fandresena",
            practicalApplications = "Rehefa sendra toe-javatra sarotra, tadidio fa tsy hahasaraka anao amin'ny fitiavan'i Kristy na fahoriana na fanenjehana.",
            bibliography = "Commentaire de la Bible Annotée (Neuchâtel) ; John Murray, The Epistle to the Romans.",
            isOfficial = true,
            isPublished = true
        ),
        PublicationEntity(
            title = "Fiofanana ho an'ny Mpitory sy Mpanompon'Andriamanitra",
            summary = "Torolalana momba ny fanomanana toriteny mahatoky amin'ny lahatsoratra (Homilétique textuelle) sy ny fomba fitondrana fampianarana kristiana.",
            fullText = """
                # Fiofanana ho an'ny Mpitory sy Mpanompon'Andriamanitra
                *Torolalana sy Modely : Solohery RAKOTOARISOA*

                ## Dingana 4 amin'ny fanomanana toriteny :
                1. **Famakiana am-bavaka sy fandinihana (Observation)** : Inona marina no lazain'ny andininy? Iza no nanoratra, tamin'iza, tamin'ny toe-javatra manao ahoana?
                2. **Fahatakarana ny hevitra tany am-boalohany (Interprétation)** : Inona no hevitr'ity teny ity ho an'ireo mpamaky voalohany tamin'ny teny fototra (Hebreo na Grika)?
                3. **Fampifandraisana amin'i Kristy (Corrélation théologique)** : Ahoana no anondroan'ity lahatsoratra ity an'i Kristy sy ny drafi-panavotan'Andriamanitra?
                4. **Fampiharana amin'ny mpihaino ankehitriny (Application)** : Inona no tokony hinoantsika, hibebahantsika, na hataontsika anio?
            """.trimIndent(),
            originalLang = "mg",
            author = "Solohery RAKOTOARISOA",
            date = "2026-08-15",
            bibleRefs = "2 Timoty 2:15 ; 2 Timoty 4:1-5",
            type = "TRAINING",
            categories = "Fiofanana, Homiletika, Asa fanompoana",
            practicalApplications = "Fampiharana ny fitsipiky ny exégèse alohan'ny hanoratana ny drafitra toriteny.",
            bibliography = "John Stott, Between Two Worlds ; Haddon Robinson, Biblical Preaching.",
            isOfficial = true,
            isPublished = true
        )
    )

    val commentaries = listOf(
        TheologicalCommentaryEntity(
            bookNumber = 43, chapter = 1, verseStart = 1, verseEnd = 5,
            passageTitle = "Ny Teny tonga Nofo (Le Prologue de Jean)",
            author = "Solohery RAKOTOARISOA / Bible Annotée",
            workTitle = "Fandalinana ny Filazantsaran'i Jaona",
            license = "Domaine public & notes pastorales",
            historicalContext = "Nosoratan'ny Apostoly Jaona tany Efesosy tamin'ny faramparan'ny taonjato voalohany mba hanoherana ny fiheverana gnostika izay nandà ny tena maha-Andriamanitra sy maha-olona marina an'i Jesosy.",
            literaryStructure = "1. Ny maha-Andriamanitra mandrakizay an'ny Logos (and. 1-2)\n2. Ny Logos amin'ny famoronana (and. 3-5)\n3. Ny fijoroana ho vavolombelona nataon'i Jaona Mpanao Batisa (and. 6-8)\n4. Ny fahatongavan'ny mazava marina sy ny fandraisana Azy (and. 9-13)\n5. Ny fahatongavana ho nofo sy ny voninahitry ny Fahasoavana (and. 14-18)",
            verseExposition = "Ny andininy 1 dia manamafy teboka telo lehibe : 1) Ny fitoerana mandrakizay ('Tamin'ny voalohany ny Teny'); 2) Ny firaisana manokana amin'ny Ray ('tao amin'Andriamanitra'); 3) Ny maha-Andriamanitra fenon'ny Zanaka ('ary ny Teny dia Andriamanitra').",
            christocentricFocus = "Jesosy Kristy no fanehoana fara-tampon'ny voninahitr'Andriamanitra. Tsy afaka mahalala an'Andriamanitra marina ny olona raha tsy amin'ny alalan'ny Zanaka.",
            homileticPoints = "1. Ny fototry ny finoantsika dia tsy petra-kevitra fa Ilay Logos velona.\n2. Ny fiainana sy ny fahazavana ao Aminy dia mandresy ny aizim-pahotan'izao tontolo izao.\n3. Ny fiantsoana ny olona tsirairay handray Azy sy ho tonga zanak'Andriamanitra.",
            studyQuestions = "1. Nahoana no teny hoe 'Logos' (Teny) no nampiasain'i Jaona hanondroana an'i Jesosy?\n2. Inona ny fahasamihafana misy eo amin'ny famoronana (Gen 1) sy ny fahatongavana ho nofo (Jao 1:14)?\n3. Ahoana no iainan'ny fiangonana izany fahazavana izany eo anivon'ny haizina?"
        ),
        TheologicalCommentaryEntity(
            bookNumber = 19, chapter = 23, verseStart = 1, verseEnd = 6,
            passageTitle = "Ilay Mpiandry Tsara (Le Seigneur est mon Berger)",
            author = "Solohery RAKOTOARISOA / Matthew Henry",
            workTitle = "Fandalinana ny Salamo",
            license = "Domaine public & notes théologiques",
            historicalContext = "Salamon'i Davida izay efa niandry ondry fony fahakeliny ary nahafantatra tsara ny fahafoizan-tena, ny fiambenana, ary ny fitarihana ilain'ny ondry.",
            literaryStructure = "1. Ny fiantohana sy fialan-tsasatra eo ambany fiandrasan'i Jehovah (and. 1-3)\n2. Ny fiarovana ao anatin'ny lohasaha maizina (and. 4)\n3. Ny fandrosoana sy fiaraha-misakafo amin'ny Tompo (and. 5)\n4. Ny fitahiana mandrakizay ao an-tranon'ny Tompo (and. 6)",
            verseExposition = "'Tsy hanan-java-mahory aho' : Tsy midika akory hoe hahazo ny rendrarendra rehetra, fa tsy hisy zavatra tena ilaina amin'ny fiainana ara-panahy ka ho latsaka.",
            christocentricFocus = "Jesosy Kristy no nanambara ao amin'ny Jaona 10:11 hoe : 'Izaho no Mpiandry Tsara; ny mpiandry tsara manolotra ny ainy hamonjy ny ondry'.",
            homileticPoints = "1. Ny fifandraisana manokana : 'Mpiandry AHY' fa tsy an'ny olona fotsiny.\n2. Ny fiadanana eo amin'ny ahi-maitso sy rano fialan-tsasatra.\n3. Ny fampiononana amin'ny tsorakazo sy tehina ao anatin'ny fisedrana.",
            studyQuestions = "1. Inona no dikan'ny 'lohasaha aloky ny fahafatesana' eo amin'ny fiainanao ankehitriny?\n2. Ahoana no ampiononan'ny tsorakazo sy ny tehin'ny Tompo antsika?"
        )
    )
}
