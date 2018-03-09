package com.guojun.dom.java;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class JavaType {

    private static final String JAVA_LANG = "java.lang"; //$NON-NLS-1$

    private String fullyQualifiedJavaType;

    private String simplifyJavaType;

    public JavaType(String javaType) {
        super();
        parse(javaType);
    }

    public String getFullyQualifiedJavaType() {
        return fullyQualifiedJavaType;
    }

    public String getSimplifyJavaType() {
        return simplifyJavaType;
    }

    public String getPackageName() {
        return JAVA_LANG;
    }

    private void parse(String javaType) {
        String spec = javaType.trim();
        if (spec.startsWith(JAVA_LANG)) {
            fullyQualifiedJavaType = javaType;
            simplifyJavaType = javaType.substring(JAVA_LANG.length() + 1);
        } else if (!spec.contains(".")) {
            simpleParse(spec);
        }
    }

    private void simpleParse(String javaType) {
        fullyQualifiedJavaType = JAVA_LANG + "." + javaType;
        simplifyJavaType = javaType;
    }

}
