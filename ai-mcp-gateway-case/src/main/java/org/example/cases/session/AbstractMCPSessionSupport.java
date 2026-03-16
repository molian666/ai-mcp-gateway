package org.example.cases.session;

import cn.bugstack.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;
import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import org.example.cases.session.factory.DefaultMCPSessionFactory;
import org.example.domain.session.service.ISessionManagementService;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author wyh
 */
public abstract class AbstractMCPSessionSupport extends AbstractMultiThreadStrategyRouter<String, DefaultMCPSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> {

    @Resource
    protected ISessionManagementService sessionManagementService;

    @Override
    protected void multiThread(String requestParameter, DefaultMCPSessionFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {

    }
}
