package com.fuzora.pipeline;

import org.springframework.stereotype.Component;

@Component("transformService")
public class TransformService extends DataHandler {

	@Override
	protected void process(DataContext context) throws Exception {
		System.out.println("data came in transform" + context.toString());
		context.setTransformedData(context.getFilteredData());
	}

}
