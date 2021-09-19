import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    static ExecutorService persons = Executors.newCachedThreadPool();
    static final int CUSTOMERS_ENTRANCE_DELAY = 4000;

    public static void main(String[] args) {
        Lunchroom restaurant = new Lunchroom("Едарьом", 3);
        restaurant.waiters.forEach(persons::execute);

        while (restaurant.isOpen()) {
            while(restaurant.isOpenForEntrance()) {
                timePass(CUSTOMERS_ENTRANCE_DELAY);
                persons.execute(new Customer(restaurant));
            }
        }

//        for (Runnable r : persons.shutdownNow())
//            System.out.println(r.toString());
//        Thread.currentThread().interrupt();
//        try {
//            persons.awaitTermination(5, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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
