package silkRoad;

import java.awt.Color;
import java.awt.Graphics;
import shapes.Circle;

/**
 * The Robot class represents a robot that can move visually on the Silk Road.
 * It keeps track of its screen coordinates and graphical representation.
 * 
 * Each robot has:
 * - A current position (x, y)
 * - An initial position (for reset)
 * - A size and color
 * - A Circle object that draws it on the screen
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class Robot {
    private int x;
    private int y;
    private int size;
    private int initialX;
    private int initialY;
    private Circle dibujo;
    private String color;

    /**
     * Creates a new Robot at the given position with a specific size and color.
     * The robot is immediately drawn on the screen.
     * 
     * @param x      initial X coordinate
     * @param y      initial Y coordinate
     * @param size   diameter of the robot
     * @param color  color name of the robot
     */
    public Robot(int x, int y, int size, String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        initialX = x;
        initialY = y;
        this.color = color;

        dibujo = new Circle();
        dibujo.changeColor(color);
        dibujo.changeSize(size);
        dibujo.moveHorizontal(x - 20);
        dibujo.moveVertical(y - 15);
        dibujo.makeVisible();
    }

    /**
     * Moves the robot smoothly to a new position on the screen.
     * Updates both its logical and graphical coordinates.
     * 
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    public void moveTo(int newX, int newY) {
        int dx = newX - x;
        int dy = newY - y;

        // Movimiento visual suave del círculo
        dibujo.slowMoveHorizontal(dx);
        dibujo.slowMoveVertical(dy);

        x = newX;
        y = newY;
    }

    /**
     * Returns the robot to its initial position.
     */
    public void reset() {
        moveTo(initialX, initialY);
    }

    /**
     * Deletes the robot from the screen.
     * Makes its visual representation invisible.
     */
    public void delete() {
        dibujo.makeInvisible();
    }
    
    /**
     * Makes the robot visible on the screen.
     */
    public void makeVisible() {
        dibujo.makeVisible();
    }
    
    /**
     * Makes the robot blink a few times.
     * The robot temporarily changes color to yellow and back.
     */
    public void blink() {
        String originalColor = color;
        // Efecto de parpadeo
        for (int i = 0; i < 3; i++) {
            dibujo.changeColor("yellow");
            dibujo.changeColor(originalColor);
        }
    }
    
    /**
     * Returns the current X coordinate of the robot.
     * @return x coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the current Y coordinate of the robot.
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
}
