package com.guojun.dom.java;

import java.util.List;
import java.util.Set;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class JavaBean {

    private String fileName;

    private Set<String> importSet;

    private String className;

    private String tableName;

    private List<Field> fieldList;
    
    private List<Method> methodList;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<String> getImportSet() {
        return importSet;
    }

    public void setImportSet(Set<String> importSet) {
        this.importSet = importSet;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Method> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<Method> methodList) {
        this.methodList = methodList;
    }

    @Override
    public String toString() {
        return "JavaBean{" +
                "fileName='" + fileName + '\'' +
                ", importSet=" + importSet +
                ", className='" + className + '\'' +
                ", tableName='" + tableName + '\'' +
                ", fieldList=" + fieldList +
                ", methodList=" + methodList +
                '}';
    }
}
