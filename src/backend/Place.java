package backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Place {
	private Point mapLoc;
	protected Coordinate location;
	protected String name;
	protected ArrayList<Link> neighbors;
	protected HashMap<Place, Link> linkMap;
	protected double rating;
	protected HashMap<Place,Double> est;
	
	public Place(String pname, Coordinate location, double rating) {
		name = pname;
		this.location = location;
		this.rating = rating;
		this.neighbors = new ArrayList<Link>();
		this.linkMap = new HashMap<Place, Link>();
		est = new HashMap<Place,Double>();
		setMapLocal();
	}
	
	public boolean equals(Object obj) {
		return (name.compareTo(((Place) obj).name) == 0);
	}
	
	public double getDEst(Place p){
		return est.get(p);
	}
	
	public double getTEst(Place p){
		return est.get(p)/85.0;
	}
	
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
	
	private double convertDistance(Coordinate f) {
		double xf = Math.abs(f.getX());
		double yf = Math.abs(f.getY());
		double xt = Math.abs(location.getX());
		double yt = Math.abs(location.getY());
		double xDis = xf-xt;
		double yDis = yf - yt;
		return Math.sqrt((xDis*xDis)+(yDis*yDis));
	}
	
	public Double getEst(Place p){
		return est.get(p);
	}
	
	public void setMapLocal(){
		mapLoc = new Point();
		double y = (location.getY()-40)*(-233);
		double x = (location.getX()+98)*(150);
		mapLoc.setLocation(x,y);
	}
	
	public Point getMapLoc(){
		return mapLoc;
	}

	public Coordinate getLocation() {
		return this.location;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Link> getNeighbors() {
		return this.neighbors;
	}
	
	public double getRating() {
		return this.rating;
	}
	
	public void addNeighbor(Link n){
		neighbors.add(n);
		linkMap.put(n.getPlace(),n);
	}

}
