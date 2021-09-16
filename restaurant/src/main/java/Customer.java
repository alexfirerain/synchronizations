import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer implements Runnable {
    private static final int CALLING_TIME = 500;
    private static final int MEAN_CONSUMING_TIME = 7000;
    private static final int MEAN_CHOOSING_TIME = 1500;

    static private int N;                   // количество посетителей в игровом мире

    private final Lunchroom lunchroom;      // организация, в которой ест
    private final int id;                   // номер посетителя

    Lock waiting = new ReentrantLock();
    Condition dishReady = waiting.newCondition();

    public Customer(Lunchroom lunchroom) {
        this.lunchroom = lunchroom;
        id = ++N;
    }
    @Override
    public String toString() {
        return "Посетитель " + id;
    }
    @Override
    public void run() {
        lunchroom.haveCustomerCome(this);
        // некоторое время изучать меню
        Main.timePass(MEAN_CHOOSING_TIME);

        try {
            waiting.lock();
            // сделать заказ в заведении
            // блокироваться и ждать получения блюда
            makeAnOrder(lunchroom);
            dishReady.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            waiting.unlock();
        }
        // получить блюдо и некоторое время кушать
        consume();
        // закончив, попрощаться и уйти
        lunchroom.haveCustomerServed(this);
    }

    private void consume() {
        System.out.println(this + " приступил к еде");
        Main.timePass(MEAN_CONSUMING_TIME);
        System.out.println(this + " закончил есть");
    }

    private void makeAnOrder(Lunchroom lunchroom) {
        // позовём официанта
        boolean calling = true;
        while(calling) {
            // периодически зовём официанта
            Main.timePass(CALLING_TIME);
            for (Waiter w : lunchroom.waiters) {
                // если официант отозвался, делаем заказ
                if (w.isFree()) {
                    try {
                        w.orderToServe.signal();
                        w.serveTheCustomer(this);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // и больше звать официанта не надо
                    calling = false;
                }
            }
        }
    }
}
