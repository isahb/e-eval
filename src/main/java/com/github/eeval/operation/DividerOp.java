package com.github.eeval.operation;

public class DividerOp implements Operation {

	public Double doOperation(Double a, Double b) {
		if (b == 0) {
			throw new IllegalArgumentException("Cannot divide by zero");
		}
		return a / b;
	}
}
