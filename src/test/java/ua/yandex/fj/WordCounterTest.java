package ua.yandex.fj;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Roman Usik
 */
public class WordCounterTest {

    String[] words;

    @Before
    public void init() {
        words = new String[] {
                "Vova",
                "Roma",
                "Roma",
                "Vova",
                "Roma",
                "Vova",
                "Vova",
                "Roma",
                "Roma",
                "Vova",
                "Roma",
                "Vova",
                "Vova",
                "Vova",
                "Vova",
                "Petya"
        };
    }

    @Test
    public void testCount() {
        Map<String, Integer> map = WordCounter.count(words);
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
    }

}
