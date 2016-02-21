package utils;

import java.util.Comparator;

import backend.Place;
/**
 * RatingComparator class to help FlexRedBlackTree class to compare based on the ratings
 * This is reverse of original comparator.
 */
public class RatingComparator<T extends Place> implements Comparator<T> {
	/**
	 * return 1 if o2 is higher, 0 if same, -1 if o1 is higher
	 * @param o1
	 * @param o2
	 */
	public int compare(T o1, T o2) {
		return ((new Double(o2.getRating())).compareTo((new Double(o1.getRating()))));
	}

}