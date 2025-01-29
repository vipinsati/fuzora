package com.fuzora.protocol.configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fuzora.reader.ConfigReader;

//import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class AMQPConfiguration {

//	@Autowired
//	ConfigReader configReader;
//
//	private String username;
//	private String password;
//	private String host;
//	private int port;
//
//	public AMQPConfiguration() {
////		JsonNode authentication = configReader.
//		this.username = authentication.get("username").asText();
//		this.password = authentication.get("password").asText();
//		this.host = authentication.get("host").asText();
//		this.port = authentication.get("port").asInt();
//	}
//
//	public static final String QUEUE_NAME = "myQueue";
//	public static final String EXCHANGE_NAME = "myExchange";
//
//	@Bean("amqp-connectionFactory")
//	ConnectionFactory connectionFactory() {
//		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//		connectionFactory.setUsername(this.username);
//		connectionFactory.setPassword(this.password);
//		connectionFactory.setHost(this.host);
//		connectionFactory.setPort(this.port);
//		return connectionFactory;
//	}
//
//	@Bean
//	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//		RabbitTemplate rabbitTemplate = new RabbitTemplate();
//		rabbitTemplate.setConnectionFactory(connectionFactory);
//		return rabbitTemplate;
//	}
}
