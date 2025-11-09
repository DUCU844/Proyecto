package silkRoad;

/**
 * The NeverBack class represents a special type of robot
 * that never moves backward (upward on the screen).
 * 
 * It extends {@link Robot} and overrides the moveTo() method 
 * to restrict any backward movement.
 * 
 * If a move command tries to take the robot back (to a smaller Y value),
 * the robot ignores it and displays a warning message.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class NeverBack extends Robot {

    /**
     * Creates a new NeverBack robot.
     * This robot always uses the color magenta for identification.
     * 
     * @param x      initial X coordinate
     * @param y      initial Y coordinate
     * @param size   diameter of the robot
     * @param color  ignored parameter (always magenta)
     */
    public NeverBack(int x, int y, int size, String color) {
        super(x, y, size, "magenta");
    }

    /**
     * Moves the robot to a new position only if it is not moving backward.
     * If the new Y position is smaller than the current one,
     * the robot will not move and a warning will be printed.
     * 
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    @Override
    public void moveTo(int newX, int newY) {
        // Evita que el robot se devuelva en dirección vertical
        if (newY < getY()) {
            System.out.println("El robot Fighter no puede retroceder.");
        } else {
            super.moveTo(newX, newY);
        }
    }
}
