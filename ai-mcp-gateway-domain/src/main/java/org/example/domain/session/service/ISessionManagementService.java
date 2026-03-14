package org.example.domain.session.service;

import org.example.domain.session.model.valobj.SessionConfigVO;

/**
 * @author wyh
 */
public interface ISessionManagementService {
    SessionConfigVO createSession(String gatewayId);

    void removeSession(String sessionId);

    SessionConfigVO getSession(String sessionId);

    void cleanupExpiredSessions();

    void shutdown();
}
