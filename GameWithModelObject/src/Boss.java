import com.jogamp.opengl.GL2;

import OBJLoader.OBJModel;
public class Boss extends BodyBound{

	private boolean isDead = false;
	private float life = 100;
	private OBJModel boss;
	public Boss(float x, float y, int size, GL2 gl) {
		super(x, y, size - 20, size - 20, 0);
		boss = new OBJModel("penguin", size, gl, true);
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void update(GL2 gl) {
		float shiftY = 5.8f;
		gl.glTranslated(this.x, shiftY, this.y);
			boss.draw(gl);
		gl.glTranslated(-this.x, -shiftY,  -this.y);
		
		for(Bullet body :Bullet.sBulletsArray) {
			if(body.getBounds().intersects(this.getBounds())) {
				life -= 0.5;
				break;
				
			}
		}		
		if(life < 0){
			isDead = true;
		}
	}
}
