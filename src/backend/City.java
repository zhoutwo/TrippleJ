package backend;

import java.awt.Point;
import java.util.ArrayList;

import utils.*;

public class City extends Place {
	
	private int population;
	private ArrayList<POI> pois;
	private FlexRedBlackTree<POI> alpPOIList;
	private FlexRedBlackTree<POI> ratPOIList;
	private Point mapLoc;
	
	public City(String name,int population, Coordinate location, double rating) {
		super(name, location, rating);
		this.population = population;
		setMapLocal();
	}
	
	// Testing only
	public City(String name, int p){
		super(name, null, 0);
		this.population = p;
	}
	
	public void setMapLocal(){
		mapLoc = new Point();
		double y = (location.getY()-40)*(-233);
		double x = (location.getX()+98)*(150);
		mapLoc.setLocation(x,y);
	}
	
	public Point getMapLoc(){
		return mapLoc;
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
	
	public boolean addPOI(POI poi) {
		if (alpPOIList.insert(poi) && ratPOIList.insert(poi)) {
			pois.add(poi);
			return true;
		} else {
			return false;
		}
	}
}
