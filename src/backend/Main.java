package backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import utils.FlexRedBlackTree;

/**
 * this is the main class that the program will run from
 * @author woodsjl
 *
 */
public class Main {
	
	private FlexRedBlackTree<City> popTree;
	
	public static void main(String[] args) {
		try {
			importFromTxtFileToTree();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * the importFromTxtFileToTree() method will load raw data from a text file
	 * and then import all of the data into a TopDownRedBlackTree for sorted 
	 * data storage 
	 * @throws IOException
	 */
	private static void importFromTxtFileToTree() throws IOException{
		// import data from a file and store it in a file type
		File inputFile = new File("src/data/KansasCities.txt");
		// create a scanner to scan through the newly created file
		Scanner inScanner = new Scanner(inputFile);
		ArrayList<String> cityName = new ArrayList<String>();
		ArrayList<String> cityPop = new ArrayList<String>();
		// add data to an array list to prepare to be input into trees 
		while(inScanner.hasNext()){
			cityName.add(inScanner.next());
			cityPop.add(inScanner.next());
		}
		System.out.println(cityName.toString());
		System.out.println(cityPop.toString());
		inScanner.close();
	}

}
