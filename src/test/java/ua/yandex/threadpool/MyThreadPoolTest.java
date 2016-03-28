package ua.yandex.threadpool;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Roman Usik
 */
public class MyThreadPoolTest {

    @Test
    public void testStop() {
        MyThreadPool myThreadPool = new MyThreadPool(10, 100);
        for (int i = 0; i < 100; i++) {
            myThreadPool.addTask(new Task(i));
        }
        myThreadPool.stop();
    }

    @Test
    public void testJoin() {
        MyThreadPool myThreadPool = new MyThreadPool(10, 100);
        for (int i = 0; i < 100; i++) {
            myThreadPool.addTask(new Task(i));
        }
        myThreadPool.join();
    }

}
