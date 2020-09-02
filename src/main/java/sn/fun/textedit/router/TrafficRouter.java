package sn.fun.textedit.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import sn.fun.textedit.handler.FormatHandler;

@Configuration
public class TrafficRouter {
    @Bean
    public RouterFunction<ServerResponse> route(FormatHandler greetingHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/process")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), greetingHandler::process);
    }
}

