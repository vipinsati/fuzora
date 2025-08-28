package com.fuzora.workflow;

import java.util.function.BooleanSupplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.fuzora.external.impl.Trigger;
import com.fuzora.reader.ConfigReader;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkflowExecutor implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private ConfigReader configReader;

	public WorkflowExecutor(ConfigReader configReader, @Autowired(required = false) Trigger externalTrigger) {
		this.configReader = configReader;
		this.externalTrigger = externalTrigger;
	}

	Trigger externalTrigger;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void execute() {
		String triggerName = this.configReader.getTriggerProtocol();

		if (externalTrigger != null) {
			log.info("Running extern trigger bean");
			externalTrigger.getAsBoolean();
		} else {
			log.info("Running internal trigger bean: " + triggerName);
			BooleanSupplier trigger = (BooleanSupplier) this.applicationContext.getBean(triggerName);
			trigger.getAsBoolean();
		}

	}

}
