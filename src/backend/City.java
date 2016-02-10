package backend;

public class City extends Place {
	protected int population;
	
	// Testing only
	public City(int p) {
		this.population = p;
	}
	
	public int getPopulation() {
		return this.population;
	}
}
