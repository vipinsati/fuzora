package com.fuzora.workflow;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fuzora.reader.ConfigReader;

@Component
public class PostStartupRunner {

	private ConfigReader configReader;

	private WorkflowExecutor executor;

	public PostStartupRunner(ConfigReader configReader, WorkflowExecutor executor) {
		this.configReader = configReader;
		this.executor = executor;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() throws IOException {
		// read config files
		configReader.readConfigFiles();
		// based on config execute workflow
		executor.execute();
	}
}
