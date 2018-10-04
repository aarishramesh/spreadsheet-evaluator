package com.spreadsheet.model;

/**
 * Class to represent Operators as Tokens
 *
 */
public class OperatorToken extends Token {

	public OperatorToken(Operator operator) {
		setToken(operator.getOperator());
	}

	public Operator getParsedValue() {
		return Operator.get(getToken());
	}

}
