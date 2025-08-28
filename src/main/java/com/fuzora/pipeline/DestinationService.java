package com.fuzora.pipeline;

import org.springframework.stereotype.Component;

import com.fuzora.exception.DataHandlerException;

@Component
public class DestinationService extends DataHandler {

	@Override
	protected void process(DataContext context) throws DataHandlerException {
		System.out.println("data came in destination" + context.toString());
	}

}
