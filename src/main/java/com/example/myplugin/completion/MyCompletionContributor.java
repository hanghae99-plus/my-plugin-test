package com.example.myplugin.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiPlainText;

public class MyCompletionContributor extends CompletionContributor {
    public MyCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiPlainText.class),
                new MyCompletionProvider());
    }
}
