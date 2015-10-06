package com.github.eeval.constants;

public enum Operator {
	PLUS("+", 1, Associativity.LEFT), MINUS("-", 1, Associativity.LEFT), TIMES("*", 2, Associativity.LEFT), DIVIDE("/",
			2, Associativity.LEFT), POWER("^", 3, Associativity.RIGHT), LEFT("(", 0, null), RIGHT(")", 0, null);

	private String sign;
	private int precedence;
	private Associativity associativity;

	private Operator(String sign, int precedence, Associativity associativity) {
		this.sign = sign;
		this.precedence = precedence;
		this.associativity = associativity;
	}

	public static Operator fromSign(String sign) {
		for (Operator op : values()) {
			if (op.sign.equals(sign)) {
				return op;
			}
		}
		throw new IllegalArgumentException("No operator for sign " + sign);
	}

	public boolean lowerOrEqualPrecedence(Operator operator) {
		if (this.precedence <= operator.precedence) {
			return true;
		}
		return false;
	}

	public boolean lowerPrecedence(Operator operator) {
		if (this.precedence < operator.precedence) {
			return true;
		}
		return false;
	}

	public boolean isLeftAssociative() {
		return this.associativity == Associativity.LEFT;
	}

	public boolean isRightAssociative() {
		return !isLeftAssociative();
	}

	public String getSign() {
		return this.sign;
	}

	public Associativity getAssociativity() {
		return associativity;
	}
}
