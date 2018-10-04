package com.spreadsheet;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.spreadsheet.model.Cell;
import com.spreadsheet.model.ReferenceToken;
import com.spreadsheet.model.Token;
import com.spreadsheet.util.PostfixExprEval;
import com.spreadsheet.util.PrettyPrinter;
import com.spreadsheet.util.Utils;

public class Spreadsheet {

	private Cell[][] cellMatrix;

	private String[][] resultMatrix;

	private int columns; // number of columns (width)
	private int rows; // number of rows (height)

	public void readInput(Scanner inputScanner) throws RuntimeException {

		columns = inputScanner.nextInt();
		rows = inputScanner.nextInt();
		inputScanner.nextLine();    // omit the newline

		cellMatrix = new Cell[rows][columns];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				String data = inputScanner.nextLine().trim().toUpperCase();
				int cellNumber = (row * columns) + col;
				cellMatrix[row][col] = new Cell(cellNumber, row, col, data);
			}
		}
	}

	public void process() {
		Set<Integer> visitedCells = new HashSet<Integer>();
		resultMatrix = new String[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {

				Cell cell = cellMatrix[i][j];
				visitedCells.add(cell.getCellNumber());
				resultMatrix[i][j] = evaluate(cell, visitedCells);
				visitedCells.clear();
			}
		}
	}

	private String evaluate(Cell cell, Set<Integer> visitedCells) {
		if (cell.getContent().equals("Cyclic dependency")) {
			return "Cyclic dependency";
		}
		StringBuilder postfixExpr = new StringBuilder();
		for (Token token : cell.getTokens()) {
			if (token instanceof ReferenceToken) {
				char rowChar = token.getToken().charAt(0);
				int row = rowChar - 65;
				int column = Integer.parseInt(token.getToken().substring(1)) - 1;

				if (resultMatrix[row][column] != null) {
					if (resultMatrix[row][column].equals("Cyclic dependency")) {
						//resultMatrix[cell.getRow()][cell.getCol()] = "Cyclic dependency";
						return "Cyclic dependency";
					} else {
						postfixExpr.append(resultMatrix[row][column] + " ");
					}
				} else {
					Cell dependentCell = cellMatrix[row][column];
					if (!visitedCells.contains(dependentCell.getCellNumber())) {

						String exprResult = evaluate(dependentCell, visitedCells);
						if (exprResult.contains("Cyclic dependency")) {
							//resultMatrix[cell.getRow()][cell.getCol()] = "Cyclic dependency";
							//return resultMatrix[cell.getRow()][cell.getCol()];
							return "Cyclic dependency";
						}
						visitedCells.add(dependentCell.getCellNumber());
						postfixExpr.append(exprResult + " ");
					} else {
						resultMatrix[row][column] = "Cyclic dependency";
						return "Cyclic dependency";
					}
				}
			} else {
				postfixExpr.append(token.getToken() + " ");
			}
		}
		resultMatrix[cell.getRow()][cell.getCol()] = PostfixExprEval.evaluate(postfixExpr.toString()) + "";
		return resultMatrix[cell.getRow()][cell.getCol()];
	}

	public void prettyPrint(boolean results) {
		String[][] matrix = new String[rows + 1][columns + 1];
		for (int i = 0; i <= rows; i++) {
			for (int j = 0; j <= columns; j++) {
				if (i == 0 || j == 0) {
					if (i == 0 && j == 0) {
						matrix[i][j] = " ";
					} else if (i == 0) {
						matrix[i][j] = String.valueOf(j);
					} else {
						matrix[i][j] = Utils.getRowName(i - 1);
					}
				} else {					
					matrix[i][j] = cellMatrix[i - 1][j - 1].getContent();
				}
			}
		}
		if (results) {
			System.out.println("Results:");
		} else {
			System.out.println("Inputs:");
		}
		final PrettyPrinter printer = new PrettyPrinter(System.out);
		printer.print(matrix);
	}
}
