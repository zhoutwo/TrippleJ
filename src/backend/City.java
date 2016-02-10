package backend;

public class City extends Place {
	
	protected String name;
	protected int population;
	
	// Testing only
	public City(String name, int p){
		name = name;
		population = p;
	}
	
	public int getPopulation() {
		return this.population;
	}
	
	public String toString() {
		return this.name + ' ' + this.population;
	}
}
