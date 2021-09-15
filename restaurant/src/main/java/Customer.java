public class Customer implements Runnable {
    static private int N;                   // количество посетителей в игровом мире

    private final Lunchroom lunchroom;      // организация, в которой ест
    private final int id;                   // номер посетителя

    public Customer(Lunchroom lunchroom) {
        this.lunchroom = lunchroom;
        id = ++N;
    }
    @Override
    public String toString() {
        return "Посетитель " + id;
    }
    @Override
    public void run() {
        lunchroom.haveCustomerCome(this);
        // некоторое время изучать меню

        // получить свободного официанта, дать ему заказ

        // блокироваться и ждать получения блюда

        // получить блюдо и некоторое время кушать

        // закончив, попрощаться и уйти
        lunchroom.haveCustomerServed(this);
    }
}
