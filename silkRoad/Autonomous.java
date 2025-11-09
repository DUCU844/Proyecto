package silkRoad;

import java.util.Random;

/**
 * The Autonomous class represents a special type of store
 * that chooses its own random position instead of the one assigned.
 * 
 * This class extends {@link Store} and changes its screen coordinates 
 * automatically during creation, giving the store a self-determined location.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class Autonomous extends Store {

    /**
     * Creates a new Autonomous store.
     * After being created, the store moves to a random position on the screen.
     * 
     * @param position logical position of the store in the route
     * @param tenges   amount of money available in the store
     * @param x        initial X coordinate (ignored, replaced by random)
     * @param y        initial Y coordinate (ignored, replaced by random)
     * @param color    color used to draw the store
     */
    public Autonomous(int position, int tenges, int x, int y, String color) {
        super(position, tenges, x, y, color);

        // The store decides its own random position instead of using the given one
        int randomX = (int)(Math.random() * 600 + 100);
        int randomY = (int)(Math.random() * 400 + 100);
        moveTo(randomX, randomY);
    }
}

