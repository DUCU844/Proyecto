package shapes;

/**
 * Clase abstracta Shape
 * Representa la forma base de todas las figuras geométricas
 * 
 * @author Beltrán-Ducuara
 * @version 2025-2
 */
public abstract class Shape {
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;
    
    /**
     * Hace visible la figura
     */
    public abstract void makeVisible();
    
    /**
     * Hace invisible la figura
     */
    public abstract void makeInvisible();
    
    /**
     * Mueve la figura horizontalmente
     * @param distance distancia en píxeles
     */
    public abstract void moveHorizontal(int distance);
    
    /**
     * Mueve la figura verticalmente
     * @param distance distancia en píxeles
     */
    public abstract void moveVertical(int distance);
    
    /**
     * Mueve la figura lentamente en horizontal
     * @param distance distancia en píxeles
     */
    public abstract void slowMoveHorizontal(int distance);
    
    /**
     * Mueve la figura lentamente en vertical
     * @param distance distancia en píxeles
     */
    public abstract void slowMoveVertical(int distance);
    
    /**
     * Cambia el color de la figura
     * @param newColor nuevo color
     */
    public abstract void changeColor(String newColor);
    
    /**
     * Dibuja la figura en el canvas
     */
    protected abstract void draw();
    
    /**
     * Borra la figura del canvas
     */
    protected abstract void erase();
    
    // Métodos comunes
    public void moveRight() {
        moveHorizontal(20);
    }
    
    public void moveLeft() {
        moveHorizontal(-20);
    }
    
    public void moveUp() {
        moveVertical(-20);
    }
    
    public void moveDown() {
        moveVertical(20);
    }
    
    public boolean isVisible() {
        return isVisible;
    }
    
    public String getColor() {
        return color;
    }
}