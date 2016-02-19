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
	
	public HashMap<String,Place> getPlaces(){
		return places;
	}
	
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
			popCityTree.insert(temp);
			ratCityTree.insert(temp);
			alphaPlaceTree.insert(temp);
		}
	}
	
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
	
	// josh implementation of findroute
	public ArrayList<Place> getRoute(String from, String to,String type) {
		char c=type.charAt(0);
		Place fromPlace = places.get(from);
		Place toPlace = places.get(to);
		ArrayList<Place> al = new ArrayList<Place>();
		al.add(fromPlace);
		if(c=='d'||c=='D'){
//			System.out.println("calling dRoute");
			return dRoute(fromPlace, toPlace,new PriorityQueue<PathNode>(new PathNode(fromPlace.getDEst(toPlace),0.0,al)));
			
		}else if(c=='t'||c=='T'){
			return tRoute(fromPlace, toPlace,new PriorityQueue<PathNode>(new PathNode(fromPlace.getTEst(toPlace),0.0,al)));
		}
		return null;
	}
	
	public ArrayList<Place> tRoute(Place from,Place to,PriorityQueue<PathNode> pq){
		return null;
	}
	
	public ArrayList<Place> dRoute(Place from,Place to,PriorityQueue<PathNode> pq){
//		System.out.println("size = "+pq.size());
		PathNode temp = pq.poll();
//		System.out.println("size = "+pq.size());
//		System.out.println(from);
//		System.out.println(to);
		Place last = temp.getWIB().get(temp.getWIB().size()-1);
//		System.out.println(temp);
//		System.out.println("size wib = "+temp.getWIB().size());
		if(last.equals(to)){return temp.getWIB();}
////		System.out.println("here i am");
////		return null;
		else{
			ArrayList<Link> nBors = last.getNeighbors();
			for(int i=0;i<nBors.size();i++){
				Link nB = nBors.get(i);
				
				Double disTrav = nB.getDistance();
				ArrayList<Place> wib = temp.getWIB();
				if(nB.getPlace().equals(to)){
					wib.add(nB.getPlace());
					return wib;
				}
				if(!wib.contains(nB.getPlace())){
				wib.add(nB.getPlace());
				pq.offer(new PathNode(disTrav+nB.getPlace().getDEst(to),disTrav+temp.getCostTraveled(),wib));
				}
			}
			dRoute(from,to,pq);
		}
		return null;
	}
	
	public ArrayList<Place> findRoute(Place current,Place destin, String type){
		char c=type.charAt(0);
		if(c=='d'||c=='D'){
			return navigateByDistance(current, destin);
		}else if(c=='t'||c=='T'){
			return navigateByTime(current, destin);
		}
		return null;
	}
	
	public ArrayList<Place> navigateByTime(Place current, Place destin){
		PlaceWithDistance currentPwd= new PlaceWithDistance(current, destin,true);
		FlexPriorityQueue<PlaceWithDistance> list= new FlexPriorityQueue<PlaceWithDistance>();
		while(true){
			
			if(currentPwd.getPlace().getNeighbors().size()==0){
				return null;//null will represent not available route found.
			}
			for(int i=0;i<currentPwd.getPlace().getNeighbors().size();i++){
				PlaceWithDistance pwd = new PlaceWithDistance(currentPwd.getPlace().getNeighbors().get(i).getPlace(), destin,true);
				pwd.getRoute().add(currentPwd.getPlace()); //keep current place in the route information
				pwd.addDistanceTraveled(currentPwd.getPlace().getNeighbors().get(i).getTime());
//				pwd.addDistanceTraveled(distanceToDestin(currentPwd.getPlace(),currentPwd.getPlace().getNeighbors().get(i).getPlace() ));
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
	
	public ArrayList<Place> navigateByDistance(Place current, Place destin){
		PlaceWithDistance currentPwd= new PlaceWithDistance(current, destin,false);
		FlexPriorityQueue<PlaceWithDistance> list= new FlexPriorityQueue<PlaceWithDistance>();
		while(true){
			
			if(currentPwd.getPlace().getNeighbors().size()==0){
				return null;//null will represent not available route found.
			}
			for(int i=0;i<currentPwd.getPlace().getNeighbors().size();i++){
				PlaceWithDistance pwd = new PlaceWithDistance(currentPwd.getPlace().getNeighbors().get(i).getPlace(), destin,false);
				pwd.getRoute().add(currentPwd.getPlace()); //keep current place in the route information
				pwd.addDistanceTraveled(currentPwd.getPlace().getNeighbors().get(i).getDistance());
//				pwd.addDistanceTraveled(distanceToDestin(currentPwd.getPlace(),currentPwd.getPlace().getNeighbors().get(i).getPlace() ));
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
	
	public boolean updateFromFormData(FormData fd) {
		if (fd.isCity()) {
			return remove(fd.getOldCity()) ? insert(fd.getNewCity()) : false;
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
	
	public class PlaceWithDistance{
		private Place place;
		private double distanceTraveled;
		private ArrayList<Place> route;
		private double distanceToDestin;
		private boolean isArrived;
		private boolean byTime;
		public PlaceWithDistance(Place p,Place destin,Boolean isTime) {
			byTime=isTime;
			place=p;
			distanceTraveled=0;
			distanceToDestin=distanceToDestin(p,destin);
			if(byTime) distanceToDestin/=85;
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
