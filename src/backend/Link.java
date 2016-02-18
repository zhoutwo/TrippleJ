package backend;

import utils.RoadType;

public class Link {
	
	private int time;
	private int distance;
	private RoadType type;
	private Place place;
	private String name;
//	private Place dest;
	
	public Link(String pName,Coordinate fromCor,Place pPlace){
		place = pPlace;
		name = pName;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
//	public Place getDest() {
//		return this.dest;
//	}
	
	public RoadType getRoadType() {
		return this.type;
	}
	
	public Place getPlace(){
		return place;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public void setRoadType(RoadType type) {
		this.type = type;
	}
	public void setPlace(Place place){
		this.place=place;
	}
}
