import java.util.LinkedList;
import java.util.Queue;

public class Showroom {
    public static final int SALES_PLAN = 10;

    private final Queue<Car> stock = new LinkedList<>();
    final Dealer supplier = new Dealer(this);
    private int carsSold;

    public synchronized void sellACar(Customer buyer) {
        while (stock.isEmpty()) {
            System.out.println("Нет ни машины в наличии");
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        Car sold = stock.remove();
        carsSold++;
        System.out.println(buyer.getName() + " приобрёл машину " + sold);
    }

    public synchronized void obtainACar(Car car) {
        stock.add(car);
        System.out.println("Получена новая машина " + car);
        notify();
    }

    public boolean planFulfilled() {
        return carsSold >= SALES_PLAN;
    }

}
