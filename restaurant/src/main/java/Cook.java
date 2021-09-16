import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.interrupted;

public class Cook implements Runnable {
    static final int MEAN_COOKING_TIME = 5000;

    final Lunchroom company;
    Lock cooking = new ReentrantLock();
    final Condition orderToCook = cooking.newCondition();
    final Condition dish = cooking.newCondition();

    public Cook(Lunchroom company) {
        this.company = company;
    }

    @Override
    public void run() {
        System.out.println("Повар вышел на смену");
        try {
            cooking.lock();
            while (company.isOpen() && !interrupted()) {
                // взять заказ; если его нет, то блокироваться и ожидать
                // получить заказ
                orderToCook.await();

                // готовить некоторое время
                Main.timePass(MEAN_COOKING_TIME);

                // уведомить о готовности заказа
                dish.signal();

                // возврат к началу; если план выполнен, закончить
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cooking.unlock();
        }

    }
}
