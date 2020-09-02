package sn.fun.textedit.data;

import lombok.Data;

@Data
public class EditorData {
    private final int editorWidth;
    private final String editorContent;
    private final String format;
    private final String align;
}
