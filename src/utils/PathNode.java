package utils;

import java.util.ArrayList;

import backend.Place;

public class PathNode implements Comparable<PathNode> {

	private Place currentPlace;
	private Place linkTo;
	private Double costTraveled;
	private Double totalCost;
	private ArrayList<Place> whereIveBeen;
	
	public PathNode(Place pcurrentPlace,Place plinkTo, double pCostTraveled,double estCost, ArrayList<Place> wib) {
		
		costTraveled = pCostTraveled;
		totalCost = costTraveled+estCost;
		whereIveBeen = wib;
		currentPlace =pcurrentPlace;
//		whereIveBeen.add(currentPlace);
		linkTo = plinkTo;
	}
	
	public PathNode(){
		totalCost =null;
		whereIveBeen = null;
		currentPlace = null;
		linkTo = null;
	}
	
	public void setTotalCost(Double d){
		totalCost = d;
	}
	
	public void setWIB(ArrayList<Place> w){
		whereIveBeen = w;
	}
	
	public void setCurrentPlace(Place cp){
		currentPlace = cp;
	}
	
	public void setLinkTo(Place lt){
		linkTo = lt;
	}
	
	public Place getLinkTo(){
		return linkTo;
	}
	
	public Place getCurrentPlace(){
		return currentPlace;
	}
	
	public String toString(){
		return "currentPlace: "+currentPlace+" linkTo "+linkTo+" total cost "+totalCost+" wib = "+whereIveBeen.toString();
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
