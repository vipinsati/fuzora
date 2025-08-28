package com.fuzora.pipeline;

import org.springframework.stereotype.Component;

import com.fuzora.exception.DataHandlerException;

//@Component("transformService")
public class TransformService extends DataHandler {

	@Override
	protected void process(DataContext context) throws DataHandlerException {
		System.out.println("data came in transform" + context.toString());
		context.setTransformedData(context.getFilteredData());
	}

}
