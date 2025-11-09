package silkRoad;

/**
 * The Discount class represents a special type of store
 * that only delivers 75% of its total money when a robot collects from it.
 * 
 * This class extends {@link Store} and modifies the collection behavior 
 * to apply a discount on the amount given.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class Discount extends Store {

    /**
     * Creates a new Discount store.
     * This store always uses the color orange to identify it visually.
     * 
     * @param position logical position of the store in the route
     * @param tenges   amount of money the store starts with
     * @param x        X coordinate on screen
     * @param y        Y coordinate on screen
     * @param color    ignored parameter (the store is always orange)
     */
    public Discount(int position, int tenges, int x, int y, String color) {
        super(position, tenges, x, y, "orange");
    }

    /**
     * Collects money from the store but only gives 75% of it.
     * The remaining 25% stays as a discount.
     * 
     * @return 75% of the original collected amount
     */
    @Override
    public int collect() {
        int amount = super.collect(); // Llama al método de la clase padre
        return (int)(amount * 0.75);  // Devuelve solo el 75%
    }
}

