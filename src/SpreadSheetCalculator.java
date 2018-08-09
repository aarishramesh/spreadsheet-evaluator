import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * TODOS
 * 
 * - Modularisation & code cleanup
 * - Customise exceptions
 * - Unit tests
 * 
 * @author polymath
 *
 */
public class SpreadSheetCalculator {

	static String[][] resultArr;

	static String[][] expr;

	static PrettyPrinter pp = new PrettyPrinter(System.out);
	
	static int noOfColumns;
	
	static int noOfRows;
	
	boolean cyclicDependencyPresent = false;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String rowColumns = sc.nextLine();
		int columns = Integer.parseInt(rowColumns.split(" ")[0]);
		noOfColumns = columns;
		int rows = Integer.parseInt(rowColumns.split(" ")[1]);
		noOfRows = rows;
		
		expr = new String[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {				
				expr[i][j] = sc.nextLine();
			}
		}

		resultArr = new String[rows][columns];
		Set<Integer> visitedCells = new HashSet<Integer>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String cellExpr = expr[i][j];
				visitedCells.add((i * columns) + j);
				resultArr[i][j] = evalExpr(i, j, cellExpr, visitedCells);
				visitedCells.clear();
			}
		}

		printCells();
		pp.print(expr);
		pp.print(resultArr);
		sc.close();
	}

	private static void printCells() {
		for (int i = 0; i < expr.length; i++) {
			for (int j = 0; j < expr[0].length; j++) {
				System.out.println(resultArr[i][j]);
			}
		}
	}

	private static String evalExpr(int exprRow, int exprColumn, String nodeExpr, Set<Integer> visitedCells) {
		if (nodeExpr.equals("Cyclic dependency")) {
			return "Cyclic dependency";
		}
		
		String[] operands = nodeExpr.split(" ");
		StringBuilder postfixExpr = new StringBuilder();
		for (String operand : operands) {
			if (!isInteger(operand) && !isOperator(operand)) {
				char rowChar = operand.charAt(0);
				int row = rowChar - 65;
				int column = Integer.parseInt(operand.substring(1)) - 1;

				if (resultArr[row][column] != null) {
					if (resultArr[row][column].equals("Cyclic dependency")) {
						resultArr[exprRow][exprColumn] = "Cyclic dependency";
						return "Cyclic dependency";
					} else {
						postfixExpr.append(resultArr[row][column] + " ");
					}
				} else {
					if (!visitedCells.contains((row * noOfColumns) + column)) {
						String exprResult = evalExpr(row, column, expr[row][column], visitedCells);
						if (exprResult.contains("Cyclic dependency")) {
							resultArr[exprRow][exprColumn] = "Cyclic dependency";
							return resultArr[exprRow][exprColumn];
						}
						visitedCells.add((row * noOfColumns) + column);
						postfixExpr.append(exprResult + " ");
					} else {
						resultArr[row][column] = "Cyclic dependency";
						return "Cyclic dependency";
					}
				}
			} else {
				postfixExpr.append(operand + " ");
			}
		}

		resultArr[exprRow][exprColumn] = PostfixExprEval.evaluate(postfixExpr.toString()) + "";
		return resultArr[exprRow][exprColumn];
	}

	private static boolean isInteger(String operand) {
		try {
			Integer.parseInt(operand);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	private static boolean isOperator(String operand) {
		if (operand.equals("+") || operand.equals("-") || operand.equals("*")
				|| operand.equals("/") || operand.equals("--") || operand.equals("++"))
			return true;
		return false;
	}

}

