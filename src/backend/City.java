package backend;

import java.util.ArrayList;

import utils.*;

public class City extends Place {
	
	private int population;
	private ArrayList<POI> pois;
	private FlexRedBlackTree<POI> alpPOIList;
	private FlexRedBlackTree<POI> ratPOIList;
	
	public City(String name, Coordinate location, double rating, int population) {
		super(name, location, rating);
		this.population = population;
	}
	
	// Testing only
	public City(String name, int p){
		super(name, null, 0);
		this.population = p;
	}
	
	public int getPopulation() {
		return this.population;
	}
	
	public String toString() {
		return this.name + ' ' + this.population;
	}
	
	public ArrayList<POI> getPois(){
		return this.pois;
	}
	
	public ArrayList<POI> getAlpPOIList() {
		return this.alpPOIList.toArrayList();
	}
	
	public ArrayList<POI> getRatPOIList() {
		return this.ratPOIList.toArrayList();
	}
}
