package ua.yandex.threadpool;

public class Task implements Runnable {

    private int i;

    public Task(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println("Task " + i);
    }
}
