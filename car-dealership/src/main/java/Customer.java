import static java.lang.Thread.interrupted;

public class Customer implements Runnable {
    private static final int AVG_EXPLOITATION_PERIOD = 7000;

    private final String name;
    private final Showroom shop;

    public Customer(String name, Showroom shop) {
        this.name = name;
        this.shop = shop;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        System.out.println(name + " пришёл в салон купить машину.");
        while (!interrupted()) {
            try {
                shop.sellACar(this);
                Main.randomDelay( AVG_EXPLOITATION_PERIOD );
                System.out.println(name + " накатался на старой машине и пришёл в салон за новой.");
            } catch (StoreClosingException e) {
                System.out.println(e.getMessage());
                return;
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
