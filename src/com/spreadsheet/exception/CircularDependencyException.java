package com.spreadsheet.exception;

/**
 * Exception class for circular dependency
 *
 */
public class CircularDependencyException extends Exception {
	public CircularDependencyException() {
	}

	public CircularDependencyException(String message) {
		super(message);
	}
}
