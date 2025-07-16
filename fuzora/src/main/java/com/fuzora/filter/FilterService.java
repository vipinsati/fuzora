package com.fuzora.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuzora.pipeline.DataContext;
import com.fuzora.pipeline.DataHandler;

@Component("filterService")
public class FilterService extends DataHandler {
	private static final Logger LOG = LoggerFactory.getLogger(FilterService.class);

	FilterConfiguration filterConfig;

	public FilterService(FilterConfiguration filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
	public void process(DataContext context) throws Exception {
		String rawData = context.getRawData();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataNode;

		dataNode = mapper.readTree(rawData);
		FilterNode f = filterConfig.getFilterNode();
		FilterResult result = f.evaluate(dataNode);
		if (result.toMap().get("status").equals("success")) {
			LOG.info("Filtering successfull {}", context);
			context.setFilteredData(rawData);// = rawData;
		} else {
			context.setFailed(true);
			context.setErrorMessage("Filtering failed for the input");
			LOG.info("Filtering failed, skipping next process {}", context);
		}

	}

}
