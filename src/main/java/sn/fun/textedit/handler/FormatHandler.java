package sn.fun.textedit.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sn.fun.textedit.algos.TextAlign;
import sn.fun.textedit.algos.TextJustificationDP;
import sn.fun.textedit.algos.TextJustificationGreedy;
import sn.fun.textedit.data.EditorData;

@Slf4j
@Component
@RequiredArgsConstructor
public class FormatHandler {
    private final TextJustificationDP textJustificationDP;
    private final TextJustificationGreedy textJustificationGreedy;
    private final TextAlign textAlign;

    public Mono<ServerResponse> process(ServerRequest request) {
        return request.bodyToMono(EditorData.class).flatMap(editorData -> {
            log.info("EditorForm {}", editorData);
            String processedText;
            if ("greedy".equals(editorData.getFormat())) {
                processedText = textJustificationGreedy.justifyText(editorData);
            } else {
                processedText = textJustificationDP.justifyText(editorData);
            }
            String alignedText = textAlign.alignText(processedText, editorData.getAlign(), editorData.getEditorWidth());
            return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                    .body(BodyInserters.fromValue(alignedText));
        });
    }

    private String process(EditorData editorData) {
        StringBuilder sb = new StringBuilder();
        int maxLen = editorData.getEditorWidth();
        String in = editorData.getEditorContent();
        for (int i = 0; i < in.length(); i++) {
            sb.append(in.charAt(i));
            if ((i+1) % maxLen == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}