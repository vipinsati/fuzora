package com.fuzora.workflow;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.fuzora.reader.ConfigReader;

@Service
public class WorkflowExecutor implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private ConfigReader configReader;

	@Autowired
	private Pipeline<Map<String, Object>, Map<String, Object>> pipeline;

	public WorkflowExecutor(ConfigReader configReader) {
		this.configReader = configReader;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public void execute() {
		String triggerName = this.configReader.getTriggerProtocol();
		String actionName = this.configReader.getActionProtocol();

		Supplier<Map<String, Object>> trigger = (Supplier<Map<String, Object>>) this.applicationContext
				.getBean(triggerName);
		Function<Map<String, Object>, Map<String, Object>> action = (Function<Map<String, Object>, Map<String, Object>>) this.applicationContext
				.getBean(actionName);

		this.pipeline.setSourceConsumer(trigger).addPipe(action);
		trigger.get();

	}

}
