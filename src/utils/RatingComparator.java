package utils;

import java.util.Comparator;

import backend.Place;

public class RatingComparator<T extends Place> implements Comparator<T> {

	public int compare(T o1, T o2) {
		return ((new Integer(o1.getRating())).compareTo((new Integer(o2.getRating()))));
	}

}