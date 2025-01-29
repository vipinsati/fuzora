package com.fuzora.amqp;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class AMQPInput implements Consumer<Map<String, String>> {

	@Override
	public void accept(Map<String, String> t) {
		// TODO Auto-generated method stub

	}

}
