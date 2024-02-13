package com.example.myplugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SnippetActionTest {

    @Mock
    private AnActionEvent mockEvent;
    @Mock
    private Editor mockEditor;
    @Mock
    private Document mockDocument;
    @Mock
    private SelectionModel mockSelectionModel;

    // Mocking SnippetLoader to avoid dependency on the actual file system
    @Mock
    private SnippetLoader mockSnippetLoader;

    // Setup required to inject mockSnippetLoader into SnippetAction
    @InjectMocks
    private SnippetAction action;

    @Before
    public void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);

        // Mocking the selected text and selection range
        when(mockEvent.getRequiredData(CommonDataKeys.EDITOR)).thenReturn(mockEditor);
        when(mockEditor.getDocument()).thenReturn(mockDocument);
        when(mockEditor.getSelectionModel()).thenReturn(mockSelectionModel);
        when(mockSelectionModel.getSelectedText()).thenReturn("hi");
        when(mockSelectionModel.getSelectionStart()).thenReturn(0);
        when(mockSelectionModel.getSelectionEnd()).thenReturn(2);

        // Configuring SnippetLoader to return a specific snippet map
        Map<String, String> testSnippets = new HashMap<>();
        testSnippets.put("hi", "System.out.println(\"Hello World\");");
        when(mockSnippetLoader.loadSnippets(anyString())).thenReturn(testSnippets);
    }

    @Test
    public void testActionPerformedWithSnippet() {
        // Call the actionPerformed method of SnippetAction
        action.actionPerformed(mockEvent);

        // Verify that the replaceString method of Document was called as expected
        verify(mockDocument).replaceString(0, 2, "System.out.println(\"Hello World\");");
    }

    @Test
    public void testActionPerformedWithInvalidSnippet() {
        // Test setup for when the selected text is not in the snippet map
        when(mockSelectionModel.getSelectedText()).thenReturn("unknown");

        // Call the actionPerformed method of SnippetAction
        action.actionPerformed(mockEvent);

        // Verify that the text was replaced with "invalid snippet"
        verify(mockDocument).replaceString(anyInt(), anyInt(), eq("invalid snippet"));
    }
}
