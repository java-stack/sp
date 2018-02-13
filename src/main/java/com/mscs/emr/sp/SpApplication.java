package com.mscs.emr.sp;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.jms.Queue;

@SpringBootApplication
@EnableScheduling
@EnableWebSocket
public class SpApplication implements WebSocketConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SpApplication.class, args);
    }

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(broadcastHandler(), "/broadcast");
    }

    @Bean
    public BroadcastWebSocketHandler broadcastHandler() {
        return new BroadcastWebSocketHandler();
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("broadcast.queue");
    }
}
