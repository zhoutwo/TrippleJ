package backend;
import java.util.ArrayList;import java.util.Comparator;
import java.util.HashMap;
import utils.*;

public class Map {
	
	private HashMap<String, City> cities;
	private FlexRedBlackTree<City> alpCityTree;
	private FlexRedBlackTree<City> ratCityTree;
	private FlexRedBlackTree<City> popCityTree;
	
	public Map() {
		this.cities = new HashMap<String, City>();
		this.alpCityTree = new FlexRedBlackTree<City>(new AlphabetComparator<City>());
		this.ratCityTree = new FlexRedBlackTree<City>(new RatingComparator<City>());
		this.popCityTree = new FlexRedBlackTree<City>(new PopulationComparator());
	}
	
	public ArrayList<City> getAlpCityList() {
		return this.alpCityTree.toArrayList();
	}
	public ArrayList<City> getRatCityList() {
		return this.ratCityTree.toArrayList();
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
	public FlexRedBlackTree<City> getAlphabetTree(){
		return this.alpCityTree;
	}
	public FlexRedBlackTree<City> getRatingTree(){
		return this.ratCityTree;
	}
}
