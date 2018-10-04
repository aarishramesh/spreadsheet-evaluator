package com.spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.spreadsheet.util.PrettyPrinter;

public class SpreadsheetEvaluator {
	
	private static final SpreadsheetEvaluator INSTANCE = new SpreadsheetEvaluator();
	
	private static final Logger LOGGER = Logger.getLogger(Spreadsheet.class.getName());

	private Scanner inputScanner;
	
	private static final int STATUS_CODE_FAILURE = 1;
	private static final String PRETTY_PRINT_ARG1 = "--pretty";
	private static final String PRETTY_PRINT_ARG2 = "-p";
	private static boolean prettyPrint = true;
	static PrettyPrinter pp = new PrettyPrinter(System.out);

	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				if (!INSTANCE.validateInput(args)) {
					LOGGER.severe("Error: Invalid number of arguments");
					printUsage();
					System.exit(STATUS_CODE_FAILURE);
				}
				Spreadsheet sheet = new Spreadsheet();
				sheet.readInput(INSTANCE.inputScanner);
				sheet.process();

				sheet.prettyPrint(prettyPrint);
				
			} catch (FileNotFoundException e) {
				LOGGER.severe("Caught FileNotFoundException: Unable to find the specified input file");
				System.exit(STATUS_CODE_FAILURE);
			} catch (RuntimeException re) {
				LOGGER.severe(re.getMessage());
				System.exit(STATUS_CODE_FAILURE);
			} finally {
				INSTANCE.inputScanner.close();
			}
		}
		
	}
	
	private boolean validateInput(String[] args) throws FileNotFoundException {
		int argsCount = args.length;
		for (String arg : args) {
			if (arg.trim().equalsIgnoreCase(PRETTY_PRINT_ARG1) || arg.trim().equalsIgnoreCase
					(PRETTY_PRINT_ARG2)) {
				prettyPrint = true;
				argsCount--;
			} else if (new File(arg).exists()) {
				setInputScanner(new Scanner(new File(arg)));
				argsCount--;
			}
		}
		return argsCount == 0;
	}
	
	private static void printUsage() {
		System.out.println("Usage: SpreadsheetSolver [--prettyPrint, -p] [filepath]");
		System.out.println("SpreadsheetSolver can take 2 command line argument");
		System.out.println("[--prettyPrint, -p]: Print the output in a pretty format");
		System.out.println("[filepath]: path of file from where the input has to be read. If no filepath is provided" +
				" " +
				"SpreadsheetSolver expects to read inputs from stdin");
	}
	
	public void setInputScanner(Scanner inputScanner) {
		this.inputScanner = inputScanner;
	}
}
