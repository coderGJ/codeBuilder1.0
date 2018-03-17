package com.guojun.dom;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class Column {

    //COLUMN_NAME
    private String columnName;

    //camelCaseName
    private String camelCaseName;

    //TYPE_NAME
    private String columnType;

    //JDBC_TYPE
    private String jdbcType;

    //COLUMN_SIZE
    private int columnSize;

    //DECIMAL_DIGITS
    private int digits;

    //NULLABLE
    //true: ALLOW; false: DISALLOW
    private boolean nullable;

    //REMARKS
    private String remarks;

    //primaryKey
    private boolean primaryKey;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCamelCaseName() {
        return camelCaseName;
    }

    public void setCamelCaseName(String camelCaseName) {
        this.camelCaseName = camelCaseName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "Column [columnName=" + columnName + ", columnType=" + columnType
                + ", columnSize=" + columnSize + ", digits=" + digits + ", nullable=" + nullable + ", remarks="
                + remarks + "]";
    }

}
