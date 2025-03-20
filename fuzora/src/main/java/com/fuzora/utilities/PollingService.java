package com.fuzora.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuzora.protocol.HTTPService;

@Component
public class PollingService implements Supplier<Map<String, Object>> {
//	Defines a thread mechanism attached on any of the protocol to do polling at certain interval.
	private final RestTemplate restTemplate = new RestTemplate();
	private static final String API_URL = "https://api.example.com/data";

	@Autowired
	HTTPService httpService;

	public void poll() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		Runnable pollingTask = () -> {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> httpMap = new HashMap<>();
			httpMap.put("httpType", "GET");
			httpMap.put("url", "https://fuzora.free.beeceptor.com");
//			httpMap.put("body",new JsonNode());
//			httpService.apply(httpMap);

		};
		scheduler.scheduleAtFixedRate(pollingTask, 0, 10, TimeUnit.SECONDS);
	}

	@Override
	public Map<String, Object> get() {
		
		return null;
	}
}
