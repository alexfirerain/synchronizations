import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Showroom {
    public static final int SALES_PLAN = 10;

    private final Queue<Car> stock = new LinkedList<>();
    final Dealer supplier = new Dealer(this);
    private int carsSold;

    public synchronized void sellACar(Customer buyer) {
        while (stock.isEmpty()) {
            System.out.println("Нет машин в наличии! " + buyer.getName() + " потусуется немного в холле.\n");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        try {
            Car sold = stock.remove();
            carsSold++;
            System.out.println(buyer.getName() + " приобрёл машину " + sold + " и поехал кататься.\n" );
        } catch (NoSuchElementException e) {
            System.out.println("Салон закрылся. " + buyer.getName() + " придёт завтра.");
        }
    }

    public synchronized void obtainACar(Car car) {
        stock.add(car);
        System.out.println("От поставщика получена новая машина " + car);
        notify();
    }

    public boolean planFulfilled() {
        return carsSold >= SALES_PLAN;
    }

}
