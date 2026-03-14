package org.example.domain.session.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.session.model.valobj.SessionConfigVO;
import org.example.domain.session.service.ISessionManagementService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wyh
 */
@Slf4j
@Service
public class SessionManagementServiceImpl implements ISessionManagementService {

    private static final long SESSION_TIMEOUT_MINUTES = 30;

    private final ScheduledExecutorService cleanupScheduler = Executors.newSingleThreadScheduledExecutor();

    private final Map<String, SessionConfigVO> activeSessions = new HashMap<>();


    public SessionManagementServiceImpl() {
        cleanupScheduler.scheduleAtFixedRate(this::cleanupExpiredSessions, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public SessionConfigVO createSession(String gatewayId) {
        String sessionId = UUID.randomUUID().toString();

        Sinks.Many<ServerSentEvent<String>> sink = Sinks.many().multicast().onBackpressureBuffer();

        String messageEndPoint = "/" + gatewayId + "/mcp/message?sessionId=" + sessionId;

        sink.tryEmitNext(ServerSentEvent.<String>builder()
                        .event("endpoint")
                        .data(messageEndPoint)
                .build());

        SessionConfigVO sessionConfigVO = new SessionConfigVO(sessionId, sink);

        activeSessions.put(sessionId, sessionConfigVO);

        log.info("创建会话 gatewayId:{}",gatewayId);

        return sessionConfigVO;
    }

    @Override
    public void removeSession(String sessionId) {
        if (null == sessionId || sessionId.isEmpty()) {
            return;
        }

        SessionConfigVO sessionConfigVO = activeSessions.remove(sessionId);

        if (sessionConfigVO == null){
            return;
        }

        sessionConfigVO.markInactive();

        try {
            sessionConfigVO.getSink().tryEmitComplete();
        } catch (Exception e) {
            log.info("关闭会话失败!");
        }

    }

    @Override
    public SessionConfigVO getSession(String sessionId) {

        if (null == sessionId || sessionId.isEmpty()){
            return null;
        }

        SessionConfigVO sessionConfigVO = activeSessions.get(sessionId);

        if (sessionConfigVO != null && sessionConfigVO.isActive()){
            sessionConfigVO.updateLastAccessed();
            return sessionConfigVO;
        }

        return null;
    }

    @Override
    public void cleanupExpiredSessions() {
        int cleanedCount = 0;

        for (Map.Entry<String, SessionConfigVO> entry : activeSessions.entrySet()) {
            SessionConfigVO sessionConfigVO = entry.getValue();
            
            if (sessionConfigVO.isExpired(SESSION_TIMEOUT_MINUTES) || !sessionConfigVO.isActive()) {
                removeSession(entry.getKey());
                cleanedCount++;
            }
        }
        
        if (cleanedCount > 0) {
            log.info("清理过期会话，清理数量：{}", cleanedCount);
        }
    }

    @Override
    public void shutdown() {
        for (String sessionId : activeSessions.keySet()){
            removeSession(sessionId);
        }

        cleanupScheduler.shutdown();

        try {
            if (!cleanupScheduler.awaitTermination(5, TimeUnit.SECONDS)){
                cleanupScheduler.shutdown();
            }
        } catch (InterruptedException e) {
            cleanupScheduler.shutdown();
            Thread.currentThread().interrupt();
        }
    }
}
