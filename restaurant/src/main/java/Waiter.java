import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.interrupted;

public class Waiter implements Runnable {
    static private int N;                         // количество официантов в игровом мире

    private final Lunchroom company;             // организация где работает
    private final int id;                        // номер официанта в организации

    private Customer client;                     // обслуживаемый посетитель
    private int served;

    Lock serving = new ReentrantLock();
    Condition orderToServe = serving.newCondition();
    Condition dishReady = serving.newCondition();

    @Override
    public void run() {
        System.out.println(this + " вышел на смену");
        while (company.isOpen() && !interrupted()) {
            try {
                serving.lock();
                orderToServe.await();
                serveTheCustomer(client);
                dishReady.signal();
                served++;
            }
            catch (InterruptedException ignored) {}
            finally { serving.unlock(); }
        }
        System.out.println(this + " обслужил сегодня " + served + " посетителей и идёт домой");
    }

    public void serveTheCustomer(Customer client) throws InterruptedException {
        System.out.println(this + " принял заказ от " + client);
        try {
            company.cook.cooking.lock();
            company.cook.cookFor(client);
            System.out.println(this + " несёт заказ для " + client);
        } finally {
            company.cook.cooking.unlock();
        }
    }

    public void takeClient(Customer client) {
        this.client = client;
    }

    public Waiter(Lunchroom company) {
        this.company = company;
        id = ++N;
    }

    @Override
    public String toString() {
        return "Официант " + id;
    }
}
