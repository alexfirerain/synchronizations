import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Lunchroom {
    static final int SALES_PLAN = 5;             // сколько надо посетителей для плана

    final private String name;                   // название заведения
    final Cook cook;                             // штатный повар
    final Set<Waiter> waiters;                   // штат официантов
    private final Set<Customer> customers;       // посетители в заведении

    private int customersCome;                   // сколько посетителей зашло

    private boolean isOpen = true;               // работает ли заведение

    public Lunchroom(String name, int numberOfWaiters) {
        this.name = name;
        cook = new Cook(this);
        customers = new HashSet<>(SALES_PLAN);
        waiters = new HashSet<>(numberOfWaiters);
        IntStream.range(0, numberOfWaiters).forEach(i -> waiters.add(new Waiter(this)));
        welcome();
    }

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
        if (customers.isEmpty() && !isOpenForEntrance()) {
            farewell();
            isOpen = false;
        }
    }

    public void welcome() {
        System.out.printf("\"%s\" открыт для посетителей!%n", name);
    }

    public void farewell() {
        System.out.printf("\"%s\" обслужил %d посетителей и на сегодня закрывается!%n", name, SALES_PLAN);

    }
}
