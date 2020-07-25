package com.saa.pdfutil;

import java.util.List;

public class Table implements Comparable{
	private String tableName;
	private List<Row> rowList;
	public Table(String tableName, List<Row> rowList) {
		super();
		this.tableName = tableName;
		this.rowList = rowList;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Row> getRowList() {
		return rowList;
	}
	public void setRowList(List<Row> rowList) {
		this.rowList = rowList;
	}
	
	@Override
	public int compareTo(Object t) {
		// TODO Auto-generated method stub
		Table tb=(Table)t;
		return tableName.compareTo(tb.getTableName());
	}
	

}
