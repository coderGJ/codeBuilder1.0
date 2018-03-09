package com.guojun.config;

import java.io.Serializable;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class TableConfiguration implements Serializable {

    /**
     * long serialVersionUID
     */
    private static final long serialVersionUID = -402413585794239193L;

    //包含的表
    private String include;
    
    //排除的表
    private String exclude;

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

}
