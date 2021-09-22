import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Showroom {
    public static final int SALES_PLAN = 10;

    private final Queue<Car> stock = new LinkedList<>();
    protected final Dealer supplier = new Dealer(this);

    Lock sync = new ReentrantLock();
    Condition carReceived = sync.newCondition();

    private int carsSold;

    public void sellACar(Customer buyer) throws StoreClosingException {
        try {
            sync.lock();
            while (stock.isEmpty()) {
                System.out.println("Нет машин в наличии! " + buyer + " потусуется немного в холле.\n");
                carReceived.await();
            }
            Car sold = stock.remove();
            carsSold++;
            System.out.println(buyer + " приобрёл машину " + sold + " и поехал кататься.\n");
        } catch (NoSuchElementException | InterruptedException e) {
            throw new StoreClosingException("Салон закрылся. " + buyer + " наверное придёт завтра.");
        } finally {
            sync.unlock();
        }
    }

    public synchronized void obtainACar(Car car) {
        try {
            sync.lock();
            stock.add(car);
            System.out.println("От поставщика получена новая машина " + car);
            carReceived.signal();
        } finally {
            sync.unlock();
        }
    }

    public boolean planFulfilled() {
        return carsSold >= SALES_PLAN;
    }

}
