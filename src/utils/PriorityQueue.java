package utils;
import java.util.ArrayList;

/**
 * 
 * @author Josh Woods
 * @date 1/9/2016
 * 
 * The PriorityQueue class is a class that uses an Array List of generic elements
 * that either extend comparable or thier super extends comparable. the values with 
 * the least value hold priority over those with higher numbers. The array list 
 * is sorted in the form of a minimum binary heap
 * 
 * Variables: 
 * 		head   
 * 		size   
 * Constructors: 
 * 		PriorityQueue() 
 * Methods: 
 * 		boolean add(T o)
 * 		boolean offer(T o)
 * 		T peek()
 * 		T poll()
 * 		void swap(int index1,int index2)
 * 		boolean remove(T o)
 * 		int size()
 * 		
 */
public class PriorityQueue<T extends Comparable<? super T>> extends ArrayList<T> {
	
	private T head; // minimum value in the array position 0
	private int size; // the size of the array

	public PriorityQueue(){
		head = null;
		size = 0;
	}
	
	public PriorityQueue(T h){
		head = null;
		size = 0;
		this.offer(h);
	}
	
	/**
	 * Inserts the specified element into this priority queue.
	 * @return true if element added and false otherwise
	 */
	public boolean add(T o){
		if(super.add(o)){
			int currentPos = size;
			int parentPos = ((currentPos+1)/2)-1;
			while(currentPos>0){
				if(this.get(currentPos).compareTo(this.get(parentPos))==-1){
					this.swap(parentPos, currentPos);
				}
				currentPos = parentPos;
				parentPos = ((currentPos+1)/2)-1;
			}
			size +=1;
			head = this.get(0);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Inserts the specified element into this priority queue.
	 * @return true if the element was inserted and false otherwise 
	 */
	public boolean offer(T o){
		return this.add(o);
	}
	
	/**
	 * Retrieves, but does not remove, the head of this queue, 
	 * or returns null if this queue is empty.
	 * @return head of the queue
	 */
	public T peek(){
		return head;
	}
	
	/**
	 * Retrieves and removes the head of this queue, 
	 * or returns null if this queue is empty.
	 * @return head of the queue
	 */
	public T poll(){
		if(head==null){
			return null;
		}
		T temp = this.peek();
		this.remove(temp);
		return temp;
	}
	
	/**
	 * swaps the positions of two elements in the array 
	 * @param index1
	 * @param index2
	 */
	private void swap(int index1,int index2){
		T temp = this.get(index1);
		this.set(index1, this.get(index2));
		this.set(index2, temp);
	}
	
	/**
	 * Removes a single instance of the specified element from this queue, 
	 * if it is present.
	 * @return true if element was removed and false otherwise
	 */
	public boolean remove(T o){
		boolean didRemove = false;
		if(this.contains(o)){
			if(size==1){
				size = 0;
				this.clear();
				head = null;
				return true;
			}
			int currentPos = this.indexOf(o);
			this.swap(currentPos, size-1);
			size -=1;
			super.remove(size);	
			didRemove = true;
			while(currentPos<size){
				int leftChildPos = ((currentPos+1)*2)-1;
				int rightChildPos = (currentPos+1)*2;
				// if current node has two children
				if(rightChildPos<=(size-1)){
					// find least child 
					int childCompare = this.get(leftChildPos).compareTo(this.get(rightChildPos));
					// if right child less than left child
					if(childCompare == 1){
						if(this.get(rightChildPos).compareTo(this.get(currentPos))==-1){
							this.swap(rightChildPos, currentPos);
							
						}
						currentPos = rightChildPos;
					}
					// left child is less than or equal to right child
					else{
						if(this.get(leftChildPos).compareTo(this.get(currentPos))==-1){
							this.swap(leftChildPos, currentPos);	
						}
						currentPos = leftChildPos;
					}
				}
				// if current node has only left child
				else if(leftChildPos<=(size-1)){
					if(this.get(leftChildPos).compareTo(this.get(currentPos))==-1){
						this.swap(leftChildPos, currentPos);
					}
					currentPos = leftChildPos;
				}
				// current node has no children
				else{
					currentPos = (currentPos+1)*2;
				}
			}
		}
		head = this.get(0);
		return didRemove;
	}
	
	/**
	 * 
	 * @return Returns the number of elements in this collection.
	 */
	public int size(){
		return size;
	}
	
}
