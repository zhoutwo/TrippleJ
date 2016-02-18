package backend;

public class FormData {
	
	private final boolean isCity;
	private final City oldCity;
	private final POI oldPOI;
	private final City parentCity;
	private final City newCity;
	private final POI newPOI;
	
	public FormData(City oldCity, String newName, int x, int y, double rating, int population) {
		this.oldCity = oldCity;
		this.oldPOI = null;
		this.isCity = true;
		this.parentCity = null;
		this.newCity = new City(newName, population, new Coordinate(x, y), rating);
		this.newPOI = null;
	}
	
	public FormData(POI oldPOI, String newName, int x, int y, City parentCity, String type, double rating, double cost) {
		this.oldCity = null;
		this.oldPOI = oldPOI;
		this.isCity = false;
		this.parentCity = parentCity;
		this.newCity = null;
		this.newPOI = new POI(newName, new Coordinate(x,y), type, rating, cost);
	}

	public City getOldCity() {
		return this.oldCity;
	}
	
	public POI getOldPOI() {
		return this.oldPOI;
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
