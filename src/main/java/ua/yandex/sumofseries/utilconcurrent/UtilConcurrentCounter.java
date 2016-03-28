package ua.yandex.sumofseries.utilconcurrent;

import ua.yandex.sumofseries.SumOfSeries;

import javax.sql.rowset.CachedRowSet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author Roman Usik
 */
public class UtilConcurrentCounter implements Callable<Double>{

    private double start;
    private double end;

    public UtilConcurrentCounter(double start, double end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Double call() throws Exception {
        return SumOfSeries.calculate(start, end);
    }

    public static double execute(double start, double end, int threads) {

        UtilConcurrentCounter[] ucc = new UtilConcurrentCounter[threads];
        ExecutorService es = Executors.newFixedThreadPool(threads);

        ArrayList<Future<Double>> results = new ArrayList<>(threads);

        double step = (end - start) / threads;
        for (int i = 0; i < threads; i++) {
            ucc[i] = new UtilConcurrentCounter(start + i * step,
                    start + (i + 1) * step);
            results.add(es.submit(ucc[i]));
        }

        double answer = 0;

        for (int i = 0; i < threads; i++) {
            try {
                answer += results.get(i).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return answer;

    }
}
