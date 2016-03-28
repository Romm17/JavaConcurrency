package ua.yandex.sumofseries.utilconcurrent;

import org.junit.Test;
/**
 * @author Roman Usik
 */
public class UtilConcurrentTest {
    
    @Test
    public void testExecute() {

        double N = 1000;

        long minTime = Long.MAX_VALUE;
        int fastestThreads = 0;

        for (int i = 1; i < 20; i++) {
            long time = System.currentTimeMillis();
            UtilConcurrentCounter.execute(-N, N, i);
            long time2 = System.currentTimeMillis() - time;
            System.out.println("" + i + " : " + time2);
            if(time2 < minTime) {
                minTime = time2;
                fastestThreads = i;
            }
        }

        System.out.println("Optimal number of threads is " + fastestThreads
                + " (" + (minTime / 1000.) + " s)");

    }
    
}
