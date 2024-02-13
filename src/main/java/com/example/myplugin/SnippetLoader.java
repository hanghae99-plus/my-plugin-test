package com.example.myplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class SnippetLoader {
    public Map<String, String> loadSnippets(String resourcePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> snippets = null;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            snippets = objectMapper.readValue(inputStream, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snippets;
    }
}
