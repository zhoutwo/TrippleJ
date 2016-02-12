package backend;

import java.io.File;
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
	
	public static void main(String[] args) {
		popTree = new FlexRedBlackTree<City>(new PopulationComparator());
		Map map=new Map();
		FlexRedBlackTree<City> populationTree= map.getPopTree();
		FlexRedBlackTree<City> alphabetTree=map.getAlphabetTree();
		FlexRedBlackTree<City> ratingTree=map.getRatingTree();
		try {
			importFromTxtFileToTree(popTree);
//			importFromTxtFileToTree(populationTree);
//			importFromTxtFileToTree(alphabetTree);
//			importFromTxtFileToTree(ratingTree);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// load gui and set visible 
		MapFrame mapFrame = new MapFrame();
		mapFrame.add(new MapComponent());
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setVisible(true);
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

}
