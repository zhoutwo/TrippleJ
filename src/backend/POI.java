package backend;

public class POI extends Place {
	
	private String type;
	private double cost;

	public POI(String name, Coordinate location, String type, int rating, double cost) {
		super(name, location, rating);
		this.type = type;
		this.cost = cost;
	}
	
	public String getType() {
		return this.type;
	}
	public double getCost() {
		return this.cost;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}
