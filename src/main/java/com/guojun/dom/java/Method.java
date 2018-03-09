package com.guojun.dom.java;

/**
 *
 * @author GuoJun
 * @since 1.0
 * 
 */
public class Method {

    private String name;

    private String returnType;

    private String parameter;

    private String methodBody;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(String methodBody) {
        this.methodBody = methodBody;
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + name + '\'' +
                ", returnType='" + returnType + '\'' +
                ", parameter='" + parameter + '\'' +
                ", methodBody='" + methodBody + '\'' +
                '}';
    }
}
