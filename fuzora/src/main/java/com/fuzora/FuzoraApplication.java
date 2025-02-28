package com.fuzora;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.fuzora.amqp.AMQPInput;
import com.fuzora.amqp.AMQPOutput;
import com.fuzora.reader.ConfigReader;
import com.fuzora.workflow.Pipeline2;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fuzora", "com.trigger" })
public class FuzoraApplication implements CommandLineRunner {

	@Autowired(required = false)
	Function<String, String> transformer;

	public static void main(String[] args) {
		SpringApplication.run(FuzoraApplication.class, args);
	}

	@Autowired
	ConfigReader configReader;

	@Autowired
	AMQPInput amqpInput;

	@Autowired
	AMQPOutput amqpOutput;

	@Autowired
	Pipeline2<Map<String, Object>, Map<String, Object>> pipeline;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {
		configReader.readConfigFiles();

		pipeline.setSourceConsumer(amqpInput)
				.addPipe(amqpOutput); // First step
		amqpInput.get();
	}

}
