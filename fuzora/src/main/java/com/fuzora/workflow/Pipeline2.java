package com.fuzora.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

@Service("pipline")
public class Pipeline2<T, R> {
	private Supplier<T> sourceConsumer; // First step in the pipeline
	private final List<Function<?, ?>> pipes = new ArrayList<>();

	public Pipeline2<T, R> setSourceConsumer(Supplier<T> sourceConsumer) {
		this.sourceConsumer = sourceConsumer;
		return this;
	}

	public <X, Y> Pipeline2<T, R> addPipe(Function<X, Y> pipe) {
		pipes.add(pipe);
		return this;
	}

	@SuppressWarnings("unchecked")
	public void startPipeline(T input) {
		Object data = input;

		if (sourceConsumer != null) {
			data = sourceConsumer.get(); // Consumer starts the pipeline
		}

		// Process through function pipes
		for (Function<?, ?> pipe : pipes) {
			data = ((Function<Object, Object>) pipe).apply(data);
		}
	}
}
