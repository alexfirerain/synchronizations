import java.util.Random;

public class Car {
    public static final String[] MODELS = { "AAA", "BBB", "CCC", "DDD" };
    private String model;

    public Car(String model) {
        this.model = model;
    }

    public static Car newRandomCar() {
        return new Car(Car.MODELS[new Random().nextInt(Car.MODELS.length)]);
    }

    @Override
    public String toString() {
        return model;
    }
}
