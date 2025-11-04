package silkRoad;

/**
 * This robot never turns back
 * 
 * @author Beltrán - Ducuara
 * @version 2025-2
 */
public class NeverBack extends Robot {

    public NeverBack(int x, int y, int size, String color) {
        super(x, y, size, "magenta");
    }

    @Override
    public void moveTo(int newX, int newY) {
        if (newY < getY()) {
            System.out.println("El robot Fighter no puede retroceder.");
        } else {
            super.moveTo(newX, newY);
        }
    }
}

