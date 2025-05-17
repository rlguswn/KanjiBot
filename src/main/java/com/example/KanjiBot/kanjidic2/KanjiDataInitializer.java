package com.example.KanjiBot.kanjidic2;

import com.example.KanjiBot.domain.Kanji;
import com.example.KanjiBot.mapper.KanjiMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class KanjiDataInitializer implements ApplicationRunner {

    private final KanjiMapper kanjiMapper;

    public KanjiDataInitializer(KanjiMapper kanjiMapper) {
        this.kanjiMapper = kanjiMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (kanjiMapper.findAll().isEmpty()) {
            insertKanjiData();
        }
    }

    @PostConstruct
    public void insertKanjiData() throws Exception {
        File xmlFile = new File("src/main/resources/static/kanjidic2.xml");
        List<Kanji> kanjiList = Kanjidic2XmlParser.parseKanjiXml(xmlFile);
        for (Kanji kanji : kanjiList) {
            Kanji existingKanji = new Kanji(
                    kanji.getKanjiCharacter(),
                    kanji.getMeaning(),
                    kanji.getReading(),
                    ""
            );
            kanjiMapper.insertKanji(existingKanji);
        }
    }
}
