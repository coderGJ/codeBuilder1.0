package com.guojun.app;

import com.guojun.base.GeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      GuoJun
 * @since       1.0
 */
public class Bootstrap {
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        GeneratorManager gm = new GeneratorManager();
        gm.generatorFiles();
    }
}
