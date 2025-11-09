package silkRoad;

import shapes.Rectangle;

/**
 * The Store class represents a shop on the Silk Road.
 * Each store has a position, an amount of money (tenges),
 * and a visual representation on the screen using a rectangle.
 * 
 * Stores can be resupplied, moved, reset, or emptied when a robot collects money.
 * This class manages both the logical and graphical state of each store.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class Store {
    private int position;
    private int initialTenges;
    private int tenges;
    private Rectangle dibujo;
    private int screenX;
    private int screenY;
    private int initialScreenX;
    private int initialScreenY;
    
    private String color;
    private String initialColor;
    private int emptiedCount;

    /**
     * Creates a new Store with its position, initial money, and visual location.
     * 
     * @param position   logical position of the store in the route
     * @param tenges     initial amount of money in the store
     * @param x          X coordinate on screen
     * @param y          Y coordinate on screen
     * @param color      color used to draw the store
     */
    public Store(int position, int tenges, int x, int y, String color) {
        this.position = position;
        this.initialTenges = tenges;
        this.tenges = tenges;
        this.color = color;
        initialColor = color;
        emptiedCount = 0;

        dibujo = new Rectangle();
        dibujo.changeColor(color);
        dibujo.changeSize(40, 40);
        dibujo.moveHorizontal(x);
        dibujo.moveVertical(y);
        dibujo.makeVisible();

        screenX = x;
        screenY = y;
        initialScreenX = x;
        initialScreenY = y;
    }

    /**
     * Resupplies the store with its initial amount of money
     * and restores its original color.
     */
    public void resupply() {
        tenges = initialTenges;
        color = initialColor;
        dibujo.changeColor(initialColor);
    }
    
    /**
     * Simulates collecting money from the store.
     * Returns the amount collected and sets the store’s money to zero.
     * Also changes the color to gray when emptied.
     * 
     * @return the amount of tenges collected
     */
    public int collect() {
        int amount = tenges;
        tenges = 0;
        
        // Si la tienda tenía dinero, aumenta el contador de vaciados
        if (amount > 0) {
            emptiedCount++;
        }

        dibujo.changeColor("gray");
        return amount;
    }

    /**
     * Resets the store to its original state.
     * Restores position, money, and color.
     */
    public void reset() {
        moveTo(initialScreenX, initialScreenY);
        tenges = initialTenges;
        color = initialColor;
        emptiedCount = 0;
        dibujo.changeColor(initialColor);
    }
    
    /**
     * Makes the store visible on screen.
     */
    public void makeVisible() {
        dibujo.makeVisible();
    }
    
    /**
     * Returns how many times the store has been emptied.
     * @return number of times the store was collected from
     */
    public int getEmptiedCount() {
        return emptiedCount;
    }

    /**
     * Moves the store to a new position on screen.
     * Updates both graphical and logical coordinates.
     * 
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    public void moveTo(int newX, int newY) {
        int dx = newX - screenX;
        int dy = newY - screenY;
        dibujo.moveHorizontal(dx);
        dibujo.moveVertical(dy);
        screenX = newX;
        screenY = newY;
    }

    /**
     * Deletes the store from the screen.
     * Makes the rectangle invisible.
     */
    public void delete() {
        dibujo.makeInvisible();
    }
    
    /**
     * Returns the logical position of the store.
     * @return store position in the route
     */
    public int getPosition() {
        return position;
    }
    
    /**
     * Returns the current X coordinate of the store on screen.
     * @return X coordinate
     */
    public int getScreenX() {
        return screenX;
    }
    
    /**
     * Returns the current Y coordinate of the store on screen.
     * @return Y coordinate
     */
    public int getScreenY() {
        return screenY;
    }
    
    /**
     * Returns the current amount of tenges (money) in the store.
     * @return current money
     */
    public int getTenges() {
        return tenges;
    }
    
    /**
     * Returns the initial amount of tenges (money) in the store.
     * @return initial money
     */
    public int getInitialTenges() {
        return initialTenges;
    }
}
