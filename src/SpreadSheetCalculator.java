import java.util.Scanner;

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
	
	static Float[][] resultArr;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String rowColumns = sc.next();
		int columns = Integer.parseInt(rowColumns.split(" ")[0]);
		int rows = Integer.parseInt(rowColumns.split(" ")[1]);
		
		String[][] expr = new String[rows][columns];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				expr[i][j] = sc.next();
			}
		}
		
		resultArr = new Float[rows][columns];
		boolean[][] visitedCells = new boolean[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String cellExpr = expr[i][j];
				try {
					evalExpr(i, j, cellExpr, visitedCells);
				} catch (Exception ex) {
					System.out.println(" Cyclic depdency detected");
					sc.close();
					return;
				}
				cleanupVisitedCells(visitedCells);
			}
		}
		sc.close();
	}
	
	private static float evalExpr(int exprRow, int exprColumn, String nodeExpr, boolean[][] visitedCells) throws Exception {
		String[] operands = nodeExpr.split(" ");
		StringBuilder postfixExpr = new StringBuilder();
		for (String operand : operands) {
			if (!isInteger(operand) && !isOperator(operand)) {
				char rowChar = operand.charAt(0);
				int row = rowChar - 44;
				int column = Integer.parseInt(operand.substring(1)) - 1;
				
				if (resultArr[row][column] != null) {
					postfixExpr.append(resultArr[row][column]);
				} else {
					if (!visitedCells[row][column]) {
						
					} else {
						throw new Exception();
					}
				}
			}
			postfixExpr.append(operand);
			// check if the operand has a calculated cell value
			// 
		}
		
		resultArr[exprRow][exprColumn] = PostfixExprEval.evaluate(postfixExpr.toString());
		return resultArr[exprRow][exprColumn];
	}
	
	private static void cleanupVisitedCells(boolean[][] visitedCells) {
		for (int i = 0; i < visitedCells.length; i++) {
			for (int j = 0; j < visitedCells[0].length; i++) {
				visitedCells [i][j] = false;
			}
		}
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
				|| operand.equals("/"))
			return true;
		return false;
	}
 }

