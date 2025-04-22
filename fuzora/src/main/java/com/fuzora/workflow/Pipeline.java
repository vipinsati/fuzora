package com.fuzora.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

@Service("pipline")
public class Pipeline<T, R> {

	private Supplier<T> sourceConsumer;

	private final List<Function<?, ?>> pipes = new ArrayList<>();

	public Pipeline<T, R> setSourceConsumer(Supplier<T> sourceConsumer) {
		this.sourceConsumer = sourceConsumer;
		return this;
	}

	public <X, Y> Pipeline<T, R> addPipe(Function<X, Y> pipe) {
		pipes.add(pipe);
		return this;
	}

	@SuppressWarnings("unchecked")
	public void startPipeline(T input) {
		Object data = input;
		// Process through function pipes
		for (Function<?, ?> pipe : pipes) {
			data = ((Function<Object, Object>) pipe).apply(data);
		}
	}
}