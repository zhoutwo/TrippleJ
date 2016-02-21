package backend;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

import gui.*;

/**
 * 
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		Map map;
		//prompt the user to see if new user
		String newUser = JOptionPane.showInputDialog("Are you a new user?");;
		while(!(newUser.equals("y")||newUser.equals("yes")||newUser.equals("n")||newUser.equals("no"))){
			newUser = JOptionPane.showInputDialog("Please use: yes / y / no / n \nAre you a new user?");
		}
		if(newUser.equals("y")||newUser.equals("yes")){
			// get new map
			map=new Map();
		}
		else {
			// get last map used
			try {
				map = read("src/data/currentState.xml");
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Sorry, previous data couldn't be found. We will load new data for you instead.", "File Not Found", JOptionPane.ERROR_MESSAGE);
				map=new Map();
			}
		}
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
	
	/**
	 * this method will load the last state of the map for continuing usage
	 * 
	 * @param filename this is the name of the file to be loaded
	 * @return this method return a map of the last state the program was left in
	 * @throws FileNotFoundException 
	 */
	public static Map read(String filename) throws FileNotFoundException {
        XMLDecoder decoder = 
        	new XMLDecoder(
        			new BufferedInputStream(
        					new FileInputStream(filename)));
        Map l = (Map) decoder.readObject();
        decoder.close();
        return l;
    }
	
}
