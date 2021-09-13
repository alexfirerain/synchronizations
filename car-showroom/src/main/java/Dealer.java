import java.util.Random;

import static java.lang.Thread.interrupted;

public class Dealer implements Runnable {
    public static final int RELEASE_PERIOD = 3000;
    private static final String[] MODELS = { "AAA", "BBB", "CCC", "DDD" };

    final Showroom partner;

    public Dealer(Showroom partner) {
        this.partner = partner;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                Thread.sleep(RELEASE_PERIOD);
                provideACar(partner);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void provideACar(Showroom partner) {
        partner.obtainACar(new Car(MODELS[new Random().nextInt(MODELS.length)]));
    }
}
