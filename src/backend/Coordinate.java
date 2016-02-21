package backend;

/**
 * Coordinate class represents the longitude and latitude coordinates of places.
 * x - longitude of coordinate of a place in double 
 * y - latitude of coordinate of a place in double
 */
public class Coordinate {

	private double x;
	private double y;

	/**
	 * The constructor of Coordinate class. This constructor will take parameter
	 * x and y and will initialize them to the fields.
	 * 
	 * @param y
	 * @param x
	 */
	public Coordinate(double y, double x) {
		this.x = x;
		this.y = y;
	}

	/**
	 * return the x coordinate of a place
	 * 
	 * @return
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * return the y coordinate of a place
	 * 
	 * @return
	 */
	public double getY() {
		return this.y;
	}
}
