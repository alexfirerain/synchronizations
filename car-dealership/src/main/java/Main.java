import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String[] customers = { "Покупатель А", "Покупатель Б", "Покупатель В", "Покупатель Г" };
    private static final int PLAN_CHECK_PERIOD = 1000;
    private static final int CUSTOMERS_ENTRANCE_DELAY = 300;

    public static void main(String[] args) {
        // инстанцилизация магазина
        final Showroom showroom = new Showroom();
        ExecutorService actors = Executors.newCachedThreadPool();

        // запуск поставщика
        actors.execute(showroom.supplier);

        // запуск покупателей
        Arrays.stream(customers).map(x -> {
            try { randomDelay(CUSTOMERS_ENTRANCE_DELAY); }
            catch (InterruptedException e) { e.printStackTrace(); }
            return new Customer(x, showroom);
        }).forEach(actors::execute);

        // или так, не могу решить, что красивее
//        for (String name : customers) {
//            actors.execute(new Customer(name, showroom));
//            try { randomDelay(CUSTOMERS_ENTRANCE_DELAY); }
//            catch (InterruptedException e) { e.printStackTrace(); }
//        }

        // проверка выполнения плана
        while (!showroom.planFulfilled()) {
            try { Thread.sleep(PLAN_CHECK_PERIOD); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println("План продажи " + Showroom.SALES_PLAN + " машин выполнен. Всем спасибо, все свободны!");
        actors.shutdownNow();
    }

    public static void randomDelay(int avg) throws InterruptedException {
        Thread.sleep((long) ((Math.random() + 0.5) * avg));
    }
}
