package com.spreadsheet.model;

/**
 * Factory for getting appropriate Token from a given token String based on Factory Design Pattern
 *
 */
public class TokenFactory {

	private static final TokenFactory INSTANCE = new TokenFactory();
	
	private static TokenFactory getInstance() {
		return INSTANCE;
	}
	
	public Token makeToken(String str) throws RuntimeException {
		if (Operator.isValidOperator(str))
			return new OperatorToken(Operator.get(str));
		else if (str.matches(ReferenceToken.refPatternRegex))
			return new ReferenceToken(str);
		else if (str.matches(ValueToken.valuePatternRegex))
			return new ValueToken(str);
		else
			throw new RuntimeException("Error: Invalid token: " + str);
	}
}
