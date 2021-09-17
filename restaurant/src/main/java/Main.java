import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ExecutorService persons = Executors.newCachedThreadPool();
    static final int CUSTOMERS_ENTRANCE_DELAY = 4000;

    public static void main(String[] args) {
        // инстанцилизация ресторана, запуск персонала
        Lunchroom restaurant = new Lunchroom("Едарьом", 3);
        persons.execute(restaurant.cook);
        restaurant.waiters.forEach(persons::execute);

        // запуск посетителей
        while(restaurant.isOpenForEntrance()) {
            timePass(CUSTOMERS_ENTRANCE_DELAY);
            persons.execute(new Customer(restaurant));
        }

        persons.shutdownNow();
    }

    public static void timePass(int avg) {
        try {
            Thread.sleep((long) (avg * (Math.random() + 0.5)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
