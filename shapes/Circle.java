package shapes;

import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2.0 - Refactored with inheritance
 */
public class Circle extends Shape {

    public static final double PI = 3.1416;
    
    private int diameter;

    public Circle() {
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }
  
    @Override
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    @Override
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    @Override
    protected void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }

    @Override
    protected void erase() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    @Override
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    @Override
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    @Override
    public void slowMoveHorizontal(int distance) {
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    @Override
    public void slowMoveVertical(int distance) {
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    @Override
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }
}