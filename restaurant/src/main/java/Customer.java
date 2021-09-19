public class Customer implements Runnable {
    private static final int CALLING_TIME = 1000;
    private static final int MEAN_CONSUMING_TIME = 7000;
    private static final int MEAN_CHOOSING_TIME = 1500;

    static private int N;                   // количество посетителей в игровом мире

    private final Lunchroom lunchroom;      // столовая, в которой ест
    private final int id;                   // номер посетителя в мире столовой

    public Customer(Lunchroom lunchroom) {
        this.lunchroom = lunchroom;
        id = ++N;
    }

    @Override
    public void run() {
        if (!lunchroom.isOpenForEntrance()) return;
        lunchroom.haveCustomerCome(this);
        makeAnOrder(lunchroom);
        consume();
        lunchroom.haveCustomerServed(this);
    }

    private void makeAnOrder(Lunchroom lunchroom) {
        Main.timePass(MEAN_CHOOSING_TIME);
        Waiter servant;
        calling:
        while (true) {
            System.out.println(this + " зовёт официанта");
            for (Waiter w : lunchroom.waiters)
                if (w.serving.tryLock()) {
                    servant = w;
                    break calling;
                }
            Main.timePass(CALLING_TIME);
        }
        try {
            servant.takeClient(this);
            servant.orderToServe.signal();
            servant.dishReady.await();
        }
        catch (InterruptedException e) { e.printStackTrace(); }
        finally { servant.serving.unlock(); }
    }

    private void consume() {
        System.out.println(this + " приступил к еде");
        Main.timePass(MEAN_CONSUMING_TIME);
        System.out.println(this + " закончил есть");
    }

    @Override
    public String toString() {
        return "Посетитель " + id;
    }
}
