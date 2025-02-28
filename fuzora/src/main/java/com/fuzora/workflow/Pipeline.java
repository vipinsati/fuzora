package com.fuzora.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Pipeline<I, O> {
	private final List<Function<?, ?>> steps = new ArrayList<>();
	private Supplier<?> sourcePipe = null;

	public <T> Pipeline<I, T> addStep(Function<O, T> step) {
		steps.add(step);
		return (Pipeline<I, T>) this;
	}

	public <R> Pipeline<I, O> addSourcePipe(Supplier<R> sourcePipe) {
		this.sourcePipe = sourcePipe;
		return this;
	}

	public O execute(I input) throws Exception {
		Object result = input;

		if (sourcePipe != null) {
			result = sourcePipe.get();
		}

		for (Function step : steps) {
			try {
				result = step.apply(result);
			} catch (Exception e) {
				System.err.println("Pipeline execution failed at step: " + step.getClass().getSimpleName());
				throw e; // Stop execution on failure
			}
		}
		return (O) result;
	}

}
