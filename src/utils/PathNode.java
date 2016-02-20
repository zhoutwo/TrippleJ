package utils;

import java.util.ArrayList;

import backend.Place;

public class PathNode implements Comparable<PathNode> {

	private Place currentPlace;
	private Place linkTo;
	private Double costTraveled;
	private Double totalCost;
	private ArrayList<Place> whereIveBeen;
	
	public PathNode(Place pcurrentPlace,Place plinkTo, double pTotalCost, ArrayList<Place> wib) {
		totalCost = pTotalCost;
//		costTraveled = pCostTraveled;
		whereIveBeen = wib;
		currentPlace =pcurrentPlace;
//		whereIveBeen.add(currentPlace);
		linkTo = plinkTo;
	}
	
	public Place getLinkTo(){
		return linkTo;
	}
	
	public Place getCurrentPlace(){
		return currentPlace;
	}
	
	public String toString(){
		return "currentPlace: "+currentPlace+" linkTo "+linkTo+" wib = "+whereIveBeen.toString();
//		return "node tc = "+totalCost+", costTraveled = "+costTraveled+", where ive been = "+whereIveBeen.toString();
	}

	@Override
	public int compareTo(PathNode o) {
		return totalCost.compareTo(o.totalCost);
	}
	
	public ArrayList<Place> getWIB(){
		return whereIveBeen;
	}
	
	public void addWIB(Place p){
		whereIveBeen.add(p);
	}
	
	public Double getCostTraveled(){
		return costTraveled;
	}
}
