package com.spreadsheet;

import java.util.ArrayList;
import java.util.List;

public class Workbook {
	private List<Spreadsheet> spreadsheets = new ArrayList<Spreadsheet>();

	public List<Spreadsheet> getSpreadsheets() {
		return spreadsheets;
	}

	public void setSpreadsheets(List<Spreadsheet> spreadsheets) {
		this.spreadsheets = spreadsheets;
	}
}
