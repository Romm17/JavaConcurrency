package ua.yandex.prodcons;

/**
 * @author Roman Usik
 */
public interface IQueue<T> {

    T get();

    void put(T value);

    boolean isEmpty();

    int size();

}

