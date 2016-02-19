package backend;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Place {
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
