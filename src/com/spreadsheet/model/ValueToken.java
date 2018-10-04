package com.spreadsheet.model;

/**
 * Class to represent Values(operands) as Tokens
 *
 * @author Rohit Sinha
 */
public class ValueToken extends Token {

	public static final String valuePatternRegex = "[+-]?\\d+";

	public ValueToken(String str) {
		setToken(str);
	}

	public double getParsedValue() {
		return Double.parseDouble(getToken());
	}
}
