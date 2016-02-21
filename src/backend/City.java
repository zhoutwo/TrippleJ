package backend;

import java.util.ArrayList;
import utils.*;

/**
 * This class is a specific type of the Place class. in addition to the Place class it has a population.
 * Fields: 
 * population - int represeting the number of people that live in the city
 * pois - array list of points of interest
 * alpPOITree - flex red black tree of points of interest sorted alphabetically
 * ratPOITree - flex red black tree of points of interest sorted by rating
 */
public class City extends Place {
	
	private int population;
	private ArrayList<POI> pois;
	private FlexRedBlackTree<POI> alpPOITree;
	private FlexRedBlackTree<POI> ratPOITree;
	
	/**
	 * this is the constructo that initializes an object city and all of its fields with no points of interst
	 * @param name
	 * @param population
	 * @param location
	 * @param rating
	 */
	public City(String name,int population, Coordinate location, double rating) {
		super(name, location, rating);
		this.population = population;
		alpPOITree = new FlexRedBlackTree<POI>(new AlphabetComparator<POI>());
		ratPOITree = new FlexRedBlackTree<POI>(new RatingComparator<POI>());
		pois = new ArrayList<POI>();
		
	}
	
	/**
	 * this constructor initializes a city object with a given array list of points of interest
	 * @param name
	 * @param population
	 * @param location
	 * @param rating
	 * @param pois
	 */
	public City(String name,int population, Coordinate location, double rating, ArrayList<POI> pois) {
		this(name, population, location, rating);
		loadPOIList(pois);
	}
	
	/**
	 * returns the population of a city
	 * @return
	 */
	public int getPopulation() {
		return this.population;
	}
	
	/**
	 * return the population of a city as a string 
	 * @return
	 */
	public String getPopulationAsString(){
		String s=this.population+"";///123151
		for(int i=s.length();i>2;i-=2){
			s=s.substring(0, i-2)+","+s.substring(i-3,s.length());
		}
		return s;
	}
	
	/**
	 * returns the name of a city as a string 
	 */
	public String toString() {
		return this.name;
	}
	
	/**
	 * returns an array list of the points of interest of a city 
	 * @return
	 */
	public ArrayList<POI> getPois(){
		return this.pois;
	}
	
	/**
	 * returns a flex red black tree of points of interest sorted by alphabet
	 * @return
	 */
	public FlexRedBlackTree<POI> getAlpPOITree() {
		return alpPOITree;
	}
	
	/**
	 * returns a flex red black tree of points of interest sorted by rating
	 * @return
	 */
	public FlexRedBlackTree<POI> getRatPOITree() {
		return ratPOITree;
	}
	
	/**
	 * add a point of intrest to the city
	 * @param poi
	 * @return
	 */
	public boolean addPOI(POI poi) {
		if (alpPOITree.insert(poi) && ratPOITree.insert(poi)) {
			pois.add(poi);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * remove a point of interest from a city
	 * @param poi
	 * @return
	 */
	public boolean removePOI(POI poi) {
		if (alpPOITree.remove(poi) && ratPOITree.remove(poi)) {
			pois.remove(poi);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * load a given array list of points of interest to the cities point of interest array list
	 * @param pl
	 */
	public void loadPOIList(ArrayList<POI> pl) {
		this.pois = pl;
		alpPOITree = new FlexRedBlackTree<POI>(new AlphabetComparator<POI>());
		ratPOITree = new FlexRedBlackTree<POI>(new RatingComparator<POI>());
		for (POI p : pl) {
			alpPOITree.insert(p);
			ratPOITree.insert(p);
		}
	}
}
