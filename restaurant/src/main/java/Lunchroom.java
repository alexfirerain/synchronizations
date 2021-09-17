import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Lunchroom {
    static final int SALES_PLAN = 5;              // сколько надо посетителей для плана

    final private String name;                    // название заведения
    final Cook cook;                              // штатный повар
    final List<Waiter> waiters;                   // штат официантов
    private List<Customer> customers;             // посетители в заведении

    private int customersCome;                    // сколько посетителей зашло

    private boolean isOpen = true;                // работает ли заведение

    public Lunchroom(String name, int numberOfWaiters) {
        this.name = name;
        cook = new Cook(this);
        customers = new ArrayList<>(SALES_PLAN);
        waiters = new ArrayList<>(numberOfWaiters);
        IntStream.range(0, numberOfWaiters).forEach(i -> waiters.add(new Waiter(this)));
        welcome();
    }


    // обеспечить работой потоки, координировать их
    // считать заказавших посетителей; когда наберётся план, перестать принимать новых
    // когда уйдёт последний посетитель, закрыть заведение

    public boolean isOpenForEntrance() {
        return customersCome < SALES_PLAN;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void haveCustomerCome(Customer customer) {
        System.out.println(customer + " зашёл похавать");
        customers.add(customer);
        customersCome++;
    }

    public void haveCustomerServed(Customer customer) {
        System.out.println(customer + " благодарит за трапезу и откланивается");
        customers.remove(customer);
        if (!isOpenForEntrance() && customers.isEmpty()) {
            isOpen = false;
            farewell();
        }
    }

    public void welcome() {
        System.out.printf("\"%s\" открыт для посетителей!%n", name);
    }

    public void farewell() {
        System.out.printf("\"%s\" обслужил %d посетителей и на сегодня закрывается!%n", name, SALES_PLAN);
    }
}
