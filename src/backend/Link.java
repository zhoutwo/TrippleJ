package backend;

import utils.RoadType;

public class Link {
	
	private static final int ISPEED = 85;
	private static final int HSPEED = 45;
	private static final int RSPEED = 40;
	private int time;
	private int distance;
	private RoadType type;
	private Place place;
	private String name;
//	private Place dest;
	
	public Link(String pName,Coordinate fromCor,Place pPlace){
		place = pPlace;
		name = pName;
		setType();
		setDistance();
		setTime();
	}
	
	private void setDistance() {
		
	}
	
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

	private void setType() {
		char c = name.charAt(0);
		String i = "Interstar";
		if(c=='I'){
			type = RoadType.INTERSTATE;
		}
		else if(c=='H'){
			type = RoadType.HIGHWAY;
		}
		else {
			type = RoadType.ROAD;
		}
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
