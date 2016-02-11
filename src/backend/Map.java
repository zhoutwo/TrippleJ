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
		PriorityQueue<Place> list= new PriorityQueue<Place>();
		PlaceWithDistance pwd=new PlaceWithDistance(current, destin);
		Place first=current.neighbors.get(0);
		for(int i=1;i<current.neighbors.size();i++){
			if(distanceToDestination(first, destin)<distanceToDestination(current.neighbors.get(i), destin)){
				list.add(first); // it does not like it becuase links is an arraylist of places, not cities.
				first=current.neighbors.get(i);
			}else{
				list.add(current.neighbors.get(i));
			}
		}
		
		
		
		
		return null;
		
	}
	
	
	public double distanceToDestination(Place current,Place destin){
		double x= current.getLocation().getX()-destin.getLocation().getX();
		double y= current.getLocation().getY()-destin.getLocation().getY();
		x=x*x;
		y=y*y;
		return Math.sqrt(x+y);
	}
	
	public boolean addEntry(FormData fd) {
		return false;
	}
	
	public boolean editEntry(FormData fd) {
		return false;
	}
	private class PlaceWithDistance{
		private Place place;
		private double distanceTraveled;
		private double distanceToDestin;
		public PlaceWithDistance(Place p,Place destin) {
			place=p;
			distanceTraveled=0;
			distanceToDestin=distanceToDestination(p,destin);
		}
		
	}
	
}
