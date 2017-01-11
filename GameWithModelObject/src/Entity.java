import java.util.Vector;

import com.jogamp.opengl.GL2;

public class Entity {
	public static Vector<BodyBound> sBlockDestroyables = new Vector<>();
	public static Vector<BodyBound> sBlockUndestroyables = new Vector<>();
	public static Vector<Enemy> sTankEnemyes = new Vector<>();
	public static Boss sBoss;
	public static void initMap(GL2 gl) {
		int size = 3;
		float x = -size * 5;
		int sizeShift = 70;
		float z = 0;
		int typeBlock = 0;
		int quantE = 0;
		for (int i = 0; i < Map.HEIGHT_MAP; i++) {
			for (int j = 0; j < Map.WIDTH_MAP; j++)
			{
				final char tile = Map.TileMap[i].charAt(j);
				if(tile == 'd' || tile == 'u' ||  tile == 'e' || tile == 'b') {
					z = -i * size * 2;
					x = j * size * 2;
					if (tile == 'd') {
						typeBlock = PossitionTextureID.BLOCK_DESTROYABLES.getValue();
						sBlockDestroyables.addElement(new BodyBound(x - sizeShift, z + sizeShift, size * 2, size * 2, typeBlock));
					}
					else if ((tile == 'u')) {
						typeBlock = PossitionTextureID.BLOCK_UNDESTROYABLES.getValue();
						sBlockUndestroyables.addElement(new BodyBound(x - sizeShift, z + sizeShift, size, size, typeBlock));
					}
					else if ((tile == 'b')) {
						sBoss = new Boss(x - sizeShift,z + sizeShift, 25, gl);
					}					
					else if ((tile == 'e')) {
						if(quantE % 2 == 0)
							sTankEnemyes.addElement(new Enemy(x - sizeShift, z + sizeShift, Direction.UP, gl));	
						else 
							sTankEnemyes.addElement(new Enemy(x - sizeShift, z + sizeShift, Direction.RIGHT, gl));	
						quantE++;
					}
				}				
			}
		}
	}
}
