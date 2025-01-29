package com.fuzora.protocol;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuzora.reader.ConfigReader;

@Service
public class AMQPService  implements Consumer<String>{

	@Autowired
	ConfigReader configReader;
	
	@Override
	public void accept(String t) {
		System.out.println(configReader.getTriggerConfig().toPrettyString());
		
	}

	//Defines consumer and producer
	
}