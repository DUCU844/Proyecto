package silkRoad;

import java.util.ArrayList;
import shapes.Rectangle;

/**
 * The SpiralPath class is responsible for drawing a square spiral path.
 * It visually connects the store positions along the Silk Road.
 * 
 * This class uses rectangles to represent the line segments of the path.
 * Each segment can be shown, hidden, or recolored depending on the simulator state.
 * 
 * @author Alejandra Beltrán - Adrian Ducuara
 * @version 2025-2
 */
public class SpiralPath {
    private ArrayList<Rectangle> pathSegments;
    private boolean visible;
    private String color;
    
    /**
     * Creates a new SpiralPath with default color black and hidden by default.
     */
    public SpiralPath() {
        pathSegments = new ArrayList<Rectangle>();
        visible = false;
        color = "black";
    }
    
    /**
     * Draws a horizontal line between two X coordinates at a given Y position.
     * Used to connect stores in the same horizontal level.
     */
    private void drawHorizontalLine(int x1, int x2, int y) {
        Rectangle line = new Rectangle();
        line.changeColor(color);
        
        int width = Math.abs(x2 - x1);
        int startX = Math.min(x1, x2);
        
        line.changeSize(5, width); // Altura 5px, ancho = distancia
        line.moveHorizontal(startX - 70);
        line.moveVertical(y - 15);
        
        if (visible) {
            line.makeVisible();
        }
        
        pathSegments.add(line);
    }
    
    /**
     * Draws a vertical line between two Y coordinates at a given X position.
     * Used to connect stores in the same vertical column.
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
     * Connects two points with a straight line.
     * If points are not aligned, it draws an 'L' shape combining horizontal and vertical lines.
     */
    public void connectPoints(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            // Línea vertical
            drawVerticalLine(x1, y1, y2);
        } else if (y1 == y2) {
            // Línea horizontal
            drawHorizontalLine(x1, x2, y1);
        } else {
            // Línea diagonal simulada como forma de L
            drawHorizontalLine(x1, x2, y1);
            drawVerticalLine(x2, y1, y2);
        }
    }
    
    /**
     * Draws a square spiral path starting from the given center point.
     * The spiral grows in steps in four directions: right, down, left, and up.
     * 
     * @param centerX the X coordinate of the spiral center
     * @param centerY the Y coordinate of the spiral center
     * @param numPoints number of points to connect
     * @param spacing distance between points
     */
    public void drawSquareSpiral(int centerX, int centerY, int numPoints, int spacing) {
        clear();
        
        int x = centerX;
        int y = centerY;
        int steps = 1;
        int direction = 0; // 0=right, 1=down, 2=left, 3=up
        int pointsDrawn = 0;
        
        while (pointsDrawn < numPoints - 1) {
            // Two directions per step increase
            for (int i = 0; i < 2; i++) { 
                for (int j = 0; j < steps && pointsDrawn < numPoints - 1; j++) {
                    int nextX = x;
                    int nextY = y;
                    
                    // Cambia dirección según el valor actual
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
                direction = (direction + 1) % 4; // Rota la dirección
            }
            steps++;
        }
    }
    
    /**
     * Draws a path connecting specific points in order.
     * @param points array of coordinates {x, y}
     */
    public void drawPath(int[][] points) {
        clear();
        
        for (int i = 0; i < points.length - 1; i++) {
            connectPoints(points[i][0], points[i][1], 
                         points[i + 1][0], points[i + 1][1]);
        }
    }
    
    /**
     * Changes the color of all path segments.
     * @param newColor the new color for the path
     */
    public void changeColor(String newColor) {
        this.color = newColor;
        for (Rectangle segment : pathSegments) {
            segment.changeColor(newColor);
        }
    }
    
    /**
     * Makes all the path segments visible.
     */
    public void makeVisible() {
        visible = true;
        for (Rectangle segment : pathSegments) {
            segment.makeVisible();
        }
    }
    
    /**
     * Hides all the path segments.
     */
    public void makeInvisible() {
        visible = false;
        for (Rectangle segment : pathSegments) {
            segment.makeInvisible();
        }
    }
    
    /**
     * Clears all path segments from the screen and list.
     * Used to reset the spiral or path before redrawing.
     */
    public void clear() {
        for (Rectangle segment : pathSegments) {
            segment.makeInvisible();
        }
        pathSegments.clear();
    }
}
