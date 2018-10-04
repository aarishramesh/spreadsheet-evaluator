package com.spreadsheet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class to represent a cell in the spreadsheet
 *
 */
public class Cell {

	private final Pattern splitRegex = Pattern.compile("\\s+");

	private final int cellNumber;
	private final int row;
	private final int col;
	private final String content;
	private List<Token> tokens = new ArrayList<Token>();
	private boolean evaluated;

	public Cell(int cellNumber, int row, int col, String content) throws RuntimeException {
		this.cellNumber = cellNumber;
		this.row = row;
		this.col = col;
		this.content = content;
		this.parse();
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public boolean isEvaluated() {
		return evaluated;
	}

	public void setEvaluated(boolean evaluated) {
		this.evaluated = evaluated;
	}

	public Pattern getSplitRegex() {
		return splitRegex;
	}


	public int getRow() {
		return row;
	}


	public int getCol() {
		return col;
	}


	public String getContent() {
		return content;
	}

	private void parse() throws RuntimeException {
		String[] strArray = splitRegex.split(content);
		TokenFactory tokenFactory = new TokenFactory();
		for (String s : strArray) {
			Token tok = tokenFactory.makeToken(s);
			this.tokens.add(tok);
		}
	}
}
