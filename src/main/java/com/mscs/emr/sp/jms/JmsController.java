package com.mscs.emr.sp.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/jms")
public class JmsController {
    private static final Long userId = 1L;
    private static final Long practiceId = 1L;
    private static final String messageTemplate = "Jms message (current time millis: %1$s)";
    private final JmsProducer jmsProducer;
    private static final int messageCount = 100;

    @Autowired
    public JmsController(final JmsProducer jmsProducer) {
        this.jmsProducer = jmsProducer;
    }

    @GetMapping(path="/send")
    public @ResponseBody String send (@RequestParam(required = false) final Integer mcount) {
        for (int i = 0; i < (null == mcount ? messageCount : mcount); i++) {
            this.jmsProducer.send(String.format(messageTemplate, System.currentTimeMillis()));
        }

        return "Submitted";
    }
}