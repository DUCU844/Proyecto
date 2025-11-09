package silkRoad;

/**
 * The Fighter class represents a special type of store
 * that only allows robots with more money than the store itself
 * to collect its tenges (money).
 * 
 * If the robot has less or equal money, it cannot take anything.
 * This behavior simulates a "competitive" store that only yields
 * to stronger robots.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class Fighter extends Store {

    /**
     * Creates a new Fighter store.
     * This store always uses the color red for identification.
     * 
     * @param position logical position of the store in the route
     * @param tenges   initial amount of money the store holds
     * @param x        X coordinate on the screen
     * @param y        Y coordinate on the screen
     * @param color    ignored parameter (the store is always red)
     */
    public Fighter(int position, int tenges, int x, int y, String color) {
        super(position, tenges, x, y, "red");
    }

    /**
     * Allows collection only if the robot has more money than the store.
     * Otherwise, the robot cannot take anything.
     * 
     * @param robotMoney current amount of money the robot has
     * @return the collected money, or 0 if the robot is not allowed
     */
    public int collect(int robotMoney) {
        // Solo entrega su dinero si el robot tiene más dinero que la tienda
        if (robotMoney > getTenges()) {
            return super.collect(); // El robot puede vaciar la tienda
        } else {
            System.out.println("El robot no tiene suficiente dinero para vaciar esta FighterStore.");
            return 0;
        }
    }
}



