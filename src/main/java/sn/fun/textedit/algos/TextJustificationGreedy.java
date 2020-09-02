package sn.fun.textedit.algos;

import org.springframework.stereotype.Component;
import sn.fun.textedit.data.EditorData;

import java.util.Arrays;
import java.util.List;

@Component
public class TextJustificationGreedy {
    private static final String whiteSpaceRegEx = "\\s+";

    public String justifyText(EditorData editorData) {
        String text = editorData.getEditorContent();
        int pageWidth = editorData.getEditorWidth();
        List<String> words = Arrays.asList(text.trim().split(whiteSpaceRegEx));

        StringBuilder sb = new StringBuilder();
        sb.append(words.get(0));
        int curLineLength = words.get(0).length();
        for (int i = 1; i < words.size(); i++) {
            String word = words.get(i);
            curLineLength += word.length();
            if (curLineLength < pageWidth) {
                sb.append(' ');
                sb.append(word);
                curLineLength++;
            } else {
                sb.append("\n");
                sb.append(word);
                curLineLength = word.length();
            }
        }
        return sb.toString();
    }
}
