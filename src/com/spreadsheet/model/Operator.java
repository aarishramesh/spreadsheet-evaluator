package com.spreadsheet.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Class to represent all the supported operators. All the operators and their logic is encapsulated here. To support
 * new operator define them here.
 *
 */
public enum Operator {

	ADD("+"), SUB("-"), MUL("*"), DIV("/"), INC("++"), DEC("--");

	// Reverse-lookup map for getting a Operator from an operator string
	private static final Map<String, Operator> lookup = new HashMap<String, Operator>();

	static {
		for (Operator op : Operator.values())
			lookup.put(op.getOperator(), op);
	}

	private final String operator;

	private Operator(String op) {
		operator = op;
	}

	public static Operator get(String op) {
		return lookup.get(op);
	}

	public static boolean isValidOperator(String op) {
		return get(op) != null;
	}

	public String getOperator() {
		return operator;
	}

	public Stack<Double> apply(Stack<Double> RPNStack) throws IllegalArgumentException {
		double op1, op2;
		switch (this) {
			case ADD:
				op1 = RPNStack.pop();
				op2 = RPNStack.pop();
				RPNStack.push(op2 + op1);
				break;
			case SUB:
				op1 = RPNStack.pop();
				op2 = RPNStack.pop();
				RPNStack.push(op2 - op1);
				break;
			case MUL:
				op1 = RPNStack.pop();
				op2 = RPNStack.pop();
				RPNStack.push(op2 * op1);
				break;
			case DIV:
				op1 = RPNStack.pop();
				op2 = RPNStack.pop();
				if (op1 == 0) {
					throw new IllegalArgumentException("Error: Cannot divide by 0");
				}
				RPNStack.push(op2 / op1);
				break;
			case INC:
				op1 = RPNStack.pop();
				RPNStack.push(++op1);
				break;
			case DEC:
				op1 = RPNStack.pop();
				RPNStack.push(--op1);
				break;
		}
		return RPNStack;
	}
}