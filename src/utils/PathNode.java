package utils;

import java.util.ArrayList;

import backend.Place;

public class PathNode implements Comparable<PathNode> {

	private Place toPlace;
	private Double costTraveled;
	private Double totalCost;
	private ArrayList<Place> whereIveBeen;
	
	public PathNode(Place ptoPlace,double pTotalCost, double pCostTraveled, ArrayList<Place> wib) {
		totalCost = pTotalCost;
		costTraveled = pCostTraveled;
		whereIveBeen = wib;
		toPlace = ptoPlace;
	}
	
	public Place getToPlace(){
		return toPlace;
	}
	
	public String toString(){
		return "node tc = "+totalCost+", costTraveled = "+costTraveled+", where ive been = "+whereIveBeen.toString();
	}

	@Override
	public int compareTo(PathNode o) {
		return totalCost.compareTo(o.totalCost);
	}
	
	public ArrayList<Place> getWIB(){
		return whereIveBeen;
	}
	
	public Double getCostTraveled(){
		return costTraveled;
	}
}
