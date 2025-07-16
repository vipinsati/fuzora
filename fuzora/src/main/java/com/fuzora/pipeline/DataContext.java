package com.fuzora.pipeline;

import lombok.Data;

@Data
public class DataContext {
	private String rawData;
	private String filteredData;
	private String transformedData;
	private boolean shouldStop = false;
	private boolean isFailed = false;
	private boolean toSkip = false;
	private String errorMessage;

	@Override
	public String toString() {
		return "DataContext [rawData=" + rawData + ", filteredData=" + filteredData + ", transformedData="
				+ transformedData + ", shouldStop=" + shouldStop + ", isFailed=" + isFailed + ", errorMessage="
				+ errorMessage + "]";
	}

}
