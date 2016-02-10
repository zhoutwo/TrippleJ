package backend;

import java.util.ArrayList;
import java.util.HashMap;
import utils.FlexRedBlackTree;

public class Map {
	
	private HashMap<String, City> cities;
	private FlexRedBlackTree<City> alpCityList;
	private FlexRedBlackTree<City> ratCityList;
	private FlexRedBlackTree<City> popCityList;
	
	public ArrayList<Place> getAlpCityList() {
		return this.alpCityList.toArrayList();
	}
	public ArrayList<Place> getRatCityList() {
		return this.ratCityList.toArrayList();
	}
	public ArrayList<Place> getPopCityList() {
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
