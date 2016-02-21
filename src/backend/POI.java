package backend;

/**
 * POI class extends Place and represents the point of interest (POI) of a city.
 * AVAILABLE_TYPES - represents available types of POI that can be
 * type - type of POI
 * cost - estimated cost to be spent at POI
 */
public class POI extends Place {
	public static final String[] AVAILABLE_TYPES = {"Museums", "Farm", "Church", "Shopping Center", "Amusement Park", "Zoo",
													"Stadium", "Library", "Theatre", "Nature Preserve", "Park", "Laser Tag Center",
													"Capital", "Indoor Park", "University", "Water Park"};
	private String type;
	private double cost;
	/**
	 * The constructor of POI class initialize its fields taken from parameters.
	 * @param name
	 * @param location
	 * @param type
	 * @param rating
	 * @param cost
	 */
	public POI(String name, Coordinate location, String type, double rating, double cost) {
		super(name, location, rating);
		this.type = type;
		this.cost = cost;
	}
	/**
	 * return the type of POI
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * return the estimated cost to be spent at the POI
	 * @return
	 */
	public double getCost() {
		return this.cost;
	}
	/**
	 * set the type of POI to given parameter type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * set the cost of POI to given parameter cost
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
}
