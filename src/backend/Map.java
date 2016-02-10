package backend;
import java.util.ArrayList;import java.util.Comparator;
import java.util.HashMap;

import utils.AlphabetComparator;
import utils.CompareType;
import utils.FlexRedBlackTree;
import utils.PopulationComparator;
import utils.RatingComparator;

public class Map {
	
	private HashMap<String, City> cities;
	private FlexRedBlackTree<Place> alphabetCityTree;
	private FlexRedBlackTree<Place> ratingCityTree;
	private FlexRedBlackTree<City> popCityTree;
	
	public Map(){
		this.alphabetCityTree=new FlexRedBlackTree<Place>(new AlphabetComparator<>());
		this.ratingCityTree=new FlexRedBlackTree<Place>(new RatingComparator<>());
		this.popCityTree=new FlexRedBlackTree<City>(new PopulationComparator());
		
		
	}
	public ArrayList<Place> getAlpCityList() {
		return this.alphabetCityTree.toArrayList();
	}
	public ArrayList<Place> getRatCityList() {
		return this.ratingCityTree.toArrayList();
	}
	public ArrayList<City> getPopCityList() {
		return this.popCityTree.toArrayList();
	}
	public ArrayList<Link> getRoute(String from, String to) {
		return null;
	}
	public boolean addEntry(Form f) {
		return false;
	}
	public boolean editEntry(Form f) {
		return false;
	}
	public FlexRedBlackTree<City> getPopTree(){
		return this.popCityTree;
	}
	public FlexRedBlackTree<Place> getAlphabetTree(){
		return this.alphabetCityTree;
	}
	public FlexRedBlackTree<Place> getRatingTree(){
		return this.ratingCityTree;
	}
}
