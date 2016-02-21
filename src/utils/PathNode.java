package utils;

import java.util.ArrayList;

import backend.Place;

public class PathNode implements Comparable<PathNode> {

	private Place currentPlace;
	private Place linkTo;
	private Double costTraveled;
	private Double totalCost;
	private ArrayList<Place> visitedPlace;
	/**
	 * The constructor that initialize all the fields with given parameters.
	 * @param currentPlace current place while searching route
	 * @param linkTo place that is linked to current place
	 * @param costTraveled total cost taken while finding the route
	 * @param costAdded cost to be added as move onto next place
	 * @param visitedPlace ArrayList of visited place while searching route
	 */
	public PathNode(Place currentPlace,Place linkTo, double costTraveled,double costAdded, ArrayList<Place> visitedPlace) {
		
		this.costTraveled = costTraveled;
		this.visitedPlace = visitedPlace;
		this.currentPlace =currentPlace;
		this.linkTo = linkTo;
		totalCost = costTraveled+costAdded;
//		whereIveBeen.add(currentPlace);
	}
	/**
	 * The constructor to initialize all the field to be null
	 */
	public PathNode(){
		totalCost = null;
		visitedPlace = null;
		currentPlace = null;
		linkTo = null;
	}
	/**
	 * set the total cost traveled to given parameter
	 * @param totalCost
	 */
	public void setTotalCost(Double totalCost){
		this.totalCost = totalCost;
	}
	/**
	 * set the visitedPlace field to given parameter
	 * @param visitedPlace
	 */
	public void setVisitedPlace(ArrayList<Place> visitedPlace){
		this.visitedPlace = visitedPlace;
	}
	/**
	 * set the current place to given parameter
	 * @param currentPlace
	 */
	public void setCurrentPlace(Place currentPlace){
		this.currentPlace = currentPlace;
	}
	/**
	 * set the field linkTo to given parameter
	 * @param linkTo linkTo be set
	 */
	public void setLinkTo(Place linkTo){
		this.linkTo = linkTo;
	}
	/**
	 * return the place that the current place is linked to
	 * @return
	 */
	public Place getLinkTo(){
		return linkTo;
	}
	/**
	 * return the current place
	 * @return
	 */
	public Place getCurrentPlace(){
		return currentPlace;
	}
	/**
	 * return the string to show the variables for testing purpose
	 */
	public String toString(){
		return "currentPlace: "+currentPlace+" linkTo "+linkTo+" total cost "+totalCost+" wib = "+visitedPlace.toString();
	}
	/**
	 * compare the total cost with the given PathNode
	 */
	@Override
	public int compareTo(PathNode o) {
		return totalCost.compareTo(o.totalCost);
	}
	/**
	 * the ArrayList of Place that has been visited while finding the route
	 * @return
	 */
	public ArrayList<Place> getVisitedPlace(){
		return visitedPlace;
	}
	/**
	 * 	add new place to visitedPlace
	 * @param place
	 */
	public void addVisitedPlace(Place place){
		visitedPlace.add(place);
	}
	/**
	 * return the cost of its travel
	 * @return
	 */
	public Double getCostTraveled(){
		return costTraveled;
	}
}
