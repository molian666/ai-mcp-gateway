package org.example.cases.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import org.example.cases.session.AbstractMCPSessionSupport;
import org.example.cases.session.factory.DefaultMCPSessionFactory;
import org.example.domain.session.model.valobj.SessionConfigVO;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author wyh
 */
@Service
public class EndNode extends AbstractMCPSessionSupport {

    @Override
    protected Flux<ServerSentEvent<String>> doApply(String requestParameter, DefaultMCPSessionFactory.DynamicContext dynamicContext) throws Exception {
        SessionConfigVO sessionConfigVO = dynamicContext.getSessionConfigVO();

        String sessionId = sessionConfigVO.getSessionId();

        Sinks.Many<ServerSentEvent<String>> sink = sessionConfigVO.getSink();

        return sink.asFlux()
                .mergeWith(
                        Flux.interval(Duration.ofSeconds(60))
                                .map(i -> ServerSentEvent.<String>builder()
                                .event("ping")
                                .data("ping")
                                .build())
                )
                .doOnCancel(() -> {
                    sessionManagementService.removeSession(sessionId);
                })
                .doOnTerminate(() -> {
                    sessionManagementService.removeSession(sessionId);
                });
    }

    @Override
    public StrategyHandler<String, DefaultMCPSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String s, DefaultMCPSessionFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }
}
