package ua.yandex.sumofseries.threads;

import ua.yandex.sumofseries.SumOfSeries;

/**
 * @author Roman Usik
 */
public class ThreadCounter implements Runnable {

    private double result;
    private double start;
    private double end;

    public ThreadCounter(double start, double end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        result = SumOfSeries.calculate(start, end);
    }

    public double getResult() {
        return result;
    }

    public static double execute(double start, double end, int threads) {

        ThreadCounter[] counters = new ThreadCounter[threads];
        Thread[] threadPool = new Thread[threads];

        double step = (end - start) / threads;
        for (int i = 0; i < threads; i++) {
            counters[i] = new ThreadCounter(start + i * step,
                    start + (i + 1) * step);
            threadPool[i] = new Thread(counters[i]);
            threadPool[i].start();
        }

        double answer = 0;

        for (int i = 0; i < threads; i++) {
            try {
                threadPool[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            answer += counters[i].getResult();
        }

        return answer;

    }

}
