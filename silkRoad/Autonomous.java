package silkRoad;


import java.util.Random;
/**
 * This store chooses the position where it wants to be located, not the one it is told.
 * 
 * @author Beltrán - Ducuara
 * @version 2025-2
 */
public class Autonomous extends Store {

    public Autonomous(int position, int tenges, int x, int y, String color) {
        super(position, tenges, x, y, color);

        // Decide su propia posición aleatoria
        int randomX = (int)(Math.random() * 600 + 100);
        int randomY = (int)(Math.random() * 400 + 100);
        moveTo(randomX, randomY);
    }
}
