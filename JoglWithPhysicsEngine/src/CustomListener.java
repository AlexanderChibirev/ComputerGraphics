import java.awt.event.MouseListener;

import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;



public class CustomListener implements MouseListener {
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		Renderer.mouseClicked = false;
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		Renderer.mouseClicked = false;
		DYN4JBall ball = new DYN4JBall(new Vector2(Renderer.mouseX, -1 * Renderer.mouseY));
		Renderer.world.addBody(ball.getBall());
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		
	}	
}