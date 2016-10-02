import java.awt.event.MouseListener;
import org.dyn4j.geometry.Vector2;



public class CustomListener  implements MouseListener {
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		DYN4JBall ball = new DYN4JBall(new Vector2(DYN4JCannon.mouseX, -1 * DYN4JCannon.mouseY));
		Renderer.world.addBody(ball.getBall());
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		
	}	
}