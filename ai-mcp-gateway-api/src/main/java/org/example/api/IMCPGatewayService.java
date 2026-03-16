package org.example.api;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * @author wyh
 */
public interface IMCPGatewayService {
    Flux<ServerSentEvent<String>> establishSSEConnection(String gatewayId) throws Exception;
}
