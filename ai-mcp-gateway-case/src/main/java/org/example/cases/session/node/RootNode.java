package org.example.cases.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import org.example.cases.session.AbstractMCPSessionSupport;
import org.example.cases.session.factory.DefaultMCPSessionFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author wyh
 */
@Service
public class RootNode extends AbstractMCPSessionSupport {

    @Resource
    private VerifyNode verifyNode;


    @Override
    public StrategyHandler<String, DefaultMCPSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String s, DefaultMCPSessionFactory.DynamicContext dynamicContext) throws Exception {
        return verifyNode;
    }

    @Override
    protected Flux<ServerSentEvent<String>> doApply(String requestParameter, DefaultMCPSessionFactory.DynamicContext dynamicContext) throws Exception {

        try {
            return router(requestParameter, dynamicContext);
        } catch (Exception e) {
            throw e;
        }
    }
}
