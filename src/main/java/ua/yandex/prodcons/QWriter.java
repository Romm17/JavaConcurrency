package ua.yandex.prodcons;

public class QWriter implements Runnable {

    private IQueue q;

    public QWriter(IQueue q) {
        this.q = q;
    }

    @Override
    public void run() {
        int toPut = (int)(Math.random() * 10);
        System.out.println("Put " + toPut);
        q.put(toPut);
    }
}
