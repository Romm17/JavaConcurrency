package ua.yandex.sumofseries;

/**
 * @author Roman Usik
 */
public class SumOfSeries {

    private static final double step = 0.0001;

    public static double calculate(double start, double end) {

        double res = 0;
        for (double i = start; i < end; i += step) {
            res += Math.sin(i) * Math.cos(i);
        }
        return res;

    }

}
