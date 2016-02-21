package utils;

import java.util.Comparator;

import backend.Place;

public class RatingComparator<T extends Place> implements Comparator<T> {

	public int compare(T o1, T o2) {
		// We want decreasing order
		return ((new Double(o2.getRating())).compareTo((new Double(o1.getRating()))));
	}

}