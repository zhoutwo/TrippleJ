package utils;

import java.util.Comparator;

import backend.Place;
/**
 * AlphabetComparator class to help FlexRedBlackTree class to compare by alphabetical order or Place names
 * This is same as original comparator.
 */
public class AlphabetComparator<T extends Place> implements Comparator<T> {
	/**
	 * return 1 if o1 is higher, 0 if same, -1 if o2 is higher
	 * @param o1
	 * @param o2
	 */
	public int compare(T o1, T o2) {
		return o1.getName().compareTo(o2.getName());
	}

}