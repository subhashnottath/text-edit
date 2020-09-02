package sn.fun.textedit.algos;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sn.fun.textedit.data.EditorData;

import java.util.*;

@Component
public class TextJustificationDP {
    private final int badnessMultiplier;
    private static final String whiteSpaceRegEx = "\\s+";

    public TextJustificationDP(@Value("${textjustification.badness-multiplier}") int badnessMultiplier) {
        this.badnessMultiplier = badnessMultiplier;
    }

    @Data
    private static class JTData {
        final List<String> words;
        final int pageWidth;
        final int badnessMultiplier;
        final Map<Integer, Integer> parent = new HashMap<>();
        final Map<Integer, Double> dp = new HashMap<>();
    }

    public String justifyText(EditorData editorData) {
        String text = editorData.getEditorContent();
        int pageWidth = editorData.getEditorWidth();
        List<String> words = Arrays.asList(text.trim().split(whiteSpaceRegEx));

        JTData data = new JTData(words, pageWidth, badnessMultiplier);
        justifyTextRec(data, 0);
        StringBuilder sb = new StringBuilder();
        Integer p = 0;
        Integer pp = data.parent.get(p);
        while (pp != null) {
            for (int i = p; i < pp; i++) {
                sb.append(words.get(i)).append(" ");
            }
            sb.append("\n");
            p = pp;
            pp = data.parent.get(p);
        }
        return sb.toString();
    }

    private double justifyTextRec(JTData data, int i) {
        int n = data.words.size();
        if (i == n) {
            return 0;
        }
        Double minCost = data.dp.get(i);
        if (minCost != null) {
            return minCost;
        }
        minCost = Double.MAX_VALUE;
        int minIndex = i;
        int currentLineLength = 0;
        for (int j = i; j < n; j++) {
            currentLineLength = currentLineLength + data.words.get(j).length();
            double badness = calculateBadness(data.pageWidth, currentLineLength);
            double currentCost = badness + justifyTextRec(data, j + 1);
            if (currentCost == Double.MAX_VALUE) {
                if (i == j) {
                    minCost = 100.0;
                }
                break;
            }
            if (currentCost < minCost) {
                minIndex = j + 1;
                minCost = currentCost;
            }
            currentLineLength++;
        }
        data.dp.put(i, minCost);
        data.parent.put(i, minIndex);
        return minCost;
    }

    double calculateBadness(int pageWidth, int currentLineLength) {
        int spaceLeft = pageWidth - currentLineLength;
        if (spaceLeft < 0) {
            return Double.MAX_VALUE;
        }
        return Math.pow(spaceLeft, badnessMultiplier);
    }
}
