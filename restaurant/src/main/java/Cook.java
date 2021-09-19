import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cook {
    static final int MEAN_COOKING_TIME = 5000;

    final Lock cooking = new ReentrantLock(true);
    final Lunchroom company;

    public Cook(Lunchroom company) {
        this.company = company;
        System.out.println("Повар вышел на смену");
    }

    public void cookFor(Customer client) {
        try {
            cooking.lock();
            System.out.println("Повар начал готовить блюдо для " + client);
            Main.timePass(MEAN_COOKING_TIME);
            System.out.println("Повар закончил готовить блюдо для " + client);
        } finally {
            cooking.unlock();
        }
    }
}
