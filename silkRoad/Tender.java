package silkRoad;

/**
 * The Tender class represents a special type of robot
 * that only collects half of the available profit from each store it visits.
 * 
 * This class extends {@link Robot} and customizes the money collection behavior.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class Tender extends Robot {

    /**
     * Creates a new Tender robot.
     * This robot always uses the color pink to distinguish it visually.
     * 
     * @param x      initial X coordinate
     * @param y      initial Y coordinate
     * @param size   diameter of the robot
     * @param color  ignored parameter (always pink)
     */
    public Tender(int x, int y, int size, String color) {
        super(x, y, size, "pink");
    }

    /**
     * Collects money from a store but only keeps half of it.
     * 
     * @param store the store to collect from
     * @return half of the total amount collected from the store
     */
    public int collectFrom(Store store) {
        // Obtiene el total del dinero de la tienda
        int total = store.collect();
        // Devuelve la mitad del dinero recolectado
        return total / 2;
    }
}
