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
	
	public Place(String pname, Coordinate location, double rating) {
		name = pname;
		this.location = location;
		this.rating = rating;
		this.neighbors = new ArrayList<Link>();
		this.linkMap = new HashMap<Place, Link>();
		setMapLocal();
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
	}

}
