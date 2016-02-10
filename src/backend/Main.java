package backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import utils.FlexRedBlackTree;

public class Main {
	
	private FlexRedBlackTree popTree;
	
	public static void main(String[] args) {
		try {
			importFromTxtFileToTree();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void importFromTxtFileToTree() throws IOException{
		File inputFile = new File("src/backend/KansasCities.txt");
		Scanner inScanner = new Scanner(inputFile);
		ArrayList<String> cityName = new ArrayList<String>();
		ArrayList<String> cityPop = new ArrayList<String>();
		while(inScanner.hasNext()){
			cityName.add(inScanner.next());
			cityPop.add(inScanner.next());
		}
		System.out.println(cityName.toString());
		System.out.println(cityPop.toString());
	}

}
