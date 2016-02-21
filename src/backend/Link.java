package backend;

import utils.RoadType;
/**
 * Link class represents the links between the places. Links will be stored in ArrayList neighbors in Place class.
 * ISPEED - Interstate speed in miles per hour
 * HSPEED - Highway speed in miles per hour
 * RSPEED - Road speed in miles per hour
 * time - a type of cost to trip in time
 * distance - a type of cost to trip in distance
 * roadType - a type of road in RoadType
 * place - a place where the link is toward to
 * type - name of the road type in String
 */
public class Link {
	
	private static final double ISPEED = 85.0;
	private static final double HSPEED = 45.0;
	private static final double RSPEED = 40.0;
	private double time;
	private double distance;
	private RoadType roadType;
	private Place place;
	private String type;
	
	/**
	 * The constructor of Link class initialize the fields by either parameters or using methods.
	 * @param name
	 * @param pFromPlace
	 * @param pToPlace
	 */
	public Link(String type,Place placeFrom,Place placeTo){
		place = placeTo;
		this.type = type;
		setType();
		setDistance(placeFrom.getLocation());
		setTime();
	}
	/**
	 * set distance of the places between the links
	 * @param from
	 */
	private void setDistance(Coordinate from) {
		double xFrom = Math.abs(from.getX());
		double yFrom = Math.abs(from.getY());
		double xTo = Math.abs(place.getLocation().getX());
		double yTo = Math.abs(place.getLocation().getY());
		double xDistance = xFrom-xTo;
		double yDistance = yFrom - yTo;
		distance = Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
	}
	/**
	 * initialize time cost of traveling places between the links based on road type.
	 */
	private void setTime() {
		if(type.equals(RoadType.INTERSTATE)){
			time = distance/ISPEED;
		}
		else if(type.equals(RoadType.HIGHWAY)){
			time = distance/HSPEED;
		}
		else {
			time = distance/RSPEED;
		}
	}
	/**
	 * initialize the type of the road that is in between the places of the links
	 */
	private void setType() {
		char c = type.charAt(1);
		if(c=='I'){
			roadType = RoadType.INTERSTATE;
		}
		else if(c=='H'){
			roadType = RoadType.HIGHWAY;
		}
		else {
			roadType = RoadType.ROAD;
		}
	}
	/**
	 * return the time cost to travel between places
	 * @return
	 */
	public double getTime() {
		return this.time;
	}
	/**
	 * return the distance to travel between the places
	 * @return
	 */
	public double getDistance() {
		return this.distance;
	}
	/**
	 * return the type of the road
	 * @return
	 */
	public RoadType getRoadType() {
		return this.roadType;
	}
	/**
	 * return the place that is linked to
	 * @return
	 */
	public Place getPlace(){
		return place;
	}
	/**
	 * 
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * set the distance to given parameter integer
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**
	 * set the type of the road to given parameter String
	 * @param type
	 */
	public void setRoadType(RoadType type) {
		this.roadType = type;
	}
	/**
	 * set the place to given parameter place 
	 * @param place
	 */
	public void setPlace(Place place){
		this.place=place;
	}
	/**
	 * prints out variable for testing purpose
	 */
	public String toString(){
		return " to "+place.name+" dis = "+distance+", t = "+time+", roadtype = "+type+", roadname = "+type;
	}
}
