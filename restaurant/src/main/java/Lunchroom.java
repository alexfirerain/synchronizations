import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Lunchroom {
    static final int SALES_PLAN = 5;             // сколько надо посетителей для плана

    final private String name;                   // название заведения
    final Cook cook;                             // штатный повар
    final Set<Waiter> waiters;                   // штат официантов
    private final Set<Customer> customers;       // посетители в заведении

    private final AtomicInteger customersCome
            = new AtomicInteger(0);                   // сколько посетителей зашло
    private boolean open;                        // работает ли заведение

    public Lunchroom(String name, int numberOfWaiters) {
        this.name = name;
        cook = new Cook(this);
        waiters = new HashSet<>(numberOfWaiters);
        customers = new HashSet<>(SALES_PLAN);
        IntStream.range(0, numberOfWaiters).forEach(i -> waiters.add(new Waiter(this)));
        open = true;
        welcome();
    }

    public boolean isOpenForEntrance() {
        return customersCome.get() < SALES_PLAN;
    }

    public boolean isOpen() {
        return open;
    }

    public void haveCustomerCome(Customer customer) {
        System.out.println(customer + " зашёл похавать");
        customers.add(customer);
        customersCome.getAndIncrement();
    }

    public void haveCustomerServed(Customer customer) {
        System.out.println(customer + " благодарит за трапезу и откланивается");
        customers.remove(customer);
        if (customers.isEmpty() && !isOpenForEntrance()) {
            farewell();
            open = false;
        }
    }

    public void welcome() {
        System.out.printf("\t<<< \"%s\" открыт для посетителей! >>>%n", name);
    }

    public void farewell() {
        System.out.printf("\t<<< \"%s\" обслужил %d посетителей и на сегодня закрывается! >>>%n", name, customersCome.get());

    }
}
