package ua.yandex.bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Roman Usik
 */
public class BankTest {

    private int accountsNum;
    private Account[] accounts;

    private int totalAmount() {
        int res = 0;
        for (Account a : accounts) {
            res += a.getAmount();
        }
        return res;
    }

    private int randInt(int high) {
        return (int) (Math.random() * high);
    }

    @Before
    public void init() {
        accountsNum = 1000;
        accounts = new Account[accountsNum];
        for (int i = 0; i < accountsNum; i++) {
            accounts[i] = new Account(randInt(100));
        }
    }

    @Test
    public void testTransfer() {
        int expected = totalAmount();
        Thread[] threads = new Thread[accountsNum * accountsNum];
        for (int i = 0; i < accountsNum * accountsNum; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bank.transfer(accounts[randInt(accountsNum)],
                            accounts[randInt(accountsNum)],
                            randInt(10));
                }
            });
            threads[i].start();

        }
        for (int i = 0; i < accountsNum * accountsNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int actual = totalAmount();
        Assert.assertEquals(expected, actual);
    }
}
