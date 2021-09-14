import static java.lang.Thread.interrupted;

public class Customer implements Runnable {
    private final int AVRG_EXPLUATATION_PERIOD = 7000;
    private final String name;
    private final Showroom shop;

    public Customer(String name, Showroom shop) {
        this.name = name;
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        System.out.println(name + " пришёл в салон купить машину.");
        while (!interrupted()) {
            shop.sellACar(this);
            try {
                Main.randomDelay(AVRG_EXPLUATATION_PERIOD);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println(name + " накатался на старой машине и пришёл в салон за новой.");
        }
    }
}
