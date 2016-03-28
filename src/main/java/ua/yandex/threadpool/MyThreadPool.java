package ua.yandex.threadpool;

import ua.yandex.prodcons.threads.ThreadQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Roman Usik
 */
public class MyThreadPool {

    private int poolSize;
    private int tasksSize;

    private ThreadQueue<Runnable> tasks;
    private Thread[] threads;
    private MyRunnable[] myRunnables;

    private volatile boolean stopping = false;

    private class MyRunnable implements Runnable {

        private boolean stop = false;
        private int i;

        public MyRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            while(!stop) {
                Runnable myTask = tasks.get();
                if (myTask != null)
                    myTask.run();
            }
            System.out.println("finish " + i);
        }

        public void finish() {
            stop = true;
        }
    }

    public MyThreadPool (int poolSize, int tasksSize) {
        this.poolSize = poolSize;
        this.tasksSize = tasksSize;
        tasks = new ThreadQueue<>(tasksSize);
        this.threads = new Thread[poolSize];
        myRunnables = new MyRunnable[poolSize];
        for (int i = 0; i < this.poolSize; i++) {
            myRunnables[i] = new MyRunnable(i);
            this.threads[i] = new Thread(myRunnables[i]);
            this.threads[i].start();
        }
    }

    public synchronized boolean addTask (Runnable task) {

        if (!stopping && tasks.size() == tasksSize) {
            return false;
        }
        tasks.put(task);
        return true;

    }

    public boolean hasTasks() {
        return !tasks.isEmpty();
    }

    /**
     * Stops all threads after finishing all tasks in queue
     * Makes impossible to add new task
     */
    public synchronized void join() {
        stopping = true;
        while (!tasks.isEmpty());
        stop();
    }

    /**
     * Stops all threads in pool immediately after finishing all runnable tasks
     */
    public synchronized void stop() {
        for (int i = 0; i < poolSize; i++) {
            myRunnables[i].finish();
        }
        for (int i = 0; i < poolSize; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

