package com.fuzora.pipeline;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeadLetterSender {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Value("${deadletter.queue.name:dead-letter-queue}")
//    private String deadLetterQueue;

	public void send(DataContext context) {

		log.warn("Sending to Dead Letter Queue: " + context.getRawData() + " with error: " + context.getErrorMessage());

//        rabbitTemplate.convertAndSend(deadLetterQueue, context.rawData + " | Error: " + context.errorMessage);
	}
}
