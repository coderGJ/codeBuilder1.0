package com.guojun.app;

import org.junit.Test;

import com.guojun.base.AppProperties;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class AppPropertiesTest {

    @Test
    public void testGet() {
        System.out.println(AppProperties.getProperty("jdbc.driverClassName"));
    }
}
