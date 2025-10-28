import java.awt.Color;
import java.awt.Graphics;

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

    public Robot(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        initialX = x;
        initialY = y;

        dibujo = new Circle();
        dibujo.changeColor("blue");
        dibujo.moveHorizontal(x);
        dibujo.moveVertical(y);
        dibujo.makeVisible();
    }

    public void moveTo(int newX, int newY) {
        int dx = newX - x;
        int dy = newY - y;
        dibujo.moveHorizontal(dx);
        dibujo.moveVertical(dy);
        x = newX;
        y = newY;
    }

    public void reset() {
        moveTo(initialX, initialY);
    }

    public void delete() {
        dibujo.makeInvisible();
    }
    
    public void blink() {
        dibujo.changeColor("yellow");
        dibujo.changeColor("blue");
        dibujo.changeColor("yellow");
        dibujo.changeColor("blue");
    }
}



