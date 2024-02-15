package com.example.myplugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class CompletionProviderImpl extends CompletionProvider<CompletionParameters> {
    private final Map<String, String> snippets;

    public CompletionProviderImpl(String resourcePath, String s) {
        snippets = loadSnippets(resourcePath);
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet resultSet) {
        String currentText = parameters.getOriginalPosition().getText();
        snippets.forEach((key, value) -> {
            if (key.startsWith(currentText)) {
                resultSet.addElement(LookupElementBuilder.create(key)
                        .withPresentableText(key + ": " + value)
                        .withInsertHandler((context1, item) -> {
                            Editor editor = context1.getEditor();
                            Document document = editor.getDocument();
                            document.replaceString(context1.getStartOffset(), context1.getTailOffset(), value);
                        }));
            }
        });
    }

    private Map<String, String> loadSnippets(String resourcePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return objectMapper.readValue(inputStream, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
