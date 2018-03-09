package com.guojun.dom.java;

import java.util.List;

/**
 *
 * @author    GuoJun
 * @since     1.0
 */
public class Controller {

    private String fileName;

    private String className;

    private List<String> importList;

    private String service;

    private String model;

    private String idType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Override
    public String toString() {
        return "Controller{" +
                "fileName='" + fileName + '\'' +
                ", className='" + className + '\'' +
                ", importList=" + importList +
                ", service='" + service + '\'' +
                ", model='" + model + '\'' +
                ", idType='" + idType + '\'' +
                '}';
    }
}
