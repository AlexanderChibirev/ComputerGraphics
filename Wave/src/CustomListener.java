import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.vecmath.Vector2f;

class CustomListener implements MouseMotionListener, MouseListener {

    private static int startX;
    private static int startY;
    private static int deltaX;
    private static int deltaY;
    private int oldX;
    private int oldY;
    static boolean isPressed;

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    	
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX() - oldX;
        startY = e.getY() - oldY;
        isPressed = true;
    }    
    
    @Override
    public void mouseReleased(MouseEvent e) {
        oldX = deltaX;
        oldY = deltaY;    
        isPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        deltaX = e.getX() - startX;
        deltaY = e.getY() - startY;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    static int getDeltaX() {

        return deltaX;
    }

    static int getDeltaY() {

        return deltaY;
    }
    
    static int getX() {

        return startX;
    }

    static int getY() {

        return startY;
    }
}
