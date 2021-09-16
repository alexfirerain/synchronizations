import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ExecutorService persons = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        Lunchroom restaurant = new Lunchroom("Едарьом", 3);
        persons.execute(restaurant.cook);
        restaurant.waiters.forEach(persons::execute);




    }

    public static void timePass(int avg) {
        try {
            Thread.sleep((long) (avg * (Math.random() + 0.5)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
