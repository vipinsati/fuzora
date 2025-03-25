package com.fuzora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fuzora.reader.ConfigReader;
import com.fuzora.workflow.WorkflowExecutor;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fuzora", "com.trigger" })
public class FuzoraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FuzoraApplication.class, args);
	}

	@Autowired
	ConfigReader configReader;

	@Autowired
	WorkflowExecutor executor;

	@Override
	public void run(String... args) throws Exception {
		// read config files
		configReader.readConfigFiles();
		// based on config execute workflow
		executor.execute();
	}

}
