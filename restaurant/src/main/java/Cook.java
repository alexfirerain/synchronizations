import static java.lang.Thread.interrupted;

public class Cook implements Runnable {
    final Lunchroom company;

    public Cook(Lunchroom company) {
        this.company = company;
    }

    @Override
    public void run() {
        while (company.isOpen() && !interrupted()) {
            // взять заказ; если его нет, то блокироваться и ожидать
            // получить заказ
            // готовить некоторое время
            // уведомить о готовности заказа

            // возврат к началу; если план выполнен, закончить
        }
    }
}
