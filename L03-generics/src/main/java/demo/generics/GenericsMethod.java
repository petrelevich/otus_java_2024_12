package demo.generics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S125")
public class GenericsMethod {
    private static final Logger logger = LoggerFactory.getLogger(GenericsMethod.class);

    public static void main(String[] args) {
        GenericsMethod genericsMethod = new GenericsMethod();

        // Полный синтаксис:
        genericsMethod.<Integer, String>print(1, "value");

        // Вывод типов K и V
        genericsMethod.print(2, "value2");

        // Будет ли здесь ошибка?
        // genericsMethod.print("3", "value3");

        // А здесь?
        // genericsMethod.<Integer, String>print("4", "value4");
    }

    private <K, V> void print(K key, V val) {
        logger.info("key:{}, val:{}", key, val);
    }
}
