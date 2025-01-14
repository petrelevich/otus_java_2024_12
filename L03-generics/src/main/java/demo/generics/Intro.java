package demo.generics;

import java.time.LocalTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"rawtypes", "unchecked", "java:S125", "java:S1854", "java:S1481", "java:S2293"})
public class Intro {
    private static final Logger logger = LoggerFactory.getLogger(Intro.class);

    public static void main(String[] args) {
        new Intro().beforeGenerics();
        logger.info("-------");
        new Intro().generics();
    }

    // До Generics
    private void beforeGenerics() {
        List list = new ArrayList();
        list.add(4.0);
        list.add(4L);
        list.add("Hello");
        list.add(LocalTime.now());

        for (Object item : list) { // Object !!!
            logger.info("{}", item);
        }

        double sum = sum(list);
        logger.info("sum = {}", sum);
    }

    double sum(List list) {
        var result = 0.0;
        for (Object item : list) {
            // Не компилируется:
            //      result += item;
            // Ошибка в runtime (ClassCastException):
            //      result += (double) item;
            // Надо делать проверки и преобразование типов:
            //      if (item != null && item instanceof Number) {
            //          result += Double.parseDouble(item.toString());
            //      } else {
            //          // Бросаем исключение или игнорируем
            //      }
        }
        return result;
    }

    // Эра Generics
    private void generics() {
        List<Integer> list = new ArrayList<Integer>(); // полный синтаксис
        List<Integer> list2 = new ArrayList<>(); // с Java 1.7, diamond operator
        var list3 = new ArrayList<Integer>(); // с Java 10, var
        list.add(4);
        //        list.add(4.0); //ошибка компиляции
        //        list.add(4L);    //ошибка компиляции
        //        list.add("Hello"); //ошибка компиляции
        //        list.add(LocalTime.now()); //ошибка компиляции

        var sum = 0.0;
        for (int item : list) {
            sum += item;
            logger.info("{}", item);
        }
        logger.info("sum with generics = {}", sum);
    }
}
