package ua.yandex.prodcons.threads;

import org.junit.Test;
import ua.yandex.prodcons.QReader;
import ua.yandex.prodcons.QWriter;

/**
 * @author Roman Usik
 */
public class ThreadQueueTest {

    @Test
    public void testUCQueue () {

        ThreadQueue<Integer> q = new ThreadQueue<>();

        for (int i = 0; i < 1000; i++) {
            if(Math.random() > 0.5)
                new Thread(new QReader(q)).start();
            else
                new Thread(new QWriter(q)).start();
        }

    }
}
