package com.example.myplugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class MyCompletionProvider extends CompletionProvider<CompletionParameters> {
    public MyCompletionProvider(String s) {
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet resultSet) {
        resultSet.addElement(LookupElementBuilder.create("hi")
                .withPresentableText("System.out.println(\"Hello World\");")
                .withInsertHandler((context1, item) -> {
                    Editor editor = context1.getEditor();
                    Document document = editor.getDocument();
                    // Replace the whole "hi" with the snippet instead of inserting.
                    document.replaceString(context1.getStartOffset(), context1.getTailOffset(), "System.out.println(\"Hello World\");");
                }));
    }
}
