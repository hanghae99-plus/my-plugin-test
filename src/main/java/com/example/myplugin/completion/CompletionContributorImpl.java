package com.example.myplugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
public class CompletionContributorImpl extends CompletionContributor {
    public CompletionContributorImpl() {
        // Specifies the location of the snippet file. In this example, 'data.json' on the classpath is used.
        // The actual path may vary depending on project configuration and execution environment.
        String jsonFilePath = "data.json";

        // Creates instances of CompletionProviderImpl to provide snippets matching the pattern,
        // and calls the extent method for each pattern.
        // In this example, different autocomplete items are provided based on the starting character(s) of the snippet
        // such as "@", "test@", "^", "$", "#".
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^@@.*")),
                new CompletionProviderImpl(jsonFilePath, "@@"));

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^test@.*")),
                new CompletionProviderImpl(jsonFilePath, "test@"));

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^\\^.*")),
                new CompletionProviderImpl(jsonFilePath, "^"));

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^\\$.*")),
                new CompletionProviderImpl(jsonFilePath, "$"));

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^#.*")),
                new CompletionProviderImpl(jsonFilePath, "#"));
    }
}
