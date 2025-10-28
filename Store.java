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

    public Store(int position, int tenges, int x, int y) {
        this.position = position;
        this.initialTenges = tenges;
        this.tenges = tenges;

        this.dibujo = new Rectangle();
        this.dibujo.changeColor("red");
        this.dibujo.moveHorizontal(x);
        this.dibujo.moveVertical(y);
        this.dibujo.makeVisible();

        this.screenX = x;
        this.screenY = y;
        this.initialScreenX = x;
        this.initialScreenY = y;
    }

    public void resupply() {
        this.tenges = this.initialTenges;
    }
    
    /**
     * Simulates money collection from the store.
     * Returns the amount of tenges collected and sets it to zero.
     */
    public int collect() {
        int amount = tenges;
        tenges = 0;
        dibujo.changeColor("gray");
        return amount;
    }


    public void reset() {
        moveTo(initialScreenX, initialScreenY);
        this.tenges = this.initialTenges;
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

