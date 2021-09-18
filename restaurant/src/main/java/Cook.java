import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cook implements Runnable {
    static final int MEAN_COOKING_TIME = 5000;

    final Lock cooking = new ReentrantLock(true);
//    final Condition orderToCook = cooking.newCondition();
    final Condition dishCooked = cooking.newCondition();
    public Cook(Lunchroom company) {
        this.company = company;
    }

    final Lunchroom company;

    @Override
    public void run() {
        System.out.println("Повар вышел на смену");
//        try {
//            cooking.lock();
//            while (company.isOpen() && !interrupted()) {
//                // взять заказ; если его нет, то блокироваться и ожидать
//                // получить заказ
//                orderToCook.await();
//
//                // готовить некоторое время
//                System.out.println( "Повар начал готовить блюдо" );
//                Main.timePass(MEAN_COOKING_TIME);
//
//                // уведомить о готовности заказа
//                System.out.println( "Повар закончил готовить блюдо" );
//                dish.signal();
//
//                // возврат к началу; если план выполнен, закончить
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            cooking.unlock();
//        }
    }

    public void cookFor(Customer client) {
        try {
            cooking.lock();
            // готовить некоторое время
            System.out.println("Повар начал готовить блюдо для " + client);
            Main.timePass(MEAN_COOKING_TIME);

            // уведомить о готовности заказа
            System.out.println("Повар закончил готовить блюдо для " + client);
//            dishCooked.signal();
        } finally {
            cooking.unlock();
        }
    }
}
