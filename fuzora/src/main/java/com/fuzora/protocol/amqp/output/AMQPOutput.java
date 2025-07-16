package com.fuzora.protocol.amqp.output;

import java.io.IOException;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Service;

import com.fuzora.pipeline.DataContext;
import com.fuzora.pipeline.DataHandler;
import com.rabbitmq.client.Channel;

@Service("amqp_output")
public class AMQPOutput extends DataHandler {

	AMQPOutputConfig amqpOutputConfig;

	public AMQPOutput(AMQPOutputConfig amqpOutputConfig) {
		this.amqpOutputConfig = amqpOutputConfig;
	}

	@Override
	protected void process(DataContext context) throws Exception {
		String message = context.getTransformedData();
		ConnectionFactory factory = amqpOutputConfig.getRabbitMQService().getConnectionFactory();

		try (Connection connection = factory.createConnection(); Channel channel = connection.createChannel(false);) {
			channel.exchangeDeclare(amqpOutputConfig.getExchange(), "topic", true);

			String queueName = amqpOutputConfig.getQueue();
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(amqpOutputConfig.getQueue(), amqpOutputConfig.getExchange(), "");
			channel.basicPublish("", queueName, null, message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
