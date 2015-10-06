package com.github.eeval.operation;

import com.github.eeval.constants.Operator;

public class OperationFactory {
	@SuppressWarnings("incomplete-switch")
	public Operation getOperation(Operator op) {
		switch (op) {
		case PLUS:
			return new AddOp();
		case MINUS:
			return new SubtractOp();
		case TIMES:
			return new MultiplierOp();
		case DIVIDE:
			return new DividerOp();
		case POWER:
			return new PowerOp();
		}
		throw new UnsupportedOperationException("op " + op + " not supported!");
	}
}
