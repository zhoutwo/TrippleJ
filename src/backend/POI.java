package backend;

/**
 * POI class extends Place and represents the point of interest (POI) of a city.
 * AVAILABLE_TYPES - represents available types of POI that can be 
 * type - type of POI 
 * cost - estimated cost to be spent at POI
 */
public class POI extends Place {
	public static final String[] AVAILABLE_TYPES = { "Museums", "Farm", "Church", "Shopping Center", "Amusement Park",
			"Zoo", "Stadium", "Library", "Theatre", "Nature Preserve", "Park", "Laser Tag Center", "Capital",
			"Indoor Park", "University", "Water Park" };
	private String type;
	private double cost;

	/**
	 * The constructor of POI class initialize its fields taken from the
	 * parameters.
	 * 
	 * @param name
	 *            name of POI in String
	 * @param location
	 *            location of POI in Coordinate class
	 * @param type
	 *            type of POI String class
	 * @param rating
	 *            rating of POI out of 5.0 in double
	 * @param cost
	 *            estimated cost to be spent at POI in double
	 */
	public POI(String name, Coordinate location, String type, double rating, double cost) {
		super(name, location, rating);
		this.type = type;
		this.cost = cost;
	}

	/**
	 * return the type of POI
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * return the estimated cost to be spent at the POI
	 * 
	 * @return
	 */
	public double getCost() {
		return this.cost;
	}

	/**
	 * set the type of POI to given parameter type
	 * 
	 * @param type
	 *            type to be set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * set the cost of POI to given parameter cost
	 * 
	 * @param cost
	 *            cost to be set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
}
