package com.fuzora.pipeline;

import org.springframework.stereotype.Component;

@Component
public class PipelineRunner {

	private DataHandler filterHandler;

	private DeadLetterSender deadLetterSender;

	PipelineRunner(DeadLetterSender deadLetterSender) {
		this.deadLetterSender = deadLetterSender;
	}

	public void runPipeline(String message) {
		DataContext context = new DataContext();
		context.setRawData(message);
		filterHandler.handle(context);
	}

	public void setup(DataHandler filterHandler, DataHandler transformHandler, DataHandler actionHandler) {
		this.filterHandler = filterHandler;
		
		//chain the steps
		filterHandler.setNext(transformHandler).setNext(actionHandler);

		//set dead letter for steps
		filterHandler.setDeadLetterSender(deadLetterSender);
		transformHandler.setDeadLetterSender(deadLetterSender);
		actionHandler.setDeadLetterSender(deadLetterSender);
	}

}
