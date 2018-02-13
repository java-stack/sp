package com.mscs.emr.sp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class BroadcastWebSocketHandler implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(BroadcastWebSocketHandler.class);
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        sessions.put(session.getId(), session);
        logger.info("Opened new session id [{}], address [{}], current session count [{}]" , session.getId(), session.getRemoteAddress().getAddress(), sessions.size());
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        logger.info("Removing closed session id [{}], current session count [{}]", session.getId(), sessions.size());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handleTransportError(final WebSocketSession session, final Throwable exception) throws Exception {
    }

    public void broadcast(final String message) throws IOException {
        for (final WebSocketSession s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(message));
            } else {
                logger.info("Removing closed session id [{}] during message broadcast", s.getId());
                sessions.remove(s.getId());
            }
        }
        logger.info("Outgoing message [{}] to [{}] session(s)", message , sessions.size());
    }

    public int getSessionCount() {
        return sessions.size();
    }

}
