package com.mscs.emr.sp.jms;

import com.mscs.emr.sp.BroadcastWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JmsConsumer {
    private static final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);
    private final BroadcastWebSocketHandler broadcastWebSocketHandler;

    @Autowired
    public JmsConsumer(final BroadcastWebSocketHandler broadcastWebSocketHandler) {
        this.broadcastWebSocketHandler = broadcastWebSocketHandler;
    }

    @JmsListener(destination = "broadcast.queue")
    public void receiveQueue(final String message) throws IOException {
        logger.info("Received Jms message [{}] to broadcast", message);
        broadcastWebSocketHandler.broadcast(message);
    }
}
