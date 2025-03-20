package com.fuzora.http;

import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.fuzora.utilities.PollingService;

@Component
public class HttpPollingInput implements Supplier<Map<String, Object>> {

	HttpPollingConfig httpPollingConfig;
	PollingService pollingService;

	public HttpPollingInput(HttpPollingConfig httpPollingConfig, PollingService pollingService) {
		this.httpPollingConfig = httpPollingConfig;
		this.pollingService = pollingService;
	}

	@Override
	public Map<String, Object> get() {
		Map<String, Object> reqPam;
		return null;
	}
	
	

}
