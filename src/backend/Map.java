package backend;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Spliterator;

import utils.*;

public class Map {
	
	private HashMap<String, Place> places;
	private FlexRedBlackTree<City> alpCityTree;
	private FlexRedBlackTree<City> ratCityTree;
	private FlexRedBlackTree<City> popCityTree;
	
	private ArrayList<City> alpCityList;
	private ArrayList<City> ratCityList;
	private ArrayList<City> popCityList;
	
	protected boolean isActive;
	
	public Map() {
		this.places = new HashMap<String, Place>();
		this.alpCityTree = new FlexRedBlackTree<City>(new AlphabetComparator<City>());
		this.ratCityTree = new FlexRedBlackTree<City>(new RatingComparator<City>());
		this.popCityTree = new FlexRedBlackTree<City>(new PopulationComparator());
		isActive = true;
		// try catch block surrounds the import process of raw data into system
		try {
			importFromTxtFileToPopCityTree(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		// call fillTrees() to fill other trees with data
		fillTrees();
//		ArrayList<City> al = alpCityTree.toArrayList();
//		for(int i=0;i<al.size();i++){
//			System.out.println(al.get(i).toString());
//		}
		
		
	}
	
	
	/**
	 * fillTrees() method imports all the city data from population tree to the other 
	 * remaining trees for easily accesable data and fills the hashmap places
	 */
	private void fillTrees(){
		Iterator<City> i = popCityTree.iterator();
		City temp;
		while(i.hasNext()){
			temp = i.next();
			places.put(temp.getName(), temp);
			alpCityTree.insert(temp);
			ratCityTree.insert(temp);
		}
	}
	
	/**
	 * the importFromTxtFileToTree() method will load raw data from a text file
	 * and then import all of the data into a TopDownRedBlackTree for sorted 
	 * data storage 
	 * @throws IOException
	 */
	private void importFromTxtFileToPopCityTree() throws IOException{
		// import data from a file and store it in a file type
		File inputFile = new File("src/data/KansasCities.txt");
		// create a scanner to scan through the newly created file
		Scanner inScanner = new Scanner(inputFile);
		// iterate through scanner and load all data into a tree until scanner is empty 
		while(inScanner.hasNext()){
			popCityTree.insert(new City(inScanner.next(),inScanner.nextInt(),new Coordinate(inScanner.nextDouble(),inScanner.nextDouble()),0));
		}
		// close scanner
		inScanner.close();
	}
	
	public void setIsAciveFalse(){
		isActive = false;
	}
	
	public FlexRedBlackTree<City> getPopTree(){
		return popCityTree;
	}
	
	public ArrayList<City> getAlpCityList() {
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
	
	public ArrayList<Place> getRoute(String from, String to) {
		return null;
	}
	
	public ArrayList<Place> navigateTo(Place current, Place destin){
		PlaceWithDistance currentPwd= new PlaceWithDistance(current, destin);
		FlexPriorityQueue<PlaceWithDistance> list= new FlexPriorityQueue<PlaceWithDistance>();
		while(true){
			
			if(currentPwd.getPlace().neighbors.size()==0){
				return null;//null will represent not available route found.
			}
			for(int i=0;i<currentPwd.getPlace().neighbors.size();i++){
				PlaceWithDistance pwd = new PlaceWithDistance(currentPwd.getPlace().getNeighbors().get(i), destin);
				pwd.getRoute().add(currentPwd.getPlace()); //keep current place in the route information
				pwd.addDistanceTraveled(distanceToDestin(currentPwd.getPlace(),currentPwd.getPlace().getNeighbors().get(i) ));
				list.add(pwd);
			}
			
			//if not arrived keep the loop going
			if(list.size()==0) return null; //When there is no "OPEN" place that you can visit through, null will represent not available route found.
			if(!list.peek().getPlace().equals(destin)){
				currentPwd=list.poll();//If you poll, that place will be removed from the list, and will be considered as closed(but its neighbors will still be open)
			}
			//if Arrived make sure it is the lowest cost.
			else {
				return list.peek().getRoute();
//				if(currentPwd.isArrived){
//					return currentPwd.getRoute();
//				}
//				currentPwd.setTrue();
//				currentPwd.addDistanceTraveled(distanceToDestin(currentPwd.getPlace(), destin));
			}
		}
	}
	
	
	public static double distanceToDestin(Place current, Place destin){
		double x= current.getLocation().getX()-destin.getLocation().getX();
		double y= current.getLocation().getY()-destin.getLocation().getY();
		return Math.sqrt(x*x+y*y);
	}
	
	public boolean upsert(FormData fd) {
		if (!fd.isNew()) {
			
		}
		if (fd.isCity()) {
			boolean success = true;
			if (!alpCityTree.insert(fd.getNewCity())) success = false;
			if (!ratCityTree.insert(fd.getNewCity())) success = false;
			if (!popCityTree.insert(fd.getNewCity())) success = false;
			places.put(fd.getNewCity().getName(), fd.getNewCity());
			return success;
		} else {
			boolean success = true;
			City parent = fd.getParentCity();
			// TODO
		}
		return false;
	}
	
	
	
	protected class PlaceWithDistance{
		private Place place;
		private double distanceTraveled;
		private ArrayList<Place> route;
		private double distanceToDestin;
		private boolean isArrived;
		public PlaceWithDistance(Place p,Place destin) {
			place=p;
			distanceTraveled=0;
			distanceToDestin=distanceToDestin(p,destin);
			route=new ArrayList<Place>();
			isArrived=false;
			
		}
		protected double getDistanceTraveled(){
			return distanceTraveled;
		}
		protected void addDistanceTraveled(double distanceToAdd){
			distanceToDestin+=distanceToAdd;
		}
		protected double getDistanceToDestin(){
			return distanceToDestin;
		}
		protected Place getPlace(){
			return place;
		}
		protected double getCost(){
			return distanceToDestin+distanceTraveled;
		}
		protected ArrayList<Place> getRoute(){
			return route;
		}
		protected boolean getIsArrived(){
			return isArrived;
		}
		protected void setTrue(){
			isArrived=true;
		}
		
		
	}
	
}
