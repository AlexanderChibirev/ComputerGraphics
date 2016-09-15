package ska;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;


public class Main {
	private static final int WINDOW_HIGHT = 800;
	private static final int WINDOW_WIGHT = 800;
	private static GraphicsEnvironment graphicsEnviorment;
	
	public static void main(String[] args) throws FileNotFoundException{ 
		final GLProfile profile = GLProfile.get(GLProfile.GL2);	/*GLProfile класс статической инициализации синглетон запрашивает		 * наличие всех профилей OpenGL и конкретизирует одноплодна€ объекты GLProfile		 *  дл€ каждого доступного профил€. задаем версию GL2*/		
		GLCapabilities capabilities = new GLCapabilities(profile);//«адает набор возможностей OpenGL.  
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(new InternalCombustionEngine());
    	glcanvas.setSize( WINDOW_WIGHT, WINDOW_HIGHT );
        JFrame frame = new JFrame( "TwoProject" );
       
        frame.getContentPane().add(glcanvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
        parseJson();
        JSONObject dataJsonObj = new JSONObject();  
        frame.setSize( frame.getContentPane().getPreferredSize() );
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowX = Math.max(0,(screenSize.width - frame.getWidth()) / 2);
        int windowY = Math.max(0,(screenSize.height - frame.getHeight()) / 2);
        
        frame.setLocation(windowX, windowY);
        frame.setVisible( true );
	}
	private static void parseJson() {
		JSONParser parser = new JSONParser(); 
		/*	try {  
  
			Object obj = parser.parse(new FileReader("input.json"));  
			JSONObject jsonObject = (JSONObject) obj;   
			String nameOfCountry = (String) jsonObject.get("Name");  
			System.out.println("Name Of Country: "+nameOfCountry);   
			long population = (Long) jsonObject.get("Population");  
			System.out.println("Population: "+population);  
			System.out.println("States are :");  
			JSONArray listOfStates = (JSONArray) jsonObject.get("States");  
			Iterator<String> iterator = listOfStates.iterator();  
			while (iterator.hasNext()) {  
				System.out.println(iterator.next());  
			}  
		} catch (FileNotFoundException e) {  
				e.printStackTrace();  
		} catch (IOException e) {  
				e.printStackTrace();  
		} catch (ParseException e) {  
				e.printStackTrace();  
		}  */
	}
}
