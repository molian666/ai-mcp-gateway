package org.example.cases;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * @author wyh
 */
public interface IMCPSessionService {

    Flux<ServerSentEvent<String>> createMCPSession(String gatewayId) throws Exception;
}
