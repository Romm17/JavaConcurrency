package ua.yandex.bank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Roman Usik
 */
public class Account {

    private static int ID = 0;

    private int id;
    private int amount;
    private Lock lock;

    public Account(int amount) {
        this.amount = amount;
        lock = new ReentrantLock();
        id = ID++;
    }

    public Lock getLock() {
        return lock;
    }

    public int getID() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public boolean withdraw(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            return true;
        }
        return false;
    }

    public void deposit(int amount) {
        this.amount += amount;
    }

}
