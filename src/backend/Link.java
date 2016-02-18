package backend;

import utils.RoadType;

public class Link {
	
	private static final double ISPEED = 85.0;
	private static final double HSPEED = 45.0;
	private static final double RSPEED = 40.0;
	private static final double MILPERGPS = 0.0195234613673439;
	private double time;
	private double distance;
	private RoadType type;
	private Place place;
	private String name;
//	private Place dest;
	
	public Link(String pName,Coordinate fromCor,Place pPlace){
		place = pPlace;
		name = pName;
		setType();
		setDistance(fromCor);
		setTime();
	}
	
	private void setDistance(Coordinate f) {
		double xf = Math.abs(f.getX());
		double yf = Math.abs(f.getY());
		double xt = Math.abs(place.getLocation().getX());
		double yt = Math.abs(place.getLocation().getY());
		double xDis = xf-xt;
		double yDis = yf - yt;
		distance = Math.sqrt((xDis*xDis)+(yDis*yDis));
		
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

	public double getTime() {
		return this.time;
	}
	
	public double getDistance() {
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
