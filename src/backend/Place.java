package backend;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Place {
	protected Coordinate location;
	protected String name;
	protected ArrayList<Place> links;
	protected HashMap<Place, Link> linkMap;
	protected int rating;
	
	public Place(String name, Coordinate location, int rating) {
		this.name = name;
		this.location = location;
		this.rating = rating;
	}
	
	public Coordinate getLocation() {
		return this.location;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Place> getLinks() {
		return this.links;
	}
	
	public int getRating() {
		return this.rating;
	}
}
