package com.example.KanjiBot.deepl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class DeepLTranslate {

    private final String authKey;

    public DeepLTranslate(DeepLConfig deepLConfig) {
        this.authKey = deepLConfig.getAuthKey();
    }

    public String translate(String text, String sourceLang, String targetLang) throws IOException {
        URL url = new URL("https://api-free.deepl.com/v2/translate");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Authorization", "DeepL-Auth-Key " + authKey);

        String data = "text=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&source_lang=" + URLEncoder.encode(sourceLang, StandardCharsets.UTF_8) +
                "&target_lang=" + URLEncoder.encode(targetLang, StandardCharsets.UTF_8);

        try (OutputStream outputStream = conn.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        return readTextData(response.toString());
    }

    public String readTextData(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.toString());
        return jsonNode.get("translations").get(0).get("text").asText();
    }
}
