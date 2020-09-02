package sn.fun.textedit.algos;

import org.springframework.stereotype.Component;

@Component
public class TextAlign {
    private static final String crRegEx = "\n";
    private static final String whiteSpaceRegEx = "\\s+";

    public String alignText(String text, String align, int pageWidth) {
        if ("left".equals(align)) {
            return text;
        } else if ("right".equals(align)) {
            return alignRight(text, pageWidth);
        } else {
            return alignJustified(text, pageWidth);
        }
    }

    String alignRight(String text, int pageWidth) {
        StringBuilder sb = new StringBuilder();
        String[] lines = text.split(crRegEx);
        for (String line: lines) {
            int toShift = pageWidth - line.length() + 1;
            sb.append(" ".repeat(Math.max(0, toShift)));
            sb.append(line);
            sb.append('\n');
        }
        return sb.toString();
    }

    String alignJustified(String text, int pageWidth) {
        StringBuilder sb = new StringBuilder();
        String[] lines = text.split(crRegEx);
        for (String line: lines) {
            int toShift = pageWidth - line.length() + 1;
            String[] words = line.split(whiteSpaceRegEx);
            int spaces = words.length - 1;
            for (String word: words) {
                sb.append(word).append(' ');
                int addSpaces = (int) Math.ceil(toShift/(double)spaces);
                sb.append(" ".repeat(Math.max(0, addSpaces)));
                spaces--;
                toShift -= addSpaces;
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
