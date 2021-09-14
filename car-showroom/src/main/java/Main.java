public class Main {
    private static final String[] customers = { "Покупатель А", "Покупатель Б", "Покупатель В", "Покупатель Г" };
    private static final int PLAN_CHECK_PERIOD = 1000;
    private static final int CUSTOMERS_ENTRANCE_DELAY = 700;

    public static void main(String[] args) {
        // инстанцилизация магазина
        final Showroom showroom = new Showroom();

        // запуск поставщика
        Thread dealer = new Thread(showroom.supplier);
        dealer.start();

        // запуск покупателей
        ThreadGroup buyers = new ThreadGroup("покупатели");
        for (String name : customers) {
            new Thread(buyers, new Customer(name, showroom)).start();
            try {
                randomDelay(CUSTOMERS_ENTRANCE_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // проверка выполнения плана
        while (!showroom.planFulfilled()) {
            try {
                Thread.sleep(PLAN_CHECK_PERIOD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dealer.interrupt();
        buyers.interrupt();
        System.out.println("План продажи " + Showroom.SALES_PLAN + " машин выполнен. Всем спасибо, все свободны!");
    }

    public static void randomDelay(int avg) throws InterruptedException {
        Thread.sleep((long) ((Math.random() + 0.5) * avg));
    }
}
