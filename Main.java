import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        /**
         * Interact with all the components.
         */
        /**
         * I used provide class which deals with the input provider.
         * This take file path and provide data.
         */
        final List<Ingredient> ingredients = ProvideInput.getIngredients();
        final List<Drink> drinks = ProvideInput.getDrinks();
        Map<Ingredient, BigDecimal> ingredientStock = ingredients.stream().
                collect(Collectors.toMap(ingredient -> ingredient, ingredient->ingredient.getCost()));
        CoffeeMachine coffeeMachine = new CoffeeMachine(ingredients, ingredientStock, drinks);

        for(Drink drink : drinks) {
            if (coffeeMachine.isOutOfStock(drink)) {

                System.out.println(String.format("%s is prepared",drink.getName()));
                coffeeMachine.makeDrink(drink);
            } else {
                String item = coffeeMachine.getNotAvailableItem();
                if (item != null)
                System.out.println(String.format("%s cannot be prepared because %s is not available",drink.getName(), item));
                else {
                    System.out.println(String.format("%s cannot be prepared because %s is not sufficient",drink.getName(),
                            coffeeMachine.getInSufficientItem(drink)));
                }
            }
        }
    }
}
