import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Vector;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;

public class DYN4JCannon {
	public static float rotationAngle = 0f;//убрать 
	public static float rotationAngleOld = 0f;
	public static double mouseX = 0;
	public static double mouseY = 0;
	private Vector<GLCannon> gun = null;
	public DYN4JCannon() {
		this.gun =  new Vector<GLCannon>();
		createGun();
	}
	
	private void createCannon(final Vector2 size, final double angle, Vector2 translate)//прямоугольник пушка
	{
		GLCannon cannon = new GLCannon();
		cannon.addFixture(new BodyFixture(new Rectangle(size.x, size.y)));
		cannon.setMass(MassType.INFINITE);
		cannon.rotate(angle);
		cannon.translate(translate.x, translate.y);
		gun.add(cannon);
	}
	
	private void createGunTurret(final int precision, final double radius)//круг 
	{
		GLCannon gunTurret = new GLCannon();
		gunTurret.addFixture(Geometry.createUnitCirclePolygon(precision, radius));
		gunTurret.setMass(MassType.INFINITE);
		gunTurret.translate(Const.GUN_TURRET_TRANSLATE_X.getValue(),Const.GUN_TURRET_TRANSLATE_Y.getValue());
		gun.add(gunTurret);
	}
	
	private  void createGun() {
		createCannon(new Vector2(3.45, .6), 1.57, new Vector2(0, 2));//left floor 0 1.55
		createGunTurret(100, 1.1);
	}
	
	public Vector<GLCannon> getCannon() {
		return gun;
	}
	public float getCelsiusAngle() {
		return ((float) (Math.atan2(mouseX,mouseY) *  180 / Math.PI)) - 90;
	}
	
	public float getRadianAngle() {
		return (float) Math.atan2(-mouseX,-mouseY);
	}
	
	public void updatePhysicsCannon() {
		Point location = MouseInfo.getPointerInfo().getLocation();
		mouseX = location.getX() - 959;
		mouseY = location.getY() - 603;
		rotationAngle = getCelsiusAngle();//убрать 
		//System.out.println(getRadianAngle());
		Renderer.world.getBody((int) Const.NUMBER_CANNON.getValue()).rotate(-rotationAngleOld);
		rotationAngleOld =  getRadianAngle();
		Renderer.world.getBody((int) Const.NUMBER_CANNON.getValue()).rotate(getRadianAngle());
	}
}
