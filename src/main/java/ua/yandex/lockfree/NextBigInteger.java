package ua.yandex.lockfree;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Roman Usik
 */
public class NextBigInteger {

    private static AtomicReference<BigInteger> number;

    static {
        number = new AtomicReference<> (new BigInteger("1"));
    }

    public static BigInteger next() {
        boolean end = false;
        BigInteger result = null;
        while (!end) {
            result = number.get();
            end = number.compareAndSet(result, result.multiply(new BigInteger("2")));
        }
        return result;
    }

}
