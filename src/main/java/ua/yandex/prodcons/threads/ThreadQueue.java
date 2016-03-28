package ua.yandex.prodcons.threads;

import ua.yandex.prodcons.IQueue;
import ua.yandex.prodcons.QReader;
import ua.yandex.prodcons.QWriter;

/**
 * @author Roman Usik
 */
public class ThreadQueue<T> implements IQueue<T> {

    private int size;
    private Object[] buffer;
    private int getPos;
    private int putPos;

    private final Object notFull = new Object();
    private final Object notEmpty = new Object();

    public ThreadQueue () {
        size = 10;
        buffer = new Object[size];
        getPos = putPos = 0;
    }

    public ThreadQueue (int size) {
        this.size = size;
        buffer = new Object[size];
        getPos = putPos = 0;
    }

    public void put (T value) {
        synchronized (notFull) {
            while ((putPos + 1) % size == getPos) {
                try {
                    notFull.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            buffer[putPos] = value;
            putPos = (putPos + 1) % size;
            notFull.notify();
        }
        synchronized (notEmpty) {
            notEmpty.notify();
        }
    }

    @Override
    public synchronized boolean isEmpty() {
        return getPos == putPos;
    }

    @Override
    public synchronized int size() {
        return (putPos - getPos + size) % size;
    }

    public T get () {
        T toReturn;
        synchronized (notEmpty) {
            for (int i = 0; getPos == putPos; i++) {
                try {
                    notEmpty.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i > 2)
                    return null;
            }
            toReturn = (T) buffer[getPos];
            getPos = (getPos + 1) % size;
            notEmpty.notify();
        }
        synchronized (notFull) {
            notFull.notify();
        }
        return toReturn;
    }

    public static void main(String[] args) {
        ThreadQueue<Integer> q = new ThreadQueue<>();

        for (int i = 0; i < 100; i++) {
            if(Math.random() > 0.5)
                new Thread(new QReader(q)).start();
            else
                new Thread(new QWriter(q)).start();
        }

    }

}
