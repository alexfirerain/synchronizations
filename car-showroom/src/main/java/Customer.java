import static java.lang.Thread.interrupted;

public class Customer implements Runnable {
    private final int AVRG_EXPLUATATION_PERIOD = 7000;
    private final String name;
    private final Showroom shop;

    public Customer(String name, Showroom shop) {
        this.name = name;
        this.shop = shop;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            System.out.println(name + " пришёл в салон купить машину.");
            shop.sellACar(this);

            try {
                Showroom.randomDelay(AVRG_EXPLUATATION_PERIOD);
            } catch (InterruptedException ignored) {
            }
            System.out.println(name + " наездился на старой машине и пошёл за новой.");
        }
    }
}
