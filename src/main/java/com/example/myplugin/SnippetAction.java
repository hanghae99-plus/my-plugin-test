package com.example.myplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

import java.util.HashMap;
import java.util.Map;
public class SnippetAction extends AnAction {
    // 스니펫을 저장할 맵. 키는 사용자가 입력할 문자열, 값은 대체될 스니펫
    private Map<String, String> snippetMap = new HashMap<>();

    public SnippetAction() {
        // 스니펫 맵에 "hi"라는 키와 대응되는 스니펫을 추가
        snippetMap.put("hi", "System.out.println(\"Hello World\");");
        // 여기에 추가 스니펫 등록
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 현재 활성화된 에디터 로드
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        SelectionModel selectionModel = editor.getSelectionModel();

        // 사용자가 선택한 텍스트 로드
        String selectedText = selectionModel.getSelectedText();

        // 선택한 텍스트가 스니펫 맵의 키에 해당한다면 스니펫으로 대체
        if (selectedText != null && snippetMap.containsKey(selectedText)) {
            String snippet = snippetMap.get(selectedText);
            int start = selectionModel.getSelectionStart();
            int end = selectionModel.getSelectionEnd();

            // WriteCommandAction을 사용하여 문서를 수정
            WriteCommandAction.runWriteCommandAction(e.getProject(), () ->
                    document.replaceString(start, end, snippet)
            );
        }
    }
}

