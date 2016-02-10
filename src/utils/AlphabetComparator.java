package utils;

import java.util.Comparator;

import backend.Place;

public class AlphabetComparator<T extends Place> implements Comparator<T> {

	public int compare(T o1, T o2) {
		return o1.getName().compareTo(o2.getName());
	}

}