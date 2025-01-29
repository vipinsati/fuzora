package com.fuzora;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fuzora.protocol.AMQPService;
import com.fuzora.reader.ConfigReader;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fuzora", "com.trigger" })
public class FuzoraApplication implements CommandLineRunner {

	@Autowired
	Function<String, String> transformer;

	public static void main(String[] args) {
		SpringApplication.run(FuzoraApplication.class, args);
	}

	@Autowired
	ConfigReader configReader;
	
	@Autowired
	AMQPService amqp;
	
	@Override
	public void run(String... args) throws Exception {
		configReader.readConfigFiles();
		amqp.accept("");
	}

}
