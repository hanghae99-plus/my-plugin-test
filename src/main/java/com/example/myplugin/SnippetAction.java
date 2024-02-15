package com.example.myplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

import java.util.Map;
public class SnippetAction extends AnAction {

    private Map<String, String> snippetMap;

    public SnippetAction() {
        // Instantiate the snippet loader to load snippets from the resource file.
        SnippetLoader snippetLoader = new SnippetLoader();
        // Specify the name of the resource file containing the snippets.
        String resourcePath = "data.json";
        // Load snippets from the specified resource path into the snippetMap.
        snippetMap = snippetLoader.loadSnippets(resourcePath);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Load the currently active editor from the action event.
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        SelectionModel selectionModel = editor.getSelectionModel();

        // Load the text selected by the user in the editor.
        String selectedText = selectionModel.getSelectedText();

        // Check if the snippetMap is null or if the selected text is not a key in the snippetMap.
        // If so, replace the selected text with "invalid snippet".
        if (snippetMap == null || !snippetMap.containsKey(selectedText)) {
            replaceSelectedText(editor, "invalid snippet");
        } else {
            // If the selected text is a key in the snippetMap, replace it with its corresponding snippet.
            String snippet = snippetMap.get(selectedText);
            int start = selectionModel.getSelectionStart();
            int end = selectionModel.getSelectionEnd();

            // Use WriteCommandAction to modify the document with the snippet.
            WriteCommandAction.runWriteCommandAction(e.getProject(), () ->
                    document.replaceString(start, end, snippet)
            );
        }
    }

    private void replaceSelectedText(Editor editor, String text) {
        Document document = editor.getDocument();
        SelectionModel selectionModel = editor.getSelectionModel();
        int start = selectionModel.getSelectionStart();
        int end = selectionModel.getSelectionEnd();

        // Use WriteCommandAction to insert the text into the document at the specified location.
        WriteCommandAction.runWriteCommandAction(editor.getProject(), () ->
                document.replaceString(start, end, text)
        );
    }
}
