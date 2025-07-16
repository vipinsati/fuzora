package com.fuzora.protocol.http.polling;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.fuzora.pipeline.PipelineRunner;
import com.fuzora.protocol.http.model.HTTPServiceRequest;
import com.fuzora.protocol.http.service.HTTPService;

@Service("http_polling_input")
public class HttpPollingInput implements Supplier<Map<String, Object>> {

	private HTTPService httpService;

	public HttpPollingInput(HTTPService httpService) {
		this.httpService = httpService;
	}

	private PipelineRunner runner;

	private HttpPollingConfig httpPollingConfig;

	public HttpPollingInput(HttpPollingConfig httpPollingConfig) {
		this.httpPollingConfig = httpPollingConfig;
	}

	@Override
	public Map<String, Object> get() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		Runnable pollingTask = () -> {

			HTTPServiceRequest hsr = new HTTPServiceRequest();
			hsr.setRequestUrl(httpPollingConfig.getUrl());
			hsr.setRequestMethod(httpPollingConfig.getMethod());
			hsr.setAuthType(httpPollingConfig.getAuthType());
			hsr.setRequestBody(httpPollingConfig.getBody());
			hsr.setHeaders(httpPollingConfig.getHeaders());
			hsr.setAuth(httpPollingConfig.getAuthBody());

			Map<String, Object> res = httpService.apply(hsr);

			runner.runPipeline((String) res.get("body"));

		};
		scheduler.scheduleAtFixedRate(pollingTask, 0, httpPollingConfig.getPollingInterval(), TimeUnit.MINUTES);

		Map<String, Object> reqPam = null;
		return reqPam;
	}

}
