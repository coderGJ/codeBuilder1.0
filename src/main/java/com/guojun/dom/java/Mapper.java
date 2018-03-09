package com.guojun.dom.java;

import com.guojun.dom.Column;

import java.util.List;

/**
 * @author GuoJun
 * @since 1.0
 */
public class Mapper {

    private String fileName;

    private String namespace;

    private String tableName;

    private String className;

    private String type;

    //主键所对应的Column
    private Column primaryColumn;

    //其他不含主键的行集合
    private List<Column> columnList;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Mapper{" +
                "fileName='" + fileName + '\'' +
                ", namespace='" + namespace + '\'' +
                ", tableName='" + tableName + '\'' +
                ", className='" + className + '\'' +
                ", type='" + type + '\'' +
                ", primaryColumn=" + primaryColumn +
                ", columnList=" + columnList +
                '}';
    }
}
