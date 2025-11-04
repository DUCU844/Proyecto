package silkRoad;

import shapes.Rectangle;
/**
 * Clase Store
 * Representa una tienda en la Ruta de la Seda (versión con coordenadas de pantalla).
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

    public Store(int position, int tenges, int x, int y, String color) {
        this.position = position;
        this.initialTenges = tenges;
        this.tenges = tenges;
        this.color = color;
        initialColor = color;
        emptiedCount = 0;

        dibujo = new Rectangle();
        dibujo.changeColor(color);
        dibujo.changeSize(40,40);
        dibujo.moveHorizontal(x);
        dibujo.moveVertical(y);
        dibujo.makeVisible();

        screenX = x;
        screenY = y;
        initialScreenX = x;
        initialScreenY = y;
    }

    public void resupply() {
        tenges = initialTenges;
        color = initialColor;
        dibujo.changeColor(initialColor);
    }
    
    /**
     * Simulates money collection from the store.
     * Returns the amount of tenges collected and sets it to zero.
     */
    public int collect() {
        int amount = tenges;
        tenges = 0;
        
        if (amount > 0) {
            emptiedCount++;
        }

        dibujo.changeColor("gray");
        return amount;
    }


    public void reset() {
        moveTo(initialScreenX, initialScreenY);
        tenges = initialTenges;
        color = initialColor;
        emptiedCount = 0;
        dibujo.changeColor(initialColor);
    }
    
    public void makeVisible(){
        dibujo.makeVisible();
    }
    
    public int getEmptiedCount() {
        return emptiedCount;
    }

    public void moveTo(int newX, int newY) {
        int dx = newX - screenX;
        int dy = newY - screenY;
        dibujo.moveHorizontal(dx);
        dibujo.moveVertical(dy);
        screenX = newX;
        screenY = newY;
    }

    public void delete() {
        dibujo.makeInvisible();
    }
    
    /**
     * Returns the current X coordinate of the store on screen.
     */
    public int getScreenX() {
        return screenX;
    }
    
    /**
     * Returns the current Y coordinate of the store on screen.
     */
    public int getScreenY() {
        return screenY;
    }
    /**
     * Returns the current amount of tenges (money) in the store.
     */
    public int getTenges() {
        return tenges;
    }

}

