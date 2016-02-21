package utils;

import java.util.Comparator;

import backend.City;
/**
 * PopulationComparator class to help FlexRedBlackTree class to compare based on the population
 * This is reverse of original comparator.
 */
public class PopulationComparator implements Comparator<City> {
	/**
	 * return 1 if o2 is higher, 0 if same, -1 if o1 is higher
	 * @param o1
	 * @param o2
	 */
	public int compare(City o1, City o2) {
		return ((new Integer(o2.getPopulation())).compareTo((new Integer(o1.getPopulation()))));
	}

}
