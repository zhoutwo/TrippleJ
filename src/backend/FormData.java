package backend;

public class FormData {
	
	private final boolean isCity;
	private final City oldCity;
	private final POI oldPOI;
	private final City parentCity;
	private final City newCity;
	private final POI newPOI;
	
	/**
	 * This constructor is used when the form is to change a City.
	 * @param oldCity the original City
	 * @param newName the new name for the City
	 * @param x the new x-coordinate for the City
	 * @param y the new y-coordinate for the City
	 * @param rating the rating of the City
	 * @param population the population of the City
	 */
	public FormData(City oldCity, String newName, double x, double y, double rating, int population) {
		this.oldCity = oldCity;
		this.oldPOI = null;
		this.isCity = true;
		this.parentCity = null;
		this.newCity = new City(newName, population, new Coordinate(x, y), rating);
		this.newPOI = null;
	}
	
	/**
	 * This constructor is used when the form is to change a POI.
	 * @param oldPOI the original POI
	 * @param newName the new name for the POI
	 * @param x the new x-coordinate for the POI
	 * @param y the new y-coordinate for the POI
	 * @param parentCity the parent City in which the POI is located
	 * @param type type of the POI
	 * @param rating rating of the POI
	 * @param cost cost of the POI
	 */
	public FormData(POI oldPOI, String newName, double x, double y, City parentCity, String type, double rating, double cost) {
		this.oldCity = null;
		this.oldPOI = oldPOI;
		this.isCity = false;
		this.parentCity = parentCity;
		this.newCity = null;
		this.newPOI = new POI(newName, new Coordinate(x,y), type, rating, cost);
	}

	/**
	 * Returns the original City which will be changed
	 * @return the old City
	 */
	public City getOldCity() {
		return this.oldCity;
	}
	
	/**
	 * Returns the original POI which will be changed
	 * @return the old POI
	 */
	public POI getOldPOI() {
		return this.oldPOI;
	}
	
	/**
	 * Returns whether the form is for a City or not.
	 * @return True if the form is for a City, false for a POI.
	 */
	public boolean isCity() {
		return this.isCity;
	}
	
	/**
	 * Returns the parent City of the POI, if the form is about a POI
	 * @return the parent City of the POI under change
	 */
	public City getParentCity() {
		return this.parentCity;
	}
	
	/**
	 * Returns the City constructed from user inputs
	 * @return The new City
	 */
	public City getNewCity() {
		return this.newCity;
	}
	
	/**
	 * Returns the POI constructed from user inputs
	 * @return The new POI
	 */
	public POI getNewPOI() {
		return this.newPOI;
	}
}
