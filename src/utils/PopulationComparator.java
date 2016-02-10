package utils;

import java.util.Comparator;

import backend.City;

public class PopulationComparator implements Comparator<City> {

	public int compare(City o1, City o2) {
		return ((new Integer(o1.getPopulation())).compareTo((new Integer(o2.getPopulation()))));
	}

}
