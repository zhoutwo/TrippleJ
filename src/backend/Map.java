package backend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import utils.*;

public class Map {
	
	private HashMap<String, City> cities;
	private HashMap<String, Place> plcaes;
	private FlexRedBlackTree<City> alpCityTree;
	private FlexRedBlackTree<City> ratCityTree;
	private FlexRedBlackTree<City> popCityTree;
	
	public Map() {
		this.cities = new HashMap<String, City>();
		this.alpCityTree = new FlexRedBlackTree<City>(new AlphabetComparator<City>());
		this.ratCityTree = new FlexRedBlackTree<City>(new RatingComparator<City>());
		this.popCityTree = new FlexRedBlackTree<City>(new PopulationComparator());
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
		ArrayList<Place> route=new ArrayList<Place>();
		boolean onTheWay=true;
		FlexPriorityQueue<PlaceWithDistance> list= new FlexPriorityQueue<PlaceWithDistance>();
		while(onTheWay){
			for(int i=0;i<current.neighbors.size();i++){
				PlaceWithDistance pwd = new PlaceWithDistance(current.getNeighbors().get(i), destin);
				list.add(pwd);
			}
			
			
		}
		
		
//		for(int i=1;i<current.neighbors.size();i++){
//			if(distanceToDestination(first, destin)<distanceToDestination(current.neighbors.get(i), destin)){
//				list.add(first);
//				first=current.neighbors.get(i);
//			}else{
//				list.add(current.neighbors.get(i));
//			}
//		}
		
		
		
		
		return route;
		
	}
	
	/**
	 * Calculates the distance (straight line connecting one place to another, not the route between.
	 * @param current
	 * @param destin
	 * @return
	 */
	public static double distanceToDestination(Place from, Place to){
		double dx= from.getLocation().getX()-to.getLocation().getX();
		double dy= from.getLocation().getY()-to.getLocation().getY();
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public boolean addEntry(FormData fd) {
		return false;
	}
	
	public boolean editEntry(FormData fd) {
		return false;
	}
	public class PlaceWithDistance{
		private Place place;
		private double distanceTraveled;
		private double distanceToDestin;
		public PlaceWithDistance(Place p,Place destin) {
			place=p;
			distanceTraveled=0;
			distanceToDestin=distanceToDestination(p,destin);
			
		}
		public double getDistanceTraveled(){
			return distanceTraveled;
		}
		protected double getDistanceToDestin(){
			return distanceToDestin;
		}
		protected Place getPlace(){
			return place;
		}
		public double getCost(){
			return distanceToDestin+distanceTraveled;
		}
		
		
	}
	
}
