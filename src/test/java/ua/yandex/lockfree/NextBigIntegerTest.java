package ua.yandex.lockfree;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.*;

/**
 * @author Roman Usik
 */
public class NextBigIntegerTest {

    private static class Getter implements Callable<BigInteger > {

        @Override
        public BigInteger call() throws Exception {
            return NextBigInteger.next();
        }
    }

    @Test
    public void testNext() {

        int threadNum = 2000;

        ExecutorService executorService
                = Executors.newFixedThreadPool(threadNum);

        ArrayList<Future<BigInteger>> results = new ArrayList<>(threadNum);

        for (int i = 0; i < threadNum; i++) {
            results.add(executorService.submit(new Getter()));
        }

        HashSet<BigInteger> set = new HashSet<>(threadNum);

        for (int i = 0; i < threadNum; i++) {
            try {
                set.add(results.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        Assert.assertEquals(set.size(), threadNum);

    }
}
