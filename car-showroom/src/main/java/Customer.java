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
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(name + " пришёл в салон купить машину.");
            shop.sellACar(this);

            try {
                Main.randomDelay(AVRG_EXPLUATATION_PERIOD);
            } catch (InterruptedException ignored) {
                break;
            }
            System.out.println(name + " наездился на старой машине и пошёл за новой.");
        }
    }
}
