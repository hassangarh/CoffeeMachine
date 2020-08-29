import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Drink {
    private final String name;
    private final List<Ingredient> ingredients = new ArrayList<>();

    private final BigDecimal cost;

    public Drink(final String name, final Ingredient... ingredients) {
        this(name, Arrays.asList(ingredients));
    }

    public Drink(final String name, final Collection<? extends Ingredient> ingredients) {
        this.name = Objects.requireNonNull(name, "name");
        this.ingredients.addAll(ingredients);

        this.cost = this.ingredients.stream().map(Ingredient::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Map<Ingredient, BigDecimal> getIngredientMap() {
        return ingredients.stream()
                .collect(Collectors.toMap(ingredient -> ingredient, ingredient->ingredient.getCost()));
    }
}