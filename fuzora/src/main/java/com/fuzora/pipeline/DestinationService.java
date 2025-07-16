package com.fuzora.pipeline;

import org.springframework.stereotype.Component;

@Component
public class DestinationService extends DataHandler {

	@Override
	protected void process(DataContext context) throws Exception {
		System.out.println("data came in destination" + context.toString());
	}

}
