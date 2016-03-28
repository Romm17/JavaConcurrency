package ua.yandex.mergesort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Roman Usik
 */
public class MultithreadedMergeSortTest {

    private int n = 10000;

    @Test
    public void testSort() {
        int[] expected = new int[n];
        for (int i = 0; i < n; i++) {
            expected[i] = (int)(Math.random() * n);
        }
        int[] actual = new int[n];
        System.arraycopy(expected, 0, actual, 0, n);
        MultithreadedMergeSort.sort(actual);
        Arrays.sort(expected);
        for (int i = 0; i < n; i++) {
            System.out.println(expected[i] + " " + actual[i]);
        }
        Assert.assertArrayEquals(expected, actual);
    }

}
