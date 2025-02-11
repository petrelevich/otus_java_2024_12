package lambda;

import static java.lang.Thread.sleep;

import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyCalc {
    private static final Logger logger = LoggerFactory.getLogger(LazyCalc.class);

    public static void main(String[] args) {
        var calc = new LazyCalc();

        var startTime1 = System.currentTimeMillis();
        var eagerResult1 = calc.calculation(calc.veryHeavyFunc(61));
        logger.info("== eager 1, time : {}", (System.currentTimeMillis() - startTime1));
        logger.info("eagerResult1:{}", eagerResult1);

        var startTime2 = System.currentTimeMillis();
        var eagerResult2 = calc.calculation(calc.veryHeavyFunc(62));
        logger.info("== eager 2, time : {}", (System.currentTimeMillis() - startTime2));
        logger.info("eagerResult2:{}", eagerResult2);

        var startTime3 = System.currentTimeMillis();
        var lazyResult1 = calc.calculationLazy(63, calc::veryHeavyFunc);
        logger.info("== lazy 1, time : {}", (System.currentTimeMillis() - startTime3));
        logger.info("lazyResult1:{}", lazyResult1.getAsInt());

        var startTime4 = System.currentTimeMillis();
        var lazyResult2 = calc.calculationLazy(64, calc::veryHeavyFunc);
        logger.info("== lazy 2, true time : {}", (System.currentTimeMillis() - startTime4));
        logger.info("lazyResult2:{}", lazyResult2.getAsInt());
    }

    private int calculation(int veryHeavyFuncResult) {
        return veryHeavyFuncResult;
    }

    private IntSupplier calculationLazy(int value, IntUnaryOperator veryHeavyFunc) {
        return () -> veryHeavyFunc.applyAsInt(value);
    }

    private int veryHeavyFunc(int input) {
        try {
            sleep(1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return input * 2;
    }
}
