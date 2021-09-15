import static java.lang.Thread.interrupted;

public class Waiter implements Runnable {
    static private int N;               // количество официантов в игровом мире

    private final Lunchroom company;    // организация где работает
    private final int id;               // номер официанта в организации

    private boolean isFree = true;      // флажок доступности официанта
    public boolean isFree() {
        return isFree;
    }

    public Waiter(Lunchroom company) {
        this.company = company;
        id = ++N;
    }
    @Override
    public String toString() {
        return "Официант " + id;
    }

    @Override
    public void run() {
        while (company.isOpen() && !interrupted()) {

            // блокироваться и ожидать заказа посетителя

            // передать заказ повару (он ставит его в очередь ?) // нет, для упрощения просто ждёт, пока тот освободится

            // ждать, пока повар сготовит блюдо // по-хорошему, нужно было бы обслуживать новых посетителей

            // отнести блюдо посетителю

            // возврат к началу;
        }
    }
}
