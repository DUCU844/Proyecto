package silkRoad;


import java.util.ArrayList;
import shapes.Rectangle;

/**
 * Clase que dibuja el camino en forma de espiral cuadrada
 * Conecta visualmente las posiciones de las tiendas
 */
public class SpiralPath {
    private ArrayList<Rectangle> pathSegments;
    private boolean visible;
    private String color;
    
    public SpiralPath() {
        pathSegments = new ArrayList<Rectangle>();
        visible = false;
        color = "black";
    }
    
    /**
     * Dibuja una línea horizontal
     */
    private void drawHorizontalLine(int x1, int x2, int y) {
        Rectangle line = new Rectangle();
        line.changeColor(color);
        
        int width = Math.abs(x2 - x1);
        int startX = Math.min(x1, x2);
        
        line.changeSize(5, width); // Altura 3px, ancho = distancia
        line.moveHorizontal(startX - 70);
        line.moveVertical(y - 15);
        
        if (visible) {
            line.makeVisible();
        }
        
        pathSegments.add(line);
    }
    
    /**
     * Dibuja una línea vertical
     */
    private void drawVerticalLine(int x, int y1, int y2) {
        Rectangle line = new Rectangle();
        line.changeColor(color);
        
        int height = Math.abs(y2 - y1);
        int startY = Math.min(y1, y2);
        
        line.changeSize(height, 5); // Ancho 5px, altura = distancia
        line.moveHorizontal(x - 70);
        line.moveVertical(startY - 15);
        
        if (visible) {
            line.makeVisible();
        }
        
        pathSegments.add(line);
    }
    
    /**
     * Conecta dos puntos con una línea
     */
    public void connectPoints(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            // Línea vertical
            drawVerticalLine(x1, y1, y2);
        } else if (y1 == y2) {
            // Línea horizontal
            drawHorizontalLine(x1, x2, y1);
        } else {
            // Línea diagonal: dibujamos en L (horizontal + vertical)
            drawHorizontalLine(x1, x2, y1);
            drawVerticalLine(x2, y1, y2);
        }
    }
    
    /**
     * Dibuja un camino espiral cuadrado
     * @param centerX centro X de la espiral
     * @param centerY centro Y de la espiral
     * @param numPoints número de puntos en la espiral
     * @param spacing espaciado entre puntos
     */
    public void drawSquareSpiral(int centerX, int centerY, int numPoints, int spacing) {
        clear();
        
        int x = centerX;
        int y = centerY;
        int steps = 1;
        int direction = 0; // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        int pointsDrawn = 0;
        
        while (pointsDrawn < numPoints - 1) {
            for (int i = 0; i < 2; i++) { // Dos direcciones por cada incremento de steps
                for (int j = 0; j < steps && pointsDrawn < numPoints - 1; j++) {
                    int nextX = x;
                    int nextY = y;
                    
                    switch (direction) {
                        case 0: nextX += spacing; break; // Derecha
                        case 1: nextY += spacing; break; // Abajo
                        case 2: nextX -= spacing; break; // Izquierda
                        case 3: nextY -= spacing; break; // Arriba
                    }
                    
                    connectPoints(x, y, nextX, nextY);
                    
                    x = nextX;
                    y = nextY;
                    pointsDrawn++;
                }
                
                direction = (direction + 1) % 4;
            }
            steps++;
        }
    }
    
    /**
     * Dibuja camino conectando puntos específicos en orden
     */
    public void drawPath(int[][] points) {
        clear();
        
        for (int i = 0; i < points.length - 1; i++) {
            connectPoints(points[i][0], points[i][1], 
                         points[i + 1][0], points[i + 1][1]);
        }
    }
    
    /**
     * Cambia el color del camino
     */
    public void changeColor(String newColor) {
        this.color = newColor;
        for (Rectangle segment : pathSegments) {
            segment.changeColor(newColor);
        }
    }
    
    /**
     * Hace visible el camino
     */
    public void makeVisible() {
        visible = true;
        for (Rectangle segment : pathSegments) {
            segment.makeVisible();
        }
    }
    
    /**
     * Hace invisible el camino
     */
    public void makeInvisible() {
        visible = false;
        for (Rectangle segment : pathSegments) {
            segment.makeInvisible();
        }
    }
    
    /**
     * Limpia todos los segmentos del camino
     */
    public void clear() {
        for (Rectangle segment : pathSegments) {
            segment.makeInvisible();
        }
        pathSegments.clear();
    }
}