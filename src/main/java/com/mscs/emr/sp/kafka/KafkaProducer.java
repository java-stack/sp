package com.mscs.emr.sp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> template;

    @Autowired
    public KafkaProducer(final KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void send(final String spMessage) {
        this.template.send("broadcast.topic", spMessage);
    }

}
