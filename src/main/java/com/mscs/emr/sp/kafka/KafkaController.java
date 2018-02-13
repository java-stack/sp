package com.mscs.emr.sp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/kafka")
public class KafkaController {
    private static final Long userId = 1L;
    private static final Long practiceId = 1L;
    private static final String messageTemplate = "Kafka message (current time millis: %1$s)";
    private final KafkaProducer kafkaProducer;

    private static final int messageCount = 100;

    @Autowired
    public KafkaController(final KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping(path="/send")
    public @ResponseBody String send (@RequestParam(required = false) final Integer mcount) {
        for (int i = 0; i < (null == mcount ? messageCount : mcount); i++) {
            this.kafkaProducer.send(String.format(messageTemplate, System.currentTimeMillis()));
        }
        return "Submitted";
    }
}