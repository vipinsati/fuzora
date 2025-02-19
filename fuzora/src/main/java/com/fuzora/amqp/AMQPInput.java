package com.fuzora.amqp;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

@Service
public class AMQPInput implements Consumer<Map<String, Object>> {

	@Autowired
	AMQPInputConfig amqpInputConfig;

	@Override
	public void accept(Map<String, Object> t) {
		try {
			startListening();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startListening() throws IOException {
		ConnectionFactory factory = amqpInputConfig.getRabbitMQService().getConnectionFactory();
		Connection connection = factory.createConnection();
		Channel channel = connection.createChannel(false);

		channel.exchangeDeclare(amqpInputConfig.getExchange(), "topic", true);
		String queueName = amqpInputConfig.getQueue();
		channel.queueBind(amqpInputConfig.getQueue(), amqpInputConfig.getExchange(), "");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}
}
