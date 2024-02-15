package com.example.myplugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PluginActionTest {

    @Mock
    private AnActionEvent mockEvent;
    @Mock
    private Editor mockEditor;
    @Mock
    private Document mockDocument;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Mock behavior
        when(mockEvent.getRequiredData(CommonDataKeys.EDITOR)).thenReturn(mockEditor);
        when(mockEditor.getDocument()).thenReturn(mockDocument);
    }

    @Test
    public void testActionPerformed() {
        // Instantiate your action
        SamplePluginAction action = new SamplePluginAction();

        // Call the method under test
        action.actionPerformed(mockEvent);

        // Verify the expected behavior
        // Assuming SamplePluginAction inserts "Hello World!" into the Document
        verify(mockDocument).insertString(anyInt(), eq("Hello World!"));
    }
}
