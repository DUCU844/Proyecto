package silkRoad;

import java.awt.Color;
import java.awt.Graphics;
import shapes.Circle;

/**
 * Clase Robot
 * Versión que conserva coordenadas en pantalla y puede moverse a una
 * posición lógica actualizando la representación gráfica.
 */


public class Robot {
    private int x;
    private int y;
    private int size;
    private int initialX;
    private int initialY;
    private Circle dibujo;
    private String color;

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

    public void moveTo(int newX, int newY) {
        int dx = newX - x;
        int dy = newY - y;
        dibujo.slowMoveHorizontal(dx);
        dibujo.slowMoveVertical(dy);
        x = newX;
        y = newY;
    }

    public void reset() {
        moveTo(initialX, initialY);
    }

    public void delete() {
        dibujo.makeInvisible();
    }
    
    public void makeVisible() {
        dibujo.makeVisible();
    }
    
    public void blink() {
        String originalColor = color;
        for(int i = 0; i < 3; i ++){
            dibujo.changeColor("yellow");
            dibujo.changeColor(originalColor);
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
}



