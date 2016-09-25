import java.util.Vector;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class DYN4JCannon {
	
	private Vector<GLCannon> gun = null;
	public DYN4JCannon() {
		this.gun =  new Vector<GLCannon>();
		createGun();
	}
	
	private void createCannon(final Vector2 size, final double angle, Vector2 translate)
	{
		GLCannon cannon = new GLCannon();
		cannon.addFixture(new BodyFixture(new Rectangle(size.x, size.y)));
		cannon.setMass(MassType.INFINITE);
		cannon.translate(translate.x, translate.y);
		cannon.rotate(angle);
		gun.add(cannon);
	}
	
	private void createGunTurret(final int precision, final double radius)
	{
		GLCannon gunTurret = new GLCannon();
		gunTurret.addFixture(Geometry.createUnitCirclePolygon(precision, radius));
		gunTurret.setMass(MassType.INFINITE);
		gunTurret.translate(Const.GUN_TURRET_TRANSLATE_X.getValue(),Const.GUN_TURRET_TRANSLATE_Y.getValue());
		gun.add(gunTurret);
	}
	
	private  void createGun() {
		createCannon(new Vector2(2, .6), 0, new Vector2(1, 3));//left floor 0
		createGunTurret(100, 1.0);
	}
	
	public Vector<GLCannon> getCannon() {
		return gun;
	}
}
