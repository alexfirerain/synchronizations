import java.util.ArrayList;
import java.util.List;

public class Showroom {
    public static final int MIN_DELAY = 500;
    public static final int MAX_DELAY = 3000;
    private final Seller seller = new Seller(this);
    private List<Car> stock = new ArrayList<>();
    private final String[] customers = {"Покупатель А","Покупатель Б", "Покупатель В", "Покупатель Г" };

    public Car sellACar() {
        return seller.sellACar();
    }

    public void obtainACar() {
        seller.acceptACar();
    }

    public List<Car> showStock() {
        return stock;
    }

    public static void main(String[] args) {
        final Showroom showroom = new Showroom();

        new Thread(showroom::sellACar, "Покупатель").start();

        new Thread(showroom::obtainACar, "Поставщик").start();
    }

    private static void randomDelay() throws InterruptedException {
        Thread.sleep((long) (MAX_DELAY * Math.random() + MIN_DELAY));
    }
}
