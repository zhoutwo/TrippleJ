package backend;
import java.util.ArrayList;
import java.util.HashMap;

import utils.*;

public class Map {
	
	private HashMap<String, City> cities;
	private HashMap<String, Place> places;
	private FlexRedBlackTree<City> alpCityTree;
	private FlexRedBlackTree<City> ratCityTree;
	private FlexRedBlackTree<City> popCityTree;
	protected boolean isActive;
	
	public Map() {
		this.cities = new HashMap<String, City>();
		this.alpCityTree = new FlexRedBlackTree<City>(new AlphabetComparator<City>());
		this.ratCityTree = new FlexRedBlackTree<City>(new RatingComparator<City>());
		this.popCityTree = new FlexRedBlackTree<City>(new PopulationComparator());
		isActive = true;
	}
	
	public void setIsAciveFalse(){
		isActive = false;
	}
	
	public ArrayList<City> getAlpCityList() {
		return this.alpCityTree.toArrayList();
	}
	
	public ArrayList<City> getRatCityList() {
		return this.ratCityTree.toArrayList();
	}
	
	public ArrayList<City> getPopCityList() {
		return this.popCityTree.toArrayList();
	}
	
	public ArrayList<Link> getRoute(String from, String to) {
		return null;
	}
	
	public FlexRedBlackTree<City> getPopTree(){
		return this.popCityTree;
	}
	
	public FlexRedBlackTree<City> getAlphabetTree(){
		return this.alpCityTree;
	}
	
	public FlexRedBlackTree<City> getRatingTree(){
		return this.ratCityTree;
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
			cities.put(fd.getNewCity().getName(), fd.getNewCity());
			return success;
		} else {
			boolean success = true;
			City parent = fd.getParentCity();
			
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
