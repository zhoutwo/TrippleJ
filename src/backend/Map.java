package backend;

import java.util.ArrayList;
import java.util.HashMap;
import utils.*;

public class Map {
	
	private HashMap<String, City> cities;
	private FlexRedBlackTree<City> alpCityList;
	private FlexRedBlackTree<City> ratCityList;
	private FlexRedBlackTree<City> popCityList;
	
	public Map() {
		this.cities = new HashMap<String, City>();
		this.alpCityList = new FlexRedBlackTree<City>(new AlphabetComparator<City>());
		this.ratCityList = new FlexRedBlackTree<City>(new RatingComparator<City>());
		this.popCityList = new FlexRedBlackTree<City>(new PopulationComparator());
	}
	
	public ArrayList<City> getAlpCityList() {
		return this.alpCityList.toArrayList();
	}
	public ArrayList<City> getRatCityList() {
		return this.ratCityList.toArrayList();
	}
	public ArrayList<City> getPopCityList() {
		return this.popCityList.toArrayList();
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
}
