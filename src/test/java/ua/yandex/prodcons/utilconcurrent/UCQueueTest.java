package ua.yandex.prodcons.utilconcurrent;

import org.junit.Test;
import ua.yandex.prodcons.*;

/**
 * @author Roman Usik
 */
public class UCQueueTest {

    @Test
    public void testUCQueue () {

        UCQueue<Integer> q = new UCQueue<>();

        for (int i = 0; i < 1000; i++) {
            new Thread(new QReader(q)).start();
            new Thread(new QWriter(q)).start();
        }

    }
}
