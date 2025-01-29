package ru.otus.gc.bench;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GcDemoControl implements GcDemoControlMBean {
    private static final Logger logger = LoggerFactory.getLogger(GcDemoControl.class);
    private final GcDemo gcDemo;

    public GcDemoControl(GcDemo gcDemo) {
        this.gcDemo = gcDemo;
    }

    @Override
    public int getObjectArraySize() {
        int size = gcDemo.getObjectArraySize();
        logger.info("current size:{}", size);
        return size;
    }

    @Override
    public void setObjectArraySize(int size) {
        logger.info("setting size:{}", size);
        gcDemo.setObjectArraySize(size);
    }
}
