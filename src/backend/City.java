package backend;

import java.awt.Point;
import java.util.ArrayList;

import utils.*;

public class City extends Place {
	
	private int population;
	private ArrayList<POI> pois;
	private FlexRedBlackTree<POI> alpPOITree;
	private FlexRedBlackTree<POI> ratPOITree;
	
	public City(String name,int population, Coordinate location, double rating) {
		super(name, location, rating);
		this.population = population;
		alpPOITree = new FlexRedBlackTree<POI>(new AlphabetComparator<POI>());
		ratPOITree = new FlexRedBlackTree<POI>(new RatingComparator<POI>());
		pois = new ArrayList<POI>();
		
	}
	
	public City(String name,int population, Coordinate location, double rating, ArrayList<POI> pois) {
		this(name, population, location, rating);
		loadPOIList(pois);
	}
	
	// Testing only
	public City(String name, int p){
		super(name, null, 0);
		this.population = p;
	}
	
	
	
	
	
	public int getPopulation() {
		return this.population;
	}
	public String getPopulationAsString(){
		String s=this.population+"";///123151
		for(int i=s.length();i>2;i-=2){
			s=s.substring(0, i-2)+","+s.substring(i-3,s.length());
		}
		return s;
	}
	
	public String toString() {
		return this.name + ' ' + this.population;
	}
	
	public ArrayList<POI> getPois(){
		return this.pois;
	}
	
	public FlexRedBlackTree<POI> getAlpPOITree() {
		return alpPOITree;
	}
	
	public FlexRedBlackTree<POI> getRatPOITree() {
		return ratPOITree;
	}
	
	public boolean addPOI(POI poi) {
		if (alpPOITree.insert(poi) && ratPOITree.insert(poi)) {
			pois.add(poi);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removePOI(POI poi) {
		if (alpPOITree.remove(poi) && ratPOITree.remove(poi)) {
			pois.remove(poi);
			return true;
		} else {
			return false;
		}
	}
	
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
