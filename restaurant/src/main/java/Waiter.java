import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.interrupted;

public class Waiter implements Runnable {
    static private int N;                         // количество официантов в игровом мире

    private final Lunchroom company;             // организация где работает
    private final int id;                        // номер официанта в организации

    private Customer client;                     // обслуживаемый посетитель
    private volatile boolean isFree = true;      // флажок доступности официанта

    Lock serving = new ReentrantLock();
    Condition orderToServe = serving.newCondition();
    Condition dishReady = serving.newCondition();

    public Waiter(Lunchroom company) {
        this.company = company;
        id = ++N;
    }

    @Override
    public void run() {
        System.out.println(this + " вышел на смену");
        while (company.isOpen() && !interrupted()) {
            try {
                serving.lock();
                orderToServe.await();
                serveTheCustomer(client);
                dishReady.signal();
            }
            catch (InterruptedException e) { e.printStackTrace(); }
            finally { serving.unlock(); }
        }
    }

    public void serveTheCustomer(Customer client) throws InterruptedException {
        isFree = false;
        System.out.println(this + " принял заказ от " + client);
        try {
            company.cook.cooking.lock();
            company.cook.cookFor(client);
            System.out.println(this + " несёт заказ для " + client);
        } finally {
            company.cook.cooking.unlock();
            isFree = true;
        }
    }

    public boolean isFree() {
        return isFree;
    }

    @Override
    public String toString() {
        return "Официант " + id;
    }

    public void takeClient(Customer client) {
        this.client = client;
    }
}
