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

        while (restaurant.isOpen()) {
            // запуск посетителей
            while(restaurant.isOpenForEntrance()) {
                timePass(CUSTOMERS_ENTRANCE_DELAY);
                persons.execute(new Customer(restaurant));
            }
        }

        persons.shutdown();
    }


    public static void timePass(int avg) {
        try {
            Thread.sleep((long) ((Math.random() + 0.5) * avg));
        } catch (InterruptedException e) {
            System.out.println("Нежданное прерывание в "
                    + Thread.currentThread().getStackTrace()[1].getClassName());
            e.printStackTrace();
        }
    }

}
