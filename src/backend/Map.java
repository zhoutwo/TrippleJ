package backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import utils.*;

public class Map {
	
	private HashMap<String, City> cities;
	private HashMap<String, Place> places;
	private FlexRedBlackTree<City> alpCityTree;
	private FlexRedBlackTree<City> ratCityTree;
	private FlexRedBlackTree<City> popCityTree;
	private FlexRedBlackTree<Place> alphaPlaceTree;
	private ArrayList<Place> route;
	private ArrayList<City> alpCityList;
	private ArrayList<City> ratCityList;
	private ArrayList<City> popCityList;
	
	protected boolean isActive;
	
	public Map() {
		this.places = new HashMap<String, Place>();
		this.cities = new HashMap<String, City>();
		this.alpCityTree = new FlexRedBlackTree<City>(new AlphabetComparator<City>());
		this.ratCityTree = new FlexRedBlackTree<City>(new RatingComparator<City>());
		this.popCityTree = new FlexRedBlackTree<City>(new PopulationComparator());
		alphaPlaceTree = new FlexRedBlackTree<Place>(new AlphabetComparator<Place>());
		route = new ArrayList<Place>();
		isActive = true;
		// try catch block surrounds the import process of raw data into system
		try {
			importFromTxtFileToAlpCityTree(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		// call fillTrees() to fill other trees with data
		fillTrees();
		try {
			importFromTxtFileToalpPOIList(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		try {
			importFromTxtFileLinks(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		fillEstimateTables();
	}
	
	public void fillEstimateTables(){
		ArrayList<Place> allP = alphaPlaceTree.toArrayList();
		Iterator<Place> i = alphaPlaceTree.iterator();
		while(i.hasNext()){
			i.next().fillEstTable(allP);
		}
	}
	
	/**
	 * getPlaces() returns a HashMap<String name,Place place> of places 
	 * @return
	 */
	public HashMap<String,Place> getPlaces(){
		return places;
	}
	
	/**
	 * putSpaceInName() takes a name of a place and looks for a capital letter in the middle of the string
	 * and then inserts a space before to make the names legible to humans. the scanner recongnizes a space as 
	 * a different string so the import test file has two word names combined with no spaces
	 * @param pname
	 * @return
	 */
	private String putSpaceInName(String pname) {
		String printName = "";
		for(int i=0;i<pname.length();i++){
			if(Character.isUpperCase(pname.charAt(i))){
				printName = printName + " "+pname.charAt(i);
			}
			else printName=printName+pname.charAt(i);
		}
		return printName;
	}
	
	/**
	 * this method imports all of the links(roads) of the given map from a test file and stores them in the 
	 * appropriate cities 
	 * @throws IOException
	 */
	private void importFromTxtFileLinks() throws IOException{
		File inputFile = new File("src/data/Links.txt");
		// create a scanner to scan through the newly created file
		Scanner inScanner = new Scanner(inputFile);
		// iterate through scanner and load all data into a tree until scanner is empty 
		City tempFrom;
		City tempTo;
		String name;
		while(inScanner.hasNext()){
			name = inScanner.next();
			tempFrom = cities.get(putSpaceInName(inScanner.next()));
			tempTo = cities.get(putSpaceInName(inScanner.next()));
			tempFrom.addNeighbor(new Link(putSpaceInName(name),tempFrom,tempTo));
			tempTo.addNeighbor(new Link(putSpaceInName(name),tempTo,tempFrom));
		}
		// close scanner
		inScanner.close();
	}
	
	/**
	 * fillTrees() method imports all the city data from population tree to the other 
	 * remaining trees for easily accesable data 
	 */
	private void fillTrees(){
		Iterator<City> i = popCityTree.iterator();
		City temp;
		while(i.hasNext()){
			temp = i.next();
			cities.put(temp.name, temp);
			places.put(temp.name, temp);
			alpCityTree.insert(temp);
			popCityTree.insert(temp);
			ratCityTree.insert(temp);
			alphaPlaceTree.insert(temp);
		}
	}
	
	/**
	 * this method imports all of the points of intrest(POI) of the given map from a text file and stores them
	 * in the appropriate cities
	 * @throws IOException
	 */
	private void importFromTxtFileToalpPOIList() throws IOException{
		File inputFile = new File("src/data/POIS.txt");
		// create a scanner to scan through the newly created file
		Scanner inScanner = new Scanner(inputFile);
		// iterate through scanner and load all data into a tree until scanner is empty 
		City temp;
		Coordinate c;
		double cost;
		double rating;
		String pName;
		String type;
		while(inScanner.hasNext()){
			temp = cities.get(putSpaceInName(inScanner.next()));
			pName = inScanner.next();
			cost = inScanner.nextDouble();
			rating = inScanner.nextDouble();
			type = inScanner.next();
			c = temp.getLocation();
			temp.addPOI(new POI(putSpaceInName(pName),c,type,rating,cost));
		}
		// close scanner
		inScanner.close();
	}
	
	/**
	 * the importFromTxtFileToTree() method will load raw data from a text file
	 * and then import all of the data into a TopDownRedBlackTree for sorted 
	 * data storage 
	 * @throws IOException
	 */
	private void importFromTxtFileToAlpCityTree() throws IOException{
		// import data from a file and store it in a file type
		File inputFile = new File("src/data/KansasCities.txt");
		// create a scanner to scan through the newly created file
		Scanner inScanner = new Scanner(inputFile);
		// iterate through scanner and load all data into a tree until scanner is empty 
		while(inScanner.hasNext()){
			popCityTree.insert(new City(putSpaceInName(inScanner.next()),inScanner.nextInt(),new Coordinate(inScanner.nextDouble(),inScanner.nextDouble()),0));
		}
		// close scanner
		inScanner.close();
	}
	
	/**
	 * sets the isActivefield to false which means that the map is no longer active
	 */
	public void setIsAciveFalse(){
		isActive = false;
	}
	
	public FlexRedBlackTree<City> getPopTree(){
		return popCityTree;
	}
	
	public ArrayList<City> getAlpCityList() {
//		System.out.println(alpCityTree);
		if (this.alpCityTree.listNeedsUpdate()) {
			this.alpCityList = this.alpCityTree.toArrayList();
		}
		return this.alpCityList;
	}
	
	public ArrayList<City> getRatCityList() {
		if (this.ratCityTree.listNeedsUpdate()) {
			this.ratCityList = this.ratCityTree.toArrayList();
		}
		return this.ratCityList;
	}
	
	public ArrayList<City> getPopCityList() {
		if (this.popCityTree.listNeedsUpdate()) {
			this.popCityList = this.popCityTree.toArrayList();
		}
		return this.popCityList;
	}
	
	/**
	 * returnRoute method return the variable route 
	 * @return
	 */
	public ArrayList<Place> returnRoute(){
		return route;
	}
	
	// josh implementation of findroute
	public void getRoute(String from, String to,String type) {
		// clear old route that might be present
		route.clear();
		// if from = to then we are looking for ourselves and just add from to route and return
		if(from.equals(to)){
			route.add(places.get(from));
			return;
		}
		//get character representation of which route to use time or distance 
		char c=type.charAt(0);
		// use string to get the Place from 
		Place fromPlace = places.get(from);
//		// add 
//		route.add(fromPlace);
		// use string to get the Place to 
		Place toPlace = places.get(to);
//		ArrayList<Place> al = new ArrayList<Place>();
//		al.add(fromPlace);
		// create a PriorityQueue to use in finding least cost path
		PriorityQueue<PathNode> pq = new PriorityQueue<PathNode>();
//		// add our current position to the priorityQueue
//		pq.offer(new PathNode())
		ArrayList<Place> wib = new ArrayList<Place>();
		wib.add(fromPlace);
		// determine which route determining function to call
		if(c=='d'||c=='D'){
			dRoute(fromPlace, toPlace,0.0,pq,wib);
		}else if(c=='t'||c=='T'){
			tRoute(fromPlace, toPlace,0.0,pq,wib);
		}
//		route.add(toPlace);
	}
	
	public void tRoute(Place current,Place to,Double traveledCost,PriorityQueue<PathNode> pq,ArrayList<Place> pwib){
		ArrayList<Place> wib = pwib;
		ArrayList<Link> neighbors = current.getNeighbors();
		ArrayList<Place> newWIB = new ArrayList<Place>();
		for(int i=0;i<neighbors.size();i++){
			Place neighborPlace = neighbors.get(i).getPlace(); 
			Double travelCost = neighbors.get(i).getTime();
			newWIB = new ArrayList<Place>();
			for(int k=0;k<wib.size();k++){
				newWIB.add(wib.get(k));
			}
			if(newWIB.add(neighborPlace)){
				pq.offer(new PathNode(neighborPlace,to,traveledCost+travelCost,neighborPlace.getTEst(to),newWIB));
			}
		}
		PathNode temp2 = pq.poll();
		Place temp3 = temp2.getCurrentPlace();
		if(temp3.equals(to)){
			route= temp2.getWIB();
		}
		else{
			dRoute(temp3,to,temp2.getCostTraveled(),pq,temp2.getWIB());
		}
	}
	
	/**
	 * dRoute() method determines the shortest route using distance as the cost factor and it will populate 
	 * the ArrayList<Place> with all of the places visited along the way
	 * @param from
	 * @param to
	 * @param pq
	 */
	public void dRoute(Place current,Place to,Double traveledCost,PriorityQueue<PathNode> pq,ArrayList<Place> pwib){
		ArrayList<Place> wib = pwib;
		ArrayList<Link> neighbors = current.getNeighbors();
		ArrayList<Place> newWIB = new ArrayList<Place>();
		for(int i=0;i<neighbors.size();i++){
			Place neighborPlace = neighbors.get(i).getPlace(); 
			Double travelCost = neighbors.get(i).getDistance();
			newWIB = new ArrayList<Place>();
			for(int k=0;k<wib.size();k++){
				newWIB.add(wib.get(k));
			}
			if(newWIB.add(neighborPlace)){
				pq.offer(new PathNode(neighborPlace,to,traveledCost+travelCost,neighborPlace.getDEst(to),newWIB));
			}
		}
		PathNode temp2 = pq.poll();
		Place temp3 = temp2.getCurrentPlace();
		if(temp3.equals(to)){
			route= temp2.getWIB();
		}
		else{
			dRoute(temp3,to,temp2.getCostTraveled(),pq,temp2.getWIB());
		}
	}
	
	public boolean updateFromFormData(FormData fd) {
		if (fd.isCity()) {
			return remove(fd.getOldCity());
//			return remove(fd.getOldCity()) ? insert(fd.getNewCity()) : false;
		} else {
			boolean success = true;
			City parent = fd.getParentCity();
			return remove(fd.getOldPOI(), parent) ? insert(fd.getNewPOI(), parent) : false;
		}
	}
	
	public boolean remove(City c) {
		boolean success = true;
		if (!alpCityTree.remove(c)) success = false;
		if (!ratCityTree.remove(c)) success = false;
		if (!popCityTree.remove(c)) success = false;
		places.remove(c.getName());
		cities.remove(c.getName());
		return success;
	}
	
	public boolean remove(POI p, City parent) {
		if (parent.removePOI(p)) {
			places.remove(p.getName());
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean insert(City c) {
		boolean success = true;
		if (!alpCityTree.insert(c)) success = false;
		if (!ratCityTree.insert(c)) success = false;
		if (!popCityTree.insert(c)) success = false;
		places.put(c.getName(), c);
		cities.put(c.getName(), c);
		return success;
	}
	
	public boolean insert(POI p, City parent) {
		if (parent.addPOI(p)) {
			places.put(p.getName(), p);
			return true;
		} else {
			return false;
		}
	}
}
