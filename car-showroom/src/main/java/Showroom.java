import java.util.LinkedList;
import java.util.List;

public class Showroom {
    public static final int SALES_PLAN = 10;
    public static final int PLAN_CHEK_PERIOD = 1000;
    private static final String[] customers = {"Покупатель А", "Покупатель Б", "Покупатель В", "Покупатель Г" };

    private final List<Car> stock = new LinkedList<>();
    private final Dealer supplier = new Dealer(this);
    private int carsSold;

//    private final Seller seller = new Seller(this);



    public synchronized void sellACar(Customer buyer) {
        if (stock.isEmpty()) {
            System.out.println("Нет ни машины в наличии");
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        carsSold++;
//        return seller.sellACar();
    }

    public void obtainACar(Car car) {
        stock.add(car);
        System.out.println("Получена новая машина " + car);
        notifyAll();
    }

    public List<Car> showStock() {
        return stock;
    }

    public static void main(String[] args) {
        // инстанцилизация магазина
        final Showroom showroom = new Showroom();

        // запуск поставщика
        Thread dealer = new Thread(showroom.supplier);
        dealer.start();

        // запуск покупателей
        ThreadGroup buyers = new ThreadGroup("покупатели");
        for (String name : customers)
            new Thread(buyers, new Customer(name, showroom)).start();

        // проверка выполнения плана
        while (showroom.carsSold < SALES_PLAN) {
            try {
                Thread.sleep(PLAN_CHEK_PERIOD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dealer.interrupt();
        buyers.interrupt();
        System.out.println(SALES_PLAN + " машин продано. Всем спасибо, все свободны!");
    }

    public static void randomDelay(int avg) throws InterruptedException {
        Thread.sleep((long) ((Math.random() + 0.5) * avg));
    }
}
