package ua.yandex.bank;

import java.util.concurrent.locks.Lock;

/**
 * @author Roman Usik
 */
public class Bank{

    public static void transfer(Account from, Account to, int amount){

        Lock lockFrom = from.getLock();
        Lock lockTo = to.getLock();

        if (from.getID() < to.getID()) {
            lockFrom.lock();
            lockTo.lock();
            if(from.withdraw(amount)) {
                to.deposit(amount);
            }
            lockTo.unlock();
            lockFrom.unlock();
        }
        else {
            lockTo.lock();
            lockFrom.lock();
            if(from.withdraw(amount)) {
                to.deposit(amount);
            }
            lockFrom.unlock();
            lockTo.unlock();
        }

    }

}