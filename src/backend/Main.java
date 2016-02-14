package backend;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import gui.*;
import utils.*;

/**
 * this is the main class that the program will run from
 * @author woodsjl
 *
 */
public class Main {
	
	private static FlexRedBlackTree<City> popTree;
	
	public static void main(String[] args) throws Exception {
		FlexRedBlackTree<City> popTree = new FlexRedBlackTree<City>(new PopulationComparator());
		Map map=new Map();
		FlexRedBlackTree<City> populationTree= map.getPopTree();
		FlexRedBlackTree<City> alphabetTree=map.getAlphabetTree();
		FlexRedBlackTree<City> ratingTree=map.getRatingTree();
		ArrayList<FlexRedBlackTree> treeList = new ArrayList<FlexRedBlackTree>();
		boolean newMap = true;
		if(newMap){
			try {
				importFromTxtFileToTree(popTree); 
			} catch (IOException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}
		else{
			treeList = read("currentState.xml");
		}
		
		// load gui and set visible 
		MapFrame mapFrame = new MapFrame();
		mapFrame.add(new MapComponent());
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setVisible(true);
		while(map.isActive){
			map.setIsAciveFalse();
		}
		treeList.clear();
		treeList.add(populationTree);
		treeList.add(alphabetTree);
		treeList.add(ratingTree);
		write(treeList,"src/data/currentState.xml");
	}
	
	/**
	 * the importFromTxtFileToTree() method will load raw data from a text file
	 * and then import all of the data into a TopDownRedBlackTree for sorted 
	 * data storage 
	 * @throws IOException
	 */
	private static void importFromTxtFileToTree(FlexRedBlackTree tree) throws IOException{
		// import data from a file and store it in a file type
		File inputFile = new File("src/data/KansasCities.txt");
		// create a scanner to scan through the newly created file
		Scanner inScanner = new Scanner(inputFile);
		ArrayList<String> cityName = new ArrayList<String>();
		ArrayList<Integer> cityPop = new ArrayList<Integer>();
		ArrayList<Double> Latitude = new ArrayList<Double>();
		ArrayList<Double> Longitude = new ArrayList<Double>();
		// add data to an array list to prepare to be input into trees 
		while(inScanner.hasNext()){
//			tree.insert(new City(inScanner.next(),inScanner.nextInt()));
			cityName.add(inScanner.next());
			cityPop.add(inScanner.nextInt());
			Latitude.add(inScanner.nextDouble());
			Longitude.add(inScanner.nextDouble());
		}
		System.out.println(cityName.toString());
		System.out.println(cityPop.toString());
		System.out.println(Latitude.toString());
		System.out.println(Longitude.toString());
		inScanner.close();
	}

	private static void write(ArrayList<FlexRedBlackTree> o, String filename) throws Exception{
		 XMLEncoder encoder =
		           new XMLEncoder(
		              new BufferedOutputStream(
		                new FileOutputStream(filename)));
		       	encoder.writeObject(o);
		        encoder.close();
	}
	
	public static ArrayList<FlexRedBlackTree> read(String filename) throws Exception {
        XMLDecoder decoder = 
        	new XMLDecoder(
        			new BufferedInputStream(
        					new FileInputStream(filename)));
        ArrayList<FlexRedBlackTree> l = (ArrayList<FlexRedBlackTree>) decoder.readObject();
        decoder.close();
        return l;
    }
}
