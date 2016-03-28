package ua.yandex.mergesort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Roman Usik
 */
public class MultithreadedMergeSort implements Runnable {

    private int[] array;
    private int l;
    private int r;
    private int m;

    private MultithreadedMergeSort(int[] array, int l, int r) {
        this.array = array;
        this.l = l;
        this.r = r;
    }

    private void merge() {
        int i = l, j = m, k = 0;
        int[] res = new int[r - l];
        while (i < m && j < r) {
            if (array[i] < array[j]) {
                res[k++] = array[i++];
            }
            else {
                res[k++] = array[j++];
            }
        }
        while (i < m) {
            res[k++] = array[i++];
        }
        while (j < r) {
            res[k++] = array[j++];
        }
        System.arraycopy(res, 0, array, l, r - l);
    }

    @Override
    public void run() {
        if (r - l < 2) {
            return;
        }
        else if (r - l == 2) {
            if (array[l] > array[l + 1]) {
                array[l] = array[l] ^ array[l + 1];
                array[l + 1] = array[l] ^ array[l + 1];
                array[l] = array[l] ^ array[l + 1];
            }
            return;
        }
        m = (l + r) / 2;
        Thread left = new Thread(new MultithreadedMergeSort(array, l, m));
        MultithreadedMergeSort right = new MultithreadedMergeSort(array, m, r);
        left.start();
        right.run();
        try {
            left.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        merge();
    }

    public static void sort(int[] array) {

        new MultithreadedMergeSort(array, 0, array.length).run();

    }

}
