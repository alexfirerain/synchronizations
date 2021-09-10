public class Seller {
    private final Showroom shop;

    public Seller(Showroom shop) {
        this.shop = shop;
    }

    public synchronized Car sellACar() {
        try {
            System.out.println("Продаём машины");
            while (shop.showStock().isEmpty()) {
                System.out.println("Машин в наличии нет!");
                wait();
            }
            Thread.sleep(1000);
            System.out.println("Машина продана!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return shop.showStock().remove(0);
    }

    public synchronized void acceptACar() {
        try {
            System.out.println("Поставщик пригнал новую машину");
            Thread.sleep(3000);
            shop.showStock().add(Car.newRandomCar());
            System.out.println("Новая машина получена в салон");
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
