package backend;

public abstract class Place {
	protected String name;
	protected int rating;
	
	public String getName() {
		return this.name;
	}
	
	public int getRating() {
		return this.rating;
	}
}
