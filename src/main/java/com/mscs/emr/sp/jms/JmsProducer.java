package com.mscs.emr.sp.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class JmsProducer {

	private final JmsMessagingTemplate jmsMessagingTemplate;
	private final Queue queue;

	@Autowired
	public JmsProducer(final JmsMessagingTemplate jmsMessagingTemplate, final Queue queue) {
		this.jmsMessagingTemplate = jmsMessagingTemplate;
		this.queue = queue;
	}

	public void send(final String spMessage) {
		this.jmsMessagingTemplate.convertAndSend(this.queue, spMessage);
	}

}
