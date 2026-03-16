package org.example.cases.session.factory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cases.session.node.RootNode;
import org.example.domain.session.model.valobj.SessionConfigVO;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author wyh
 */
@Service
public class DefaultMCPSessionFactory {

    @Resource
    private RootNode rootNode;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext{
        private SessionConfigVO sessionConfigVO;
    }

    public StrategyHandler<String, DefaultMCPSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> strategyHandler(){
        return rootNode;
    }

}
