import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class CustomListener implements MouseMotionListener, MouseListener{

    private int startX;
    private int startY;
    private static int deltaX;
    private static int deltaY;
    private int oldX;
    private int oldY;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX() - oldX;
        startY = e.getY() - oldY;
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
        oldX = deltaX;
        oldY = deltaY;
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
}

