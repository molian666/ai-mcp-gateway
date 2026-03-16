package org.example.cases.session;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import org.example.cases.IMCPSessionService;
import org.example.cases.session.factory.DefaultMCPSessionFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author wyh
 */
@Service
public class MCPSessionServiceImpl implements IMCPSessionService {

    @Resource
    private DefaultMCPSessionFactory defaultMCPSessionFactory;

    @Override
    public Flux<ServerSentEvent<String>> createMCPSession(String gatewayId) throws Exception {
        StrategyHandler<String, DefaultMCPSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> strategyHandler = defaultMCPSessionFactory.strategyHandler();

        return strategyHandler.apply(gatewayId, new DefaultMCPSessionFactory.DynamicContext());
    }
}
