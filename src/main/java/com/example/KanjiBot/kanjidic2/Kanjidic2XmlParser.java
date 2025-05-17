package com.example.KanjiBot.kanjidic2;

import com.example.KanjiBot.domain.Kanji;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Kanjidic2XmlParser {

    public static List<Kanji> parseKanjiXml(File xmlFile) throws Exception {
        List<Kanji> kanjiList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(xmlFile), StandardCharsets.UTF_8)) {
            InputSource inputSource = new InputSource(reader);
            Document document = builder.parse(inputSource);

            NodeList kanjiNodes = document.getElementsByTagName("character");

            for (int i = 0; i < kanjiNodes.getLength(); i++) {
                Element characterElement = (Element) kanjiNodes.item(i);

                String character = getTextContent(characterElement);

                List<String> readings = getReadings(characterElement);
                String readingText = String.join(", ", readings);
                if (readings.isEmpty()) continue;

                List<String> meanings = getMeanings(characterElement);
                String meaningText = String.join(", ", meanings);
                if (meanings.isEmpty()) continue;

                Kanji kanji = new Kanji(
                        character,
                        meaningText,
                        readingText,
                        null
                );
                kanjiList.add(kanji);
            }
        }
        return kanjiList;
    }

    private static List<String> getReadings(Element element) {
        List<String> readings = new ArrayList<>();
        NodeList readingNodes = element.getElementsByTagName("reading");

        for (int i = 0; i < readingNodes.getLength(); i++) {
            Element readingElement = (Element) readingNodes.item(i);
            String type = readingElement.getAttribute("r_type");
            if (type.equals("ja_on") || type.equals("ja_kun")) {
                readings.add(readingElement.getTextContent());
            }
        }
        return readings;
    }

    private static List<String> getMeanings(Element element) {
        List<String> meanings = new ArrayList<>();
        NodeList meaningNodes = element.getElementsByTagName("meaning");

        for (int i = 0; i < meaningNodes.getLength(); i++) {
            Element meaningElement = (Element) meaningNodes.item(i);
            if (!meaningElement.hasAttribute("m_lang") || meaningElement.getAttribute("m_lang").equals("en")) {
                meanings.add(meaningElement.getTextContent());
            }
        }
        return meanings;
    }

    private static String getTextContent(Element element) {
        NodeList nodeList = element.getElementsByTagName("literal");
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
