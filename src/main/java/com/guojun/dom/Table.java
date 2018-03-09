package com.guojun.dom;

import java.util.List;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class Table {

    //TABLE_NAME
    private String tableName;

    //TABLE_TYPE
    private String tableType;

    //REMARKS
    private String remarks;

    //主键所对应的Column
    private Column primaryColumn;

    //其他不含主键的行集合
    private List<Column> columnList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Column getPrimaryColumn() {
        return primaryColumn;
    }

    public void setPrimaryColumn(Column primaryColumn) {
        this.primaryColumn = primaryColumn;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", tableType='" + tableType + '\'' +
                ", remarks='" + remarks + '\'' +
                ", primaryColumn=" + primaryColumn +
                ", columnList=" + columnList +
                '}';
    }
}
