import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CoffeeMachine {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Map<Ingredient, BigDecimal> ingredientStock = new HashMap<>();
    private final List<Drink> drinks = new ArrayList<>();

    public CoffeeMachine(final Collection<? extends Ingredient> ingredients, final Map<? extends Ingredient, BigDecimal> ingredientStock, final Collection<? extends Drink> drinks) {
        this.ingredients.addAll(ingredients);
        this.ingredientStock.putAll(ingredientStock);
        this.drinks.addAll(drinks);

        this.ingredients.forEach(ingredient -> this.ingredientStock.putIfAbsent(ingredient, BigDecimal.ZERO));
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public List<Drink> getDrinks() {
        return new ArrayList<>(drinks);
    }


    public boolean isOutOfStock(final Drink drink) {
        checkIsValidDrink(drink);
        return drink.getIngredientMap().entrySet().stream()
                .anyMatch(entry -> {
                    Ingredient ingredient = entry.getKey();
                    BigDecimal count = entry.getValue();
                    return !(ingredientStock.containsKey(ingredient) && (ingredientStock.get(ingredient).compareTo(count)!=-1));
                });
    }

    public String getInSufficientItem(final Drink drink) {
        List<Ingredient> ingredients = drink.getIngredients();
        for(Ingredient ingredient: ingredients) {
            if (ingredientStock.containsKey(ingredient) && (ingredient.getCost().compareTo(
                    ingredientStock.get(ingredient)) == 1)) {
                return ingredient.getName();
            }
        }
        return null;
    }

    public String getNotAvailableItem() {
        for(Map.Entry<Ingredient, BigDecimal> entry :ingredientStock.entrySet() ) {
            if (BigDecimal.ZERO.compareTo(entry.getValue()) == 0) {
                return entry.getKey().getName();
            }
        }
        return null;
    }


    public void makeDrink(final Drink drink) {
        checkIsValidDrink(drink);
        if (isOutOfStock(drink)) {
            throw new IllegalArgumentException("Drink " + drink + " is not in stock");
        }
        drink.getIngredients().forEach(this::decrementDrinkValue);
    }

    private void decrementDrinkValue(final Ingredient ingredient) {
        ingredientStock.compute(ingredient, (innerIngredient, stock) -> (stock.subtract(ingredient.getCost())));
    }



    private void checkIsValidDrink(final Drink drink) {
        if (!drinks.contains(drink)) {
            throw new IllegalArgumentException("Unknown drink: " + drink);
        }
    }
}