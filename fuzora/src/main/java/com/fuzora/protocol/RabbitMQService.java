package com.fuzora.protocol;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQService {

	private ConnectionFactory connectionFactory;
	private RabbitTemplate rabbitTemplate;

	public void createConnection(String host, int port, String username, String password, String virtualHost) {
		this.connectionFactory = createConnectionFactory(host, port, username, password, virtualHost);
		this.rabbitTemplate = new RabbitTemplate(this.connectionFactory);
	}

	private ConnectionFactory createConnectionFactory(String host, int port, String username, String password,
			String virtualHost) {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setVirtualHost(virtualHost);
		return factory;
	}

	public Queue declareQueue(String queue) {
		return new Queue(queue, true);
	}

	public DirectExchange declareExchange(String exchange) {
		return new DirectExchange(exchange);
	}

	public Binding declareBinding(Queue queue, DirectExchange exchange, String routingKey) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	public RabbitTemplate getRabbitTemplate() {
		return this.rabbitTemplate;
	}

	public ConnectionFactory getConnectionFactory() {
		return this.connectionFactory;
	}
}
