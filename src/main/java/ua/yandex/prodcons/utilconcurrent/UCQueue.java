package ua.yandex.prodcons.utilconcurrent;

import ua.yandex.prodcons.IQueue;
import ua.yandex.prodcons.QReader;
import ua.yandex.prodcons.QWriter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Roman Usik
 */
public class UCQueue<T> implements IQueue<T>{

    private ReentrantLock reentrantLock;

    private final Condition notEmpty;
    private final Condition notFull;

    private int size;
    private Object[] buffer;
    private int getPos;
    private int putPos;

    public UCQueue () {
        size = 10;
        buffer = new Object[size];
        getPos = putPos = 0;

        reentrantLock = new ReentrantLock();

        notEmpty = reentrantLock.newCondition();
        notFull = reentrantLock.newCondition();
    }

    public UCQueue (int size) {
        this.size = size;
        buffer = new Object[size];
        getPos = putPos = 0;

        reentrantLock = new ReentrantLock();

        notEmpty = reentrantLock.newCondition();
        notFull = reentrantLock.newCondition();
    }

    @Override
    public void put (T value) {
        reentrantLock.lock();
        try {

            while ((putPos + 1) % size == getPos) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            buffer[putPos] = value;
            putPos = (putPos + 1) % size;
            notEmpty.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return getPos == putPos;
    }

    @Override
    public int size() {
        return (putPos - getPos + size) % size;
    }

    @Override
    public T get () {
        reentrantLock.lock();

        try {
            while (getPos == putPos) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T toReturn = (T) buffer[getPos];
            getPos = (getPos + 1) % size;
            notFull.signal();
            return toReturn;
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        UCQueue<Integer> q = new UCQueue<>();

        for (int i = 0; i < 1000; i++) {
            new Thread(new QReader(q)).start();
            new Thread(new QWriter(q)).start();
        }
    }

}
