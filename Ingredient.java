import java.math.BigDecimal;
import java.util.Objects;

public class Ingredient {
    private final String name;
    private final BigDecimal cost;

    public Ingredient(final String name, final BigDecimal cost) {
        this.name = Objects.requireNonNull(name, "name");
        this.cost = Objects.requireNonNull(cost, "cost");
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }
}