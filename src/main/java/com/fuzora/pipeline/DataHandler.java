package com.fuzora.pipeline;

import com.fuzora.exception.DataHandlerException;

public abstract class DataHandler {
	protected DataHandler next;
	protected DeadLetterSender deadLetterSender;

	public DataHandler setNext(DataHandler next) {
		this.next = next;
		return next;
	}

	public void setDeadLetterSender(DeadLetterSender sender) {
		this.deadLetterSender = sender;
	}

	public final void handle(DataContext context) {
		try {
			process(context);
		} catch (Exception e) {
			context.setFailed(true);
			context.setErrorMessage(e.getMessage());
		}

		// Always send to DLQ if any terminal flag is set
		if (context.isFailed() || context.isShouldStop() || context.isToSkip()) {
			if (deadLetterSender != null) {
				deadLetterSender.send(context);
			}
			return; // ⛔ Stop the flow regardless
		}

		// Continue only if all flags are false
		if (next != null) {
			next.handle(context);
		}
	}

	protected abstract void process(DataContext context) throws DataHandlerException;
}
