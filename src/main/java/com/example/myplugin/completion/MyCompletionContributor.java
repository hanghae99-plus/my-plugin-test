package com.example.myplugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;

public class MyCompletionContributor extends CompletionContributor {
    public MyCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^@.*")),
                new MyCompletionProvider("@"));
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^test@.*")),
                new MyCompletionProvider("test@"));
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("\\^.*")),
                new MyCompletionProvider("^"));
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^\\$.*")),
                new MyCompletionProvider("$"));
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class).withText(StandardPatterns.string().matches("^#.*")),
                new MyCompletionProvider("#"));
    }
}
