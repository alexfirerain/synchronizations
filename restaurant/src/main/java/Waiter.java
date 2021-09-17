import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.interrupted;

public class Waiter implements Runnable {
    static private int N;               // количество официантов в игровом мире

    private final Lunchroom company;    // организация где работает
    private final int id;               // номер официанта в организации
//    private Customer client;            // обслуживаемый посетитель
    private boolean isFree = true;      // флажок доступности официанта

    Lock serving = new ReentrantLock();
    Condition orderToServe = serving.newCondition();

    public Waiter(Lunchroom company) {
        this.company = company;
        id = ++N;
    }

    @Override
    public String toString() {
        return "Официант " + id;
    }
    @Override
    public void run() {
        System.out.println(this + " вышел на смену");
        serving.lock();
        try {
            while (company.isOpen() && !interrupted()) {
                // блокироваться и ожидать заказа посетителя
                orderToServe.await();
                // здесь

                // возврат к началу;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            serving.unlock();
        }
    }

    public void serveTheCustomer(Customer client) throws InterruptedException {
        System.out.println(this + " принял заказ от " + client);
        isFree = false;
        try {
            company.cook.cooking.lock();
            // передать заказ повару (он ставит его в очередь ?) // нет, для упрощения просто ждёт, пока тот освободится
            company.cook.orderToCook.signal();
            // ждать, пока повар сготовит блюдо // по-хорошему, нужно было бы обслуживать новых посетителей
            company.cook.cookFor(client);
            company.cook.dish.await();
            // отнести блюдо посетителю
             System.out.println(this + " отнёс заказ для " + client);
             client.dishReady.signal();
        } finally {
            company.cook.cooking.unlock();
        }
        isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }

}
