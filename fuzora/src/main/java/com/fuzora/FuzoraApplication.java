package com.fuzora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fuzora", "com.trigger" })
public class FuzoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuzoraApplication.class, args);
	}

}
