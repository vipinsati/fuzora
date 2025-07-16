package com.fuzora.protocol.amqp.input;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Component;

import com.fuzora.pipeline.PipelineRunner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

@Component("amqp_input")
public class AMQPInput implements BooleanSupplier {

	private PipelineRunner pipelineRunner;

	private AMQPInputConfig amqpInputConfig;

	public AMQPInput(PipelineRunner pipelineRunner, AMQPInputConfig amqpInputConfig) {
		this.pipelineRunner = pipelineRunner;
		this.amqpInputConfig = amqpInputConfig;
	}

	@Override
	public boolean getAsBoolean() {
		ConnectionFactory factory = amqpInputConfig.getRabbitMQService().getConnectionFactory();
		try (Connection connection = factory.createConnection(); Channel channel = connection.createChannel(false);) {

			channel.exchangeDeclare(amqpInputConfig.getExchange(), "topic", true);

			String queueName = amqpInputConfig.getQueue();
			channel.queueDeclare(queueName, true, false, false, null);

			channel.queueBind(amqpInputConfig.getQueue(), amqpInputConfig.getExchange(), "");

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

				Map<String, Object> ret = new HashMap<>();
				ret.put("body", message);
				if (!message.isEmpty())
					pipelineRunner.runPipeline(message);
			};
			channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
			});

		} catch (Exception e) {

		}
		return true;
	}
}
