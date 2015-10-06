package com.github.eeval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.eeval.constants.Operator;
import com.github.eeval.operation.Operation;
import com.github.eeval.operation.OperationFactory;

public class EEVal {
	private static OperationFactory opFactory = new OperationFactory();
	private static final String TOKEN_REGEX = "([0-9]+(\\.[0-9]+)?)|\\+|\\*|\\-|\\/|\\(|\\)|\\^";

	/**
	 * Returns evaluated expression to Double
	 * 
	 * @param expression
	 * @return
	 */
	public static Double evalToDouble(String expression) {
		if (expression == null) {
			throw new IllegalArgumentException("expression cannot be null");
		}
		List<String> postfix = toPostfix(expression);
		Stack<Double> stackOfCalculations = new Stack<Double>();
		for (String s : postfix) {
			if (isNumber(s)) {
				stackOfCalculations.push(Double.valueOf(s));
			} else if (isOperator(s)) {
				Operator op = Operator.fromSign(s);
				Double b = stackOfCalculations.pop();
				Double a = stackOfCalculations.pop();
				Operation operationExecutor = opFactory.getOperation(op);
				Double result = operationExecutor.doOperation(a, b);
				stackOfCalculations.push(result);
			}
		}
		return stackOfCalculations.pop();
	}

	/**
	 * Returns evaluated expression to BigDecimal
	 * 
	 * @param expression
	 * @return
	 */
	public static BigDecimal evalToBigDecimal(String expression) {
		if (expression == null) {
			throw new IllegalArgumentException("expression cannot be null");
		}
		return new BigDecimal(evalToDouble(expression));
	}

	/**
	 * Returns expression in postfix(reverse polish notation), as tokens in the
	 * list
	 * 
	 * @param expression
	 * @return
	 */
	public static List<String> toPostfix(String expression) {
		if (expression == null) {
			throw new IllegalArgumentException("expression cannot be null");
		}
		List<String> infixTokens = new ArrayList<String>();
		Pattern p = Pattern.compile(TOKEN_REGEX);
		Matcher m = p.matcher(expression);
		while (m.find()) {
			infixTokens.add(m.group());
		}
		Stack<Operator> operators = new Stack<Operator>();
		List<String> postfixTokens = new ArrayList<String>();
		for (String s : infixTokens) {
			if (isOperator(s)) {
				Operator currOp = Operator.fromSign(s);
				while (operatorsNotEmpty(operators) && shouldPeekOperator(operators, currOp)) {
					Operator op = operators.pop();
					postfixTokens.add(op.getSign());
				}
				operators.push(currOp);
			} else if (Operator.LEFT.getSign().equals(s)) {
				operators.push(Operator.fromSign(s));
			} else if (Operator.RIGHT.getSign().equals(s)) {
				while (operatorsNotEmpty(operators) && !(operators.peek() == Operator.LEFT)) {
					postfixTokens.add(operators.pop().getSign());
				}
				if (operatorsNotEmpty(operators)) {
					operators.pop();
				}
			} else {
				if (isNumber(s)) {
					postfixTokens.add(s);
				}
			}
		}
		while (operatorsNotEmpty(operators)) {
			postfixTokens.add(operators.pop().getSign());
		}
		return postfixTokens;
	}

	private static boolean shouldPeekOperator(Stack<Operator> operators, Operator currOp) {
		return (currOp.isLeftAssociative() && currOp.lowerOrEqualPrecedence(operators.peek()))
				|| (currOp.isRightAssociative() && currOp.lowerPrecedence(operators.peek()));
	}

	private static boolean operatorsNotEmpty(Stack<Operator> operators) {
		return !operators.isEmpty();
	}

	private static boolean isOperator(String c) {
		if ("+".equals(c) || "-".equals(c) || "/".equals(c) || "*".equals(c) || "^".equals(c)) {
			return true;
		}
		return false;
	}

	private static boolean isNumber(String input) {
		try {
			new BigDecimal(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
