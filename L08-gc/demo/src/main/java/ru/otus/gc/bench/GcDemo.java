package ru.otus.gc.bench;

import com.sun.management.GarbageCollectionNotificationInfo;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import javax.management.*;
import javax.management.openmbean.CompositeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
О формате логов
http://openjdk.java.net/jeps/158

Анализ лога GC
https://gceasy.io

VM options:
-Xms1024m
-Xmx1024m
-verbose:gc
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
-XX:MaxGCPauseMillis=10
*/
// -XX:+UseZGC
// -XX:+ZGenerational

/*
Основная настройка:
    -XX:MaxGCPauseMillis=100000
    -XX:MaxGCPauseMillis=10
*/

public class GcDemo {
    private static final Logger logger = LoggerFactory.getLogger(GcDemo.class);
    private volatile int objectArraySize = 5 * 1000 * 1000;

    public static void main(String... args) throws Exception {
        logger.info("Starting pid: {}", ManagementFactory.getRuntimeMXBean().getName());

        GcDemo gcDemo = new GcDemo();

        switchOnMonitoring();
        switchOnMxBean(gcDemo);

        gcDemo.run();
    }

    public int getObjectArraySize() {
        return objectArraySize;
    }

    public void setObjectArraySize(int objectArraySize) {
        this.objectArraySize = objectArraySize;
    }

    private void run() throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        createObject();
        logger.info("time:{}", (System.currentTimeMillis() - beginTime) / 1000);
    }

    private void createObject() throws InterruptedException {
        int loopCounter = 100_000;
        for (int loopIdx = 0; loopIdx < loopCounter; loopIdx++) {
            int local = objectArraySize;
            Integer[] array = new Integer[local];
            Arrays.fill(array, Integer.MAX_VALUE);
            Thread.sleep(100); // Label_1
            long sum = 0;
            for (var elem : array) {
                sum += elem;
            }
            logger.trace("array sum:{}", sum);
        }
    }

    private static void switchOnMxBean(GcDemo gcDemo)
            throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException,
                    MBeanRegistrationException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=gcDemo");

        GcDemoControlMBean mbean = new GcDemoControl(gcDemo);
        mbs.registerMBean(mbean, name);
    }

    private static void switchOnMonitoring() {
        var gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (var gcbean : gcbeans) {
            logger.info("GC name:{}", gcbean.getName());
            var emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                var info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                var gcName = info.getGcName();
                var gcAction = info.getGcAction();
                var gcCause = info.getGcCause();

                var startTime = info.getGcInfo().getStartTime();
                var duration = info.getGcInfo().getDuration();

                logger.info(
                        "start:{}, Name:{}, action:{}, gcCause:{}  {}(ms)",
                        startTime,
                        gcName,
                        gcAction,
                        gcCause,
                        duration);
            };
            emitter.addNotificationListener(
                    listener,
                    notification -> notification
                            .getType()
                            .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION),
                    null);
        }
    }
}
