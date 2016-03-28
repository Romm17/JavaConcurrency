package ua.yandex.fj;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

/**
 * @author Roman Usik
 */
public class WordCounter {

    private ConcurrentHashMap<String, Integer> map;

    private static class Counter extends RecursiveTask<Void> {

        private static final int MIN_SIZE = 2;

        private String[] words;
        private ConcurrentHashMap<String, Integer> map;

        public Counter(String[] words, ConcurrentHashMap<String, Integer> map) {
            this.words = words;
            this.map = map;
        }

        private void count() {
            for (String word : words) {
                Integer was;
                boolean replaced = false;
                while (!replaced) {
                    was = map.get(word);
                    if (was == null) {
                        map.put(word, 1);
                        replaced = true;
                    }
                    else {
                        replaced = map.replace(word, was, was + 1);
                    }
                }
            }
        }

        @Override
        protected Void compute() {
            if (words.length <= MIN_SIZE) {
                count();
            }
            else {
                int newLength = words.length / 2;
                String[] leftArray = new String[newLength];
                int addition = words.length % 2 == 0 ? 0 : 1;
                String[] rightArray = new String[newLength + addition];
                System.arraycopy(words, 0, leftArray, 0, newLength);
                System.arraycopy(words, newLength,
                        rightArray, 0, newLength + addition);

                Counter left = new Counter(leftArray, map);
                Counter right = new Counter(rightArray, map);
                right.fork();
                left.compute();
                right.join();
            }
            return null;
        }

    }

    public static Map<String, Integer> count(String[] words) {
        ConcurrentHashMap<String, Integer> concurrentHashMap
                = new ConcurrentHashMap<>();
        new Counter(words, concurrentHashMap).compute();
        return concurrentHashMap;
    }

}
