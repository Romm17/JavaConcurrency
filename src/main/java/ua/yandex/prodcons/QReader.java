package ua.yandex.prodcons;

public class QReader implements Runnable {

    private IQueue q;

    public QReader(IQueue q) {
        this.q = q;
    }

    @Override
    public void run() {
        System.out.println("Get " + q.get());
    }
}
