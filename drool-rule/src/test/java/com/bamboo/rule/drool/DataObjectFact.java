package com.bamboo.rule.drool;

public class DataObjectFact {

  private String dbName;
  private String tableName;
  private String columnName;
  private boolean run;

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public boolean isRun() {
    return run;
  }

  public void setRun(boolean run) {
    this.run = run;
  }

  @Override
  public String toString() {
    return "DataObjectFact{" +
        "dbName='" + dbName + '\'' +
        ", tableName='" + tableName + '\'' +
        ", columnName='" + columnName + '\'' +
        ", run=" + run +
        '}';
  }
}
