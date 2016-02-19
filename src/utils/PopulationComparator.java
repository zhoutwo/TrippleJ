package utils;

import java.util.Comparator;

import backend.City;

public class PopulationComparator implements Comparator<City> {

	public int compare(City o1, City o2) {
		// We want largest to smallest, so switch order
		return ((new Integer(o2.getPopulation())).compareTo((new Integer(o1.getPopulation()))));
	}

}
