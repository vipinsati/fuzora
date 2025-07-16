package com.fuzora.pipeline;

import org.springframework.stereotype.Component;

@Component
public class DeadLetterSender {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Value("${deadletter.queue.name:dead-letter-queue}")
//    private String deadLetterQueue;

	public void send(DataContext context) {
		System.out.println(
				"Sending to Dead Letter Queue: " + context.getRawData() + " with error: " + context.getErrorMessage());
//        rabbitTemplate.convertAndSend(deadLetterQueue, context.rawData + " | Error: " + context.errorMessage);
	}
}
