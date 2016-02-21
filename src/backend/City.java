package backend;

import java.util.ArrayList;
import utils.*;

/**
 * This class is a specific type of the Place class. in addition to the Place class it has a population.
 * Fields: 
 * population - Integer representing the number of people that live in the city
 * pois - ArrayList of POI
 * alpPOITree - FlextRedBlackTree of points of interest sorted alphabetically
 * ratPOITree - FlextRedBlackTree of points of interest sorted by rating
 */
public class City extends Place {
	
	private int population;
	private ArrayList<POI> pois;
	private FlexRedBlackTree<POI> alpPOITree;
	private FlexRedBlackTree<POI> ratPOITree;
	
	/**
	 * This is the constructor that initializes an object city and all of its fields with no points of interest
	 * @param name name of the city in String
	 * @param population population of the city in integer
	 * @param location location of the city in Coordinate
	 * @param rating rating of the city in double out of 5.0
	 */
	public City(String name,int population, Coordinate location, double rating) {
		super(name, location, rating);
		this.population = population;
		alpPOITree = new FlexRedBlackTree<POI>(new AlphabetComparator<POI>());
		ratPOITree = new FlexRedBlackTree<POI>(new RatingComparator<POI>());
		pois = new ArrayList<POI>();
		
	}
	
	/**
	 * This constructor initializes a city object with a given array list of points of interest
	 * @param name name of the city in String
	 * @param population number of population of the city in Integer
	 * @param location location of the city in Coordinate class
	 * @param rating rating of the city in double out of 5.0
	 * @param pois ArrayList of POI of the city
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
	 * returns FlexRedBlackTree of points of interest sorted by alphabet
	 * @return
	 */
	public FlexRedBlackTree<POI> getAlpPOITree() {
		return alpPOITree;
	}
	
	/**
	 * returns FlexRedBlackTree of points of interest sorted by rating
	 * @return
	 */
	public FlexRedBlackTree<POI> getRatPOITree() {
		return ratPOITree;
	}
	
	/**
	 * add a point of interest to the city
	 * @param poi POI to be added
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
	 * @param poi POI to be removed
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
	 * @param pois ArrayList of POIs that is to be loaded
	 */
	public void loadPOIList(ArrayList<POI> pois) {
		this.pois = pois;
		alpPOITree = new FlexRedBlackTree<POI>(new AlphabetComparator<POI>());
		ratPOITree = new FlexRedBlackTree<POI>(new RatingComparator<POI>());
		for (POI p : pois) {
			alpPOITree.insert(p);
			ratPOITree.insert(p);
		}
	}
}
