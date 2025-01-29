package com.fuzora.amqp;

import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class AMQPInputConfig implements Function<Map<String, String>, Map<String, String>> {

	@Override
	public Map<String, String> apply(Map<String, String> t) {
		
		return null;
	}

}
