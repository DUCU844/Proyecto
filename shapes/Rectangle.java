package shapes;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 2.0 - Refactored with inheritance
 */
public class Rectangle extends Shape {

    public static int EDGES = 4;
    
    private int height;
    private int width;

    public Rectangle() {
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
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

    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    @Override
    protected void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
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
}
