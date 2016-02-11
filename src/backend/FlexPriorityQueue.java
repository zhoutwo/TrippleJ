package backend;

import java.util.ArrayList;
import java.util.Iterator;

import backend.Map.PlaceWithDistance;

/**
 * This is PriorityQueue class. 
 * PriorityQueue class has ArrayList<T> que that contains the elements 
 * in order of binary heap and takes type T.
 * This class includes Constructor, and methods called add, balanceHeap,
 * clear, contains, offer, peek, poll, remove, size, switchIndex, 
 * toArray, toArray(with parameter) and toPrint.
 * @author Seongjin Yoon. Created January 5th, 2016.
 */
public class FlexPriorityQueue<PlaceWithDistance> implements Iterable<PlaceWithDistance> {
	ArrayList<PlaceWithDistance> que;

	/**
	 * Creates a PriorityQueue with the type ArrayList and first element(index
	 * of 0) as null.
	 */
	public FlexPriorityQueue() {
		que = new ArrayList<PlaceWithDistance>();
		que.add(null);
	}
	/**
	 * @param e
	 * @return boolean Inserts the element e into the queue. returns true if
	 *         correctly inserted.
	 */
	public boolean add(PlaceWithDistance pwd) {
		if (pwd == null)
			throw new NullPointerException();
		que.add(pwd);
		balanceHeap(que.size() - 1, true);
		return true;
	}

	/**
	 * @param index
	 * @param b
	 *            Balance the queue to meet binary heap rules. If boolean b is
	 *            true, balance the queue as it goes down, and goes up the queue
	 *            if b is false. Both start checking the queue from the index of
	 *            parameter index.
	 */
	public void balanceHeap(int index, boolean b) {
		if (b) {
			PlaceWithDistance temp = que.get(index);
			
			if (index != 1 && ((backend.Map.PlaceWithDistance) que.get(index)).getCost()<((backend.Map.PlaceWithDistance) que.get(index / 2)).getCost()) {
				this.switchIndex(index, index / 2);
				if (index / 2 > 1)
					balanceHeap(index / 2, b);
			}
		}
		if (!b && index * 2 <= this.size()) {
			int left = index * 2;
			int right = index * 2 + 1;
			if (size() >= right) {
				if (((backend.Map.PlaceWithDistance) que.get(left)).getCost()>((backend.Map.PlaceWithDistance) que.get(right)).getCost()) {
					left = right;
				}
			}
			this.switchIndex(left, index);
			if (size() > 5)
				balanceHeap(left, false);

		}
	}

	/**
	 * @param first
	 * @param second
	 *            Switch the index of the parameters, first and second.
	 */
	public void switchIndex(int first, int second) {
		PlaceWithDistance pwd = que.get(first);
		que.set(first, que.get(second));
		que.set(second, pwd);
	}

	/**
	 * @return the array containing all the element in the queue.
	 */
	public Object[] toArray() {
		Object[] temp = new Object[size()];
		for(int i=1;i<que.size();i++){
			temp[i-1]=que.get(i);
		}
		return temp;
	}

	/**
	 * @param e
	 * @return boolean Inserts the element e into the queue. returns true if
	 *         correctly inserted.
	 */
	public boolean offer(PlaceWithDistance pwd) {
		if (pwd == null)
			throw new NullPointerException();
		que.add(pwd);
		balanceHeap(que.size() - 1, true);
		return true;
	}

	/**
	 * @return the head of this queue.
	 */
	public PlaceWithDistance peek() {
		if (size() == 0)
			return null;
		return que.get(1);
	}

	/**
	 * @return Remove the head of this queue and returns the element that was
	 *         removed.
	 */
	public PlaceWithDistance poll() {
		if (size() < 1)
			return null;
		PlaceWithDistance pwd = que.get(1);
		this.switchIndex(1, size());
		que.remove(size());
		balanceHeap(1, false);
		return pwd;
	}

	/**
	 * @param e
	 * @return boolean Removes element e from the queue. Returns true if the
	 *         element e is removed from the queue, false if this priority queue
	 *         does not contain this element e.
	 */
	public boolean remove(PlaceWithDistance pwd) {
		for (int i = 1; i < size(); i++) {
			if (((backend.Map.PlaceWithDistance) que.get(i)).getCost()==((backend.Map.PlaceWithDistance) pwd).getCost() ) {
				switchIndex(i, size());
				que.remove(size());
				balanceHeap(i, true);
				balanceHeap(i, false);
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes all the elements in the priority queue.
	 */
	public void clear() {
		que.clear();
		que.add(null);
	}

	/**
	 * @return the number of elements in the queue as Integer;
	 */
	public int size() {
		return que.size() - 1;
	}

	@Override
	public Iterator<PlaceWithDistance> iterator() {
		return null;
	}

	/**
	 * @param e
	 * @return boolean true if the queue contains element e
	 */
	public boolean contains(PlaceWithDistance pwd) {
		return que.contains(pwd);
	}

	/**
	 * Prints out all the elements that is in the queue.
	 */
	public void toPrint() {
		for (int i = 1; i < que.size(); i++)
			System.out.print(que.get(i) + " , ");
		System.out.println();
	}

	/**
	 * @param e
	 * @return the array containing all the element in the queue with the type
	 *         of parameter e.
	 */
	public  PlaceWithDistance[] toArray(PlaceWithDistance[] pwd) {
		ArrayList<PlaceWithDistance> temp=(ArrayList<PlaceWithDistance>) que;
		temp.remove(0);
		
		return temp.toArray(pwd);
	}

}

