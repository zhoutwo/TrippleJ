package backend;

import utils.RoadType;

public class Link {
	
	private int time;
	private int distance;
	private RoadType type;
	private Place dest;
	
	public int getTime() {
		return this.time;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public Place getDest() {
		return this.dest;
	}
	
	public RoadType getRoadType() {
		return this.type;
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
}
