package com;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		Ba b = new Ba();
		Y y = b.getClass().getAnnotation(Y.class);
		Arrays.asList(y.basePackages()).forEach(System.out::println);
	}
}

@Y(basePackages = { "abc" })
class Ba {
	String a = "";
}
