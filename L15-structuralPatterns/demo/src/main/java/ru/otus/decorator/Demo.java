package ru.otus.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipInputStream;

@SuppressWarnings("java:S125")
public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) throws Exception {
        var ds = new DataSourceImpl();
        printer(ds);

        printer(new DataSourceDecoratorAdder(ds));
        printer(new DataSourceDecoratorMultiplicator(ds));
        printer(new DataSourceDecoratorMultiplicator(
                new DataSourceDecoratorAdder(new DataSourceDecoratorMultiplicator(ds))));

        // Пример из JDK - система ввода вывода
        // InputStream, FileInputStream, BufferedInputStream

//                FileInputStream fis = new FileInputStream("/objects.gz");
//                BufferedInputStream bis = new BufferedInputStream(fis);
//
//                ZipInputStream zis = new ZipInputStream(fis);
//
//                GzipInputStream gis = new GzipInputStream(bis);
//
//                ObjectInputStream ois = new ObjectInputStream(gis);
//                SomeObject someObject = (SomeObject) ois.readObject();
    }

    private static void printer(DataSource ds) {
        logger.info("{}:", ds.getInteger());
    }
}
