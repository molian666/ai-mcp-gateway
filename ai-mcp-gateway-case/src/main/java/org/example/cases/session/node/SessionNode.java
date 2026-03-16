package org.example.cases.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import org.example.cases.session.AbstractMCPSessionSupport;
import org.example.cases.session.factory.DefaultMCPSessionFactory;
import org.example.domain.session.model.valobj.SessionConfigVO;
import org.example.domain.session.service.ISessionManagementService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author wyh
 */
@Service
public class SessionNode extends AbstractMCPSessionSupport {

    @Resource
    private EndNode endNode;

    @Resource
    private ISessionManagementService sessionManagementService;

    @Override
    protected Flux<ServerSentEvent<String>> doApply(String requestParameter, DefaultMCPSessionFactory.DynamicContext dynamicContext) throws Exception {
        SessionConfigVO sessionConfigVO = sessionManagementService.createSession(requestParameter);
        dynamicContext.setSessionConfigVO(sessionConfigVO);
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<String, DefaultMCPSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String s, DefaultMCPSessionFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
