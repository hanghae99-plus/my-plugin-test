package com.example.myplugin.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class MyCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet resultSet) {
        resultSet.addElement(LookupElementBuilder.create("hi")
                .withPresentableText("System.out.println(\"Hello World\");")
                .withInsertHandler((insertionContext, item) -> {
                    Editor editor = insertionContext.getEditor();
                    Document document = editor.getDocument();
                    document.insertString(insertionContext.getTailOffset(), "System.out.println(\"Hello World\");");
                }));
    }
}