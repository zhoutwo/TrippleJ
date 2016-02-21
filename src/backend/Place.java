package backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Place represents place either by City class or POI class
 * centerPoint - center point of the place in MapDisplayPanel
 * location - location of the place
 * name - name of the place
 * neighbors - ArrayList of Links that connects to neighboring places
 * linkMap - A HashMap to retrieve the link to a specific Place
 * rating - rating of the place out of 5.0
 * est - Calculations used by the cost function
 */
public abstract class Place {
	private Point centerPoint;
	protected Coordinate location;
	protected String name;
	protected ArrayList<Link> neighbors;
	protected HashMap<Place, Link> linkMap;
	protected double rating;
	protected HashMap<Place,Double> est;
	/**
	 * 
	 * @param name name of the place in String
	 * @param location location of the place in Coordinate class
	 * @param rating rating of place in double out of 5.0
	 */
	public Place(String name, Coordinate location, double rating) {
		this.name = name;
		this.location = location;
		this.rating = rating;
		this.neighbors = new ArrayList<Link>();
		this.linkMap = new HashMap<Place, Link>();
		est = new HashMap<Place,Double>();
		setMapLocal();
	}
	/**
	 * this methods helps to compare different type of places
	 */
	public boolean equals(Object obj) {
		return (name.compareTo(((Place) obj).name) == 0);
	}
	
	/**
	 * For use by Map when calculating routes
	 * 
	 */
	public double getDEst(Place p){
		return est.get(p);
	}
	
	/**
	 * For use by Map when calculating routes
	 * 
	 */
	public double getTEst(Place p){
		return est.get(p)/85.0;
	}

	/**
	 * For use by Map when calculating routes
	 * 
	 */
	public void fillEstTable(ArrayList<Place> al){
		Place temp;
		for(int i=0;i<al.size();i++){
			temp=al.get(i);
			if(linkMap.containsKey(temp)){
				est.put(temp, linkMap.get(temp).getDistance());
			}
			else{
				est.put(temp, convertDistance(temp.getLocation()));
			}
		}
	}
	/**
	 * calculate the straight line distance from the place taken as parameter to current place
	 * @param from
	 * @return
	 */
	private double convertDistance(Coordinate from) {
		double xFrom = Math.abs(from.getX());
		double yFrom = Math.abs(from.getY());
		double xTo = Math.abs(location.getX());
		double yTo = Math.abs(location.getY());
		double xDistance = xFrom-xTo;
		double yDistance = yFrom - yTo;
		return Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
	}

	/**
	 * For use by Map when calculating routes
	 * 
	 */
	public Double getEst(Place p){
		return est.get(p);
	}
	
	/**
	 * initialize the center of its location drawn in the MapDisplayPanel
	 */
	public void setMapLocal(){
		centerPoint = new Point();
		double y = (location.getY()-40)*(-233);
		double x = (location.getX()+98)*(150);
		centerPoint.setLocation(x,y);
	}
	
	/**
	 * return the center point of its location drawn in the MapDisplayPanel
	 * @return
	 */
	public Point getMapLoc(){
		return centerPoint;
	}
	
	/**
	 * return the GPS coordinate of the place
	 * @return the coordinates of the place
	 */
	public Coordinate getLocation() {
		return this.location;
	}
	
	/**
	 * return the name of the place
	 * @return the name of the place
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * return the neighboring links of the place
	 * @return the ArrarList of links
	 */
	public ArrayList<Link> getNeighbors() {
		return this.neighbors;
	}
	
	/**
	 * return the rating of the place
	 * @return the rating of the place
	 */
	public double getRating() {
		return this.rating;
	}
	
	/**
	 * adds a neighboring link to the place taken from a parameter 
	 * @param n
	 */
	public void addNeighbor(Link n){
		neighbors.add(n);
		linkMap.put(n.getPlace(),n);
	}

}
