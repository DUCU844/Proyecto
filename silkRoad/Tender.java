package silkRoad;

/**
 * This robot only collects half of the profits
 * @author Beltrán - Ducuara
 * @version 2025-2
 */
public class Tender extends Robot {

    public Tender(int x, int y, int size, String color) {
        super(x, y, size, color);
    }

    // Método que se llama cuando recolecta dinero
    public int collectFrom(Store store) {
        int total = store.collect();
        return total / 2;
    }
}

