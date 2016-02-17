package backend;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import gui.*;

/**
 * this is the main class that the program will run from
 * @author woodsjl
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		//get current map 
		Map map=new Map();
		// load gui and set visible 
		new MapFrame(map);
		// while loop while the program is in use. once the user is done and 
		// exits the program map.setIsActiveFalse() should be called and data stored 
		// before exiting out to save current state
		while(map.isActive){
			map.setIsAciveFalse();
		}
		write(map,"src/data/currentState.xml");
	}
	
	private static void write(Map o, String filename) throws Exception{
		 XMLEncoder encoder =
		           new XMLEncoder(
		              new BufferedOutputStream(
		                new FileOutputStream(filename)));
		       	encoder.writeObject(o);
		        encoder.close();
	}
	
}
