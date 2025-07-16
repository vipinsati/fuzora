package com.fuzora.transformer;

import lombok.Data;

@Data
public class MappingRule {
	private String target;
	private String source;
	private TransformOperation transform;
	private Object defaultValue;

	// for arrays
	private boolean isArray;
	private String sourceField; // used inside array mapping

	
}
