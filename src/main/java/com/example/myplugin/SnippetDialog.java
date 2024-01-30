package com.example.myplugin;

import com.intellij.openapi.ui.Messages;

public class SnippetDialog {
    public static String show() {
        // 대화 상자를 표시하여 사용자로부터 스니펫 내용을 입력받습니다.
        return Messages.showInputDialog("Enter your snippet:", "Insert Snippet", null);
    }
}
