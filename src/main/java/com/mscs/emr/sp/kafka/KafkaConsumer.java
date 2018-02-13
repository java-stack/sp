package com.mscs.emr.sp.kafka;

import com.mscs.emr.sp.BroadcastWebSocketHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final BroadcastWebSocketHandler broadcastWebSocketHandler;

    @Autowired
    public KafkaConsumer(final BroadcastWebSocketHandler broadcastWebSocketHandler) {
        this.broadcastWebSocketHandler = broadcastWebSocketHandler;
    }

    @KafkaListener(topics = "broadcast.topic")
    public void listen(final ConsumerRecord<String, String> cr) throws Exception {
        logger.info("Received Kafka message [{}] to broadcast", cr.toString());
        broadcastWebSocketHandler.broadcast(cr.value());
    }
}
