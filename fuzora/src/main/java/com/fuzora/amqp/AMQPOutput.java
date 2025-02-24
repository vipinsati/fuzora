package com.fuzora.amqp;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

@Service
public class AMQPOutput implements Function<Map<String, Object>, Map<String, Object>> {

	@Autowired
	AMQPOutputConfig amqpOutputConfig;

	@Override
	public Map<String, Object> apply(Map<String, Object> t) {
		
		String message = (String) t.get("body");
		
		ConnectionFactory factory = amqpOutputConfig.getRabbitMQService().getConnectionFactory();
		Connection connection = factory.createConnection();
		Channel channel = connection.createChannel(false);

		try {
			channel.exchangeDeclare(amqpOutputConfig.getExchange(), "topic", true);

			String queueName = amqpOutputConfig.getQueue();
			channel.queueBind(amqpOutputConfig.getQueue(), amqpOutputConfig.getExchange(), "");
			channel.basicPublish("", queueName, null, message.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
