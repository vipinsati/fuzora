package com.trigger;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component("transformer")
public class ZohoTransformer implements Function<String, String> {

	@Override
	public String apply(String input) {
		String output = "";
		if ("hello".equals(input))
			output = "it works";
		return output;
	}

}
