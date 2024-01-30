package com.example.myplugin;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.command.WriteCommandAction;
import java.util.HashMap;
import java.util.Map;

public class SnippetAction extends AnAction {
    private Map<String, String> snippetMap = new HashMap<>();

    public SnippetAction() {
        // 여기에 스니펫을 추가합니다.
        snippetMap.put("hi", "System.out.println(\"Hello World\");");
        // 필요한 만큼 추가...
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();

        // 현재 단어 또는 선택된 텍스트를 가져옵니다.
        String currentText = editor.getSelectionModel().getSelectedText();

        // 스니펫 맵에서 해당 텍스트에 대한 스니펫을 찾습니다.
        String snippet = snippetMap.get(currentText);
        if (snippet != null) {
            int offset = caretModel.getOffset();
            WriteCommandAction.runWriteCommandAction(e.getProject(), () ->
                    document.insertString(offset, snippet)
            );
        }
        System.out.println();
    }
}
