package backend;

public class FormData {
	
	private final boolean isCity;
	private final City parentCity;
	private final City newCity;
	private final POI newPOI;
	
	public FormData(String name, int x, int y, double rating, int population) {
		this.isCity = true;
		this.parentCity = null;
		this.newCity = new City(name, new Coordinate(x, y), rating, population);
		this.newPOI = null;
	}
	
	public FormData(String name, int x, int y, City parentCity, String type, double rating, double cost) {
		this.isCity = false;
		this.parentCity = parentCity;
		this.newCity = null;
		this.newPOI = new POI(name, new Coordinate(x,y), type, rating, cost);
	}
	
	public boolean isCity() {
		return this.isCity;
	}
	
	public City getParentCity() {
		return this.parentCity;
	}
	
	public City getNewCity() {
		return this.newCity;
	}
	
	public POI getNewPOI() {
		return this.newPOI;
	}
}
