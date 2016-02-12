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
		PlaceWithDistance currentPwd= new PlaceWithDistance(current, destin);
		boolean onTheWay=true;
		FlexPriorityQueue<PlaceWithDistance> list= new FlexPriorityQueue<PlaceWithDistance>();
		while(onTheWay){
			
			///Need to update distance traveled as I open new place.
			for(int i=0;i<currentPwd.getPlace().neighbors.size();i++){
				PlaceWithDistance pwd = new PlaceWithDistance(currentPwd.getPlace().getNeighbors().get(i), destin);
				pwd.getRoute().add(currentPwd.getPlace());
				list.add(pwd);
			}
			
			//if not arrived keep the loop going
			if(currentPwd.getPlace()!=destin){
				currentPwd=list.poll();
			}
			//if Arrived make sure it is the lowest cost.
			else {
				if(currentPwd.isArrived){
					return currentPwd.getRoute();
				}
				currentPwd.setTrue();
				currentPwd.updateDistanceTraveled(distanceToDestin(currentPwd.getPlace(), destin));
				
			}
			
			
			
			
		}
		
		
		
		
		
		
		return null;//just for now
		
	}
	
	
	public double distanceToDestin(Place current,Place destin){
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
		protected void updateDistanceTraveled(double distanceToAdd){
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
