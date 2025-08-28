package com.fuzora.transformer.runner;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuzora.exception.DataHandlerException;
import com.fuzora.pipeline.DataContext;
import com.fuzora.pipeline.DataHandler;
import com.fuzora.transformer.JsonTransformer;

@Service("transformService")
public class Transformer extends DataHandler {

	private final TransformerConfig config;

	public Transformer(TransformerConfig config) {
		this.config = config;
	}

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void process(DataContext context) throws DataHandlerException {
		try {
			System.out.println(context.getFilteredData());
			JsonNode input = mapper.readTree(context.getFilteredData());
			System.out.println(input);
			JsonTransformer transformer = new JsonTransformer();
			JsonNode output = transformer.transform(input, config.getSchema());
			context.setTransformedData(output.asText());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
