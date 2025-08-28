package com.fuzora.protocol.amqp.output;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Service;

import com.fuzora.constants.AppConstants;
import com.fuzora.pipeline.DataContext;
import com.fuzora.pipeline.DataHandler;
import com.rabbitmq.client.Channel;

@Service(AppConstants.AMQP_OUTPUT)
public class AMQPOutput extends DataHandler {

	AMQPOutputConfig amqpOutputConfig;

	public AMQPOutput(AMQPOutputConfig amqpOutputConfig) {
		this.amqpOutputConfig = amqpOutputConfig;
	}

	@Override
	protected void process(DataContext context) {
		String message = context.getTransformedData();
		ConnectionFactory factory = amqpOutputConfig.getRabbitMQService().getConnectionFactory();

		try (Connection connection = factory.createConnection(); Channel channel = connection.createChannel(false);) {
			channel.exchangeDeclare(amqpOutputConfig.getExchange(), "topic", true);
			System.out.println(connection.hashCode());
			String queueName = amqpOutputConfig.getQueue();
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(amqpOutputConfig.getQueue(), amqpOutputConfig.getExchange(), amqpOutputConfig.getQueue());
			channel.basicPublish(amqpOutputConfig.getExchange(), amqpOutputConfig.getQueue(), null, message.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
