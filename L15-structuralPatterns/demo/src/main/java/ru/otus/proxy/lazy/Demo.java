package ru.otus.proxy.lazy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        lazy();
    }

    private static void lazy() {
        HeavyObject heavyObjectProxy = new LazyProxy();
        heavyObjectProxy.init("xxx");

        logger.info("{}", heavyObjectProxy.getValue());
        logger.info("{}", heavyObjectProxy.getValue());
    }
}
