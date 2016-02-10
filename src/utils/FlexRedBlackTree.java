package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import backend.Place;

public class FlexRedBlackTree <T extends Place> implements Iterable<FlexRedBlackTree.BinaryNode>{
	public BinaryNode root; 
	private int size; 
	private int modCount;
	private int rotationCount;
	private final Comparator<T> c;
	
	/** this constructor instantiates an empty Red Black Tree
	 * @throws Exception Wrong compare type
	*/
	public FlexRedBlackTree(Comparator<T> c){
		rotationCount = 0;
		modCount = 0;
		root = null;
		size = 0;
		this.c = c;
	}
	/**
	 *the enumeration Color declares two constant colors RED and BLACK
	 */
	public enum Color {RED, BLACK} 
	
	/** the isEmpty() method returns true if the tree is empty and false if it is not
	*/
	public boolean isEmpty(){
		if (root==null){
			return true;
		}else{
			return false;
		}
	}
	
	/** the height() method returns the height of the tree 
	*/
	public int height(){
		if(this.isEmpty()){
			return -1;
		}else 
			return root.height(); 
	}
	
	/** the size() method return the size of the tree 
	*/
	public int size(){
		return this.size;
	}
	
	/** the toString() method returns a string of all elements in the tree in order 
	// in brackets and seperated by commas
	*/
	public String toString(){
		ArrayList<FlexRedBlackTree.BinaryNode> alist = this.toArrayList();
		String str = "{ ";
		for (int i = 0;i < alist.size();i++){
			str = str + alist.get(i).getElement()+", "+alist.get(i).getColor()+", ";
		}
		str = str + "}";
		return str;
	}

	/** the toArrayList() method return an array list of elements in order
	*/
	public ArrayList<FlexRedBlackTree.BinaryNode> toArrayList(){
		ArrayList<FlexRedBlackTree.BinaryNode> aList = new ArrayList<FlexRedBlackTree.BinaryNode>();
		if(this.isEmpty()){
			return aList;
		}
			Iterator<FlexRedBlackTree.BinaryNode> i = this.iterator();
			while(i.hasNext()){
				aList.add( i.next());
		}
			return aList;
	}
	
	/** the toArray() method returns an array of elements in order 
	*/
	public Object[] toArray(){
		return toArrayList().toArray();
	}
	
	/** the iterator() method returns an preorder iterator
	*/
	public Iterator<FlexRedBlackTree.BinaryNode> iterator(){
		return new PreOrderIterator();
	}
	
	
	/** the insert method add new elements to the tree in order and increments the size
	*/
	public boolean insert(T element){
		if(element==null){throw new IllegalArgumentException();}
		if(root==null){
			root = new BinaryNode(element);
			size += 1;
			root.color = Color.BLACK;
			return true;}
		MyBoolean b = new MyBoolean();
		root.insert(element,b,null,null,null);
		if(b.getValue()){
			size += 1;
			modCount++;
		}
		root.setBlack();
		return b.getValue();
	}

	/**
	 *  the remove method removes an element from the tree and it will return true 
	 *  if the element was removed and false if not removed 
	 */
	public boolean remove(T element){ 
		if (element == null){
			throw new IllegalArgumentException();
		}
		if (this.isEmpty()){
			return false;
		}
		MyBoolean b = new MyBoolean();
		root.removeStep1(element,b);
		if(b.getValue()){
			modCount++;
			size--;
		}
		if(root!=null){root.setBlack();}
		return b.getValue(); 
	}
	
	/**
	 * the getRotationCount() method will return the number of rotations 
	 * that occured during an insert or remove in order to rebalance the tree 
	 */
	public int getRotationCount(){
		return rotationCount;
	}
	
	public class BinaryNode{
		private T element; // this is the generic item being stored in the node and tree 
		private BinaryNode leftChild; // this is the left child of the node
		private BinaryNode rightChild; // this is the right child of the node
		private Color color;
		
		/** this constructor instantiates the BinaryNode class with a generic element
		*/
		private BinaryNode(T element){
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;	
			this.color = Color.RED;
		}
		
		public void setBlack(){
			color = Color.BLACK;
		}
		
		public Color getColor(){
			return this.color;
		}
		
		public T getElement(){
			return  this.element;
		}
		
		public BinaryNode getLeftChild(){
			return leftChild;
		}
		public BinaryNode getRightChild(){
			return rightChild;
		}
		
		private boolean checkBlackWithTwoRedChildrenCondition(){
			if(this.leftChild!=null && this.rightChild!=null){
				if(this.color.equals(Color.BLACK)){
					if(this.leftChild.color.equals(Color.RED)&&this.rightChild.color.equals(Color.RED)){
						swapBLackWithTwoRedChildrenColors();
						return true;
					}
				}
			}
			return false;
		}
		
		private void swapBLackWithTwoRedChildrenColors(){
			this.color = Color.RED;
			this.leftChild.color = Color.BLACK;
			this.rightChild.color = Color.BLACK;
		}
		
		private void insert(T addition, MyBoolean b, BinaryNode p, BinaryNode gp, BinaryNode ggp){
			if(checkBlackWithTwoRedChildrenCondition()){
				if(p != null){
					if(p.color.equals(Color.RED)){
							gp.whatRotation(ggp);
					}
			}
			}
			int compareValue = c.compare(addition, element);
			if(compareValue==0){
				b.setFalse();
			}
			else if(compareValue==-1){ 
				if(leftChild!=null){leftChild.insert(addition, b, this,p,gp);}
				else {
					leftChild = new BinaryNode(addition);
					if(this.color.equals(Color.RED)){
						if(p != null){
							p.whatRotation(gp);
						}
					}
					}
			}
			else{
				if(rightChild!=null){rightChild.insert(addition, b, this, p, gp);}
				else {
					rightChild = new BinaryNode(addition);
					if(this.color.equals(Color.RED)){
						if(p != null){
							p.whatRotation(gp);
						}
					}
					}
			}
		}

		private void removeStep1(T removeElement, MyBoolean b){	
			int compareTo = c.compare(removeElement, element);
			BinaryNode sibling;
			BinaryNode X;
			if(compareTo==-1){
				X = leftChild;
				sibling = rightChild;
			}
			else if(compareTo==1){
				X = rightChild;
				sibling = leftChild;
			}
			else{
				if(leftChild==null&&rightChild==null){
					root = null;
					return;
				}
				X = root;
				sibling = null;
			}
			if(isLeaf()&&compareTo!=0){
				b.setFalse();
				return;
			}
			if((leftChild==null || leftChild.color.equals(Color.BLACK))&&(rightChild==null || rightChild.color.equals(Color.BLACK))){
				root.color = Color.RED;
				if(compareTo==0){
					removeStep3(removeElement,b,X,sibling,null);
				}
				else {removeStep2(removeElement,b,X,sibling,null);}
			}
			else {
				removeStep2B(removeElement,b,root,null,null);
			}
		}
		
		private void removeStep2(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			if(leftChild==null&&rightChild==null){b.setFalse();return;}
			if((X.leftChild==null||X.leftChild.color.equals(Color.BLACK))&&(X.rightChild==null||X.rightChild.color.equals(Color.BLACK))){
				removeStep2A(removeElement,b,X,sibling,p);
			}
			else if((X.leftChild!=null&&X.leftChild.color.equals(Color.RED))||(X.rightChild!=null&&X.rightChild.color.equals(Color.RED))){
				removeStep2B(removeElement,b,X,sibling,p);
			}
		}
		
		private void removeStep2A(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			if(sibling!=null){
				if(isXLeftChild(X)){
					if(sibling.rightChild!=null&&sibling.rightChild.color.equals(Color.RED)){
						removeStep2A3(removeElement,b,X,sibling,p);
					}
					else if(sibling.leftChild!=null&&sibling.leftChild.color.equals(Color.RED)){
						
						removeStep2A2(removeElement,b,X,sibling,p);
					}
					else removeStep2A1(removeElement,b,X,sibling,p);
				}
				else{
					if(sibling.leftChild!=null&&sibling.leftChild.color.equals(Color.RED)){
						removeStep2A3(removeElement,b,X,sibling,p);
					}
					else if(sibling.rightChild!=null&&sibling.rightChild.color.equals(Color.RED)){
						
						removeStep2A2(removeElement,b,X,sibling,p);
					}
					else removeStep2A1(removeElement,b,X,sibling,p);
				}
			}
			else {
				if(X.isLeaf()){
					if(isXLeftChild(X)){
						leftChild = null;
					}
					else rightChild = null;
				}
			}
		}
		
		private void removeStep2A1(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			flipColor();
			X.flipColor();
			sibling.flipColor();
			int compareTo = c.compare(removeElement, X.element);
			if(compareTo==0){
				removeStep3(removeElement,b,X,sibling,p);
			}
			else if(compareTo==-1){
				if(X.leftChild==null){
					b.setFalse();
					return;}
				else {
					X.removeStep2(removeElement, b, X.leftChild, X.rightChild,this);
				}
			}
			else {
				if(X.rightChild==null){
					b.setFalse();
					return;}
				else {
					X.removeStep2(removeElement, b, X.rightChild, X.leftChild,this);
				}
			}
		}
		
		private void removeStep2A2(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			X.flipColor();
			flipColor();
			if(isXLeftChild(X)){
				p=doubleRotateRightChild(p);
			}
			else{
				p=doubleRotateLeftChild(p);
			}
			int compareTo = c.compare(removeElement, X.element);
			if(compareTo==0){
				removeStep3(removeElement,b,X,sibling,p);
			}
			else if(compareTo==-1){
				if(X.leftChild==null){
					b.setFalse();
					return;}
				else X.removeStep2(removeElement, b, X.leftChild, X.rightChild,this);
			}
			else{
				if(X.rightChild==null){
					b.setFalse();
					return;}
				else X.removeStep2(removeElement, b, X.rightChild, X.leftChild,this);
			}
		}
		
		private void removeStep2A3(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			X.flipColor();
			flipColor(); 
			if(sibling!=null){sibling.flipColor();}
			if(isXLeftChild(X)){
				sibling.rightChild.flipColor();
				singleRotateRightChild(p);
			}
			else{
				sibling.leftChild.flipColor();
				singleRotateLeftChild(p);
			}
			int compareTo = c.compare(removeElement, X.element);
			if(compareTo==0){
				removeStep3(removeElement,b,X,sibling,p);
			}
			else if(compareTo==-1){
				if(X.leftChild==null){
					b.setFalse();
					return;}
				else X.removeStep2(removeElement, b, X.leftChild, X.rightChild,this);
			}
			else{
				if(X.rightChild==null){
					b.setFalse();
					return;}
				else X.removeStep2(removeElement, b, X.rightChild, X.leftChild,this);
			}
		}
		
		private void removeStep2B(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			if(X==null) {
				b.setFalse();
				return;
			}
			int compareTo = c.compare(removeElement, X.element);
			if(compareTo==0){
				removeStep3(removeElement,b,X,sibling,p);
			}
			else if(compareTo==-1){
				if(X.leftChild==null){
					b.setFalse();
					return;
				}
				else if(X.leftChild.color.equals(Color.RED)){
					X.removeStep2B1(removeElement,b,X.leftChild,X.rightChild,this);
				}
				else {
					X.removeStep2B2(removeElement,b,X.leftChild,X.rightChild,this);
				}
			}
			else {
				if(X.rightChild==null){
					b.setFalse();
					return;
				}
				else if(X.rightChild.color.equals(Color.RED)){
					X.removeStep2B1(removeElement,b,X.rightChild,X.leftChild,this);
				}
				else {
					X.removeStep2B2(removeElement,b,X.rightChild,X.leftChild,this); 
				}
			}
		}
		
		private void removeStep2B1(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			int compareTo = c.compare(removeElement, X.element);
			if(compareTo==0){
				removeStep3(removeElement,b,X,sibling,p);
			}
			else if(compareTo==-1){
				if(X.leftChild==null){
					b.setFalse();
				}
				else {
					X.removeStep2(removeElement,b,X.leftChild,X.rightChild,this);
				}
			}
			else {
				if(X.rightChild==null){
					b.setFalse();
				}
				else {
					X.removeStep2(removeElement,b,X.rightChild,X.leftChild,this);
				}
			}
		}
		
		private void removeStep2B2(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			flipColor();
			if(sibling!=null){
				sibling.flipColor();
			}
			if(isXLeftChild(X)){
				if(p.element.equals(element)){
					singleRotateRightChild(null);
				}
				else singleRotateRightChild(p);
				removeStep2(removeElement,b,X,rightChild,sibling);
			}
			else {
				if(p.element.equals(element)){
					singleRotateLeftChild(null);
				}
				else singleRotateLeftChild(p);
				removeStep2(removeElement,b,X,leftChild.rightChild,sibling);
			}
			
		}

		private void removeStep3(T removeElement, MyBoolean b,BinaryNode X,BinaryNode sibling,BinaryNode p){
			boolean isXLC = isXLeftChild(X);
			if(X.isLeaf()){
				if(isXLC){
					leftChild = null;
				}
				else {rightChild=null;}
			}
			else if(X.leftChild!=null&&X.rightChild!=null){
				BinaryNode biggestLeftNode = X.leftChild.getBiggestNodeToLeft();
				if(X.color.equals(Color.RED)){
					X.element = biggestLeftNode.element;
					X.removeStep2(X.element, b, X.leftChild, X.rightChild,this);
				}
				else{
					removeStep2B(biggestLeftNode.element, b, X, sibling,p);
					X.element = biggestLeftNode.element;
				}
			}
			else {
				if(X.element.equals(root.element)){
					if(X.leftChild!=null){
						root = X.leftChild;
					}
					else{
						root = X.rightChild;
					}
				}
				else if(isXLC){
					if(X.color.equals(Color.BLACK)){
						if(X.leftChild!=null){
							X.leftChild.flipColor();
						}
						else X.rightChild.flipColor();
					}
					if(X.leftChild!=null){
						leftChild = X.leftChild;
					}
					else {leftChild = X.rightChild;}
				}
				else {
					if(X.color.equals(Color.BLACK)){
						if(X.leftChild!=null){
							X.leftChild.flipColor();
						}
						else X.rightChild.flipColor();
					}
					if(X.leftChild!=null){
						rightChild = X.leftChild;
					}
					else {rightChild = X.rightChild;}
				}
			}
		}
		
		private boolean isXLeftChild(BinaryNode X){
			if(X!=null&&leftChild!=null&&X.element.equals(leftChild.element)){
				return true;
			}
			return false;
		}
		
		private boolean isLeaf(){
			return (this.leftChild==null&&this.rightChild==null);
		}
		
		private BinaryNode getBiggestNodeToLeft(){
			if(rightChild!=null){
				return rightChild.getBiggestNodeToLeft();
			}
			return this;
		}
		
		private void whatRotation(BinaryNode gp){
			if(leftChild != null && leftChild.leftChild != null && leftChild.color.equals(Color.RED) && leftChild.leftChild.color.equals(Color.RED)){
				flipColor();
				leftChild.flipColor();
				singleRotateLeftChild(gp);	
			}
			else if(rightChild != null && rightChild.rightChild != null && rightChild.color.equals(Color.RED) && rightChild.rightChild.color.equals(Color.RED)){
				flipColor();
				rightChild.flipColor();
				singleRotateRightChild(gp);
			}
			else if(leftChild!=null&&leftChild.rightChild!=null && leftChild.color.equals(Color.RED)&&leftChild.rightChild.color.equals(Color.RED)){
				flipColor();
				leftChild.rightChild.flipColor();
				doubleRotateLeftChild(gp);
			}
			else if(rightChild!=null&&rightChild.leftChild!=null && rightChild.color.equals(Color.RED)&&rightChild.leftChild.color.equals(Color.RED)){
				flipColor();
				rightChild.leftChild.flipColor();
				doubleRotateRightChild(gp);
			}
		}
		
		private void singleRotateRightChild(BinaryNode p){
			rotationCount +=1;
			BinaryNode temp = this.rightChild;
			this.rightChild = temp.leftChild;
			temp.leftChild = this;
			if(p==null){
				root = temp;
			}
			else {
				if(c.compare(temp.element, p.element)==-1){
					p.leftChild = temp;
				}
				else {
					p.rightChild = temp;
				}
				
			}
		}
		
		private void singleRotateLeftChild(BinaryNode p){
			rotationCount +=1;
			BinaryNode temp = this.leftChild;
			this.leftChild = temp.rightChild;
			temp.rightChild = this;
			if(p==null){
				root = temp;
			}
			else{
				if(c.compare(temp.element, p.element)==-1){
					p.leftChild = temp;
				}
				else{
					p.rightChild = temp;
				}
				
			}
		}
		
		private BinaryNode doubleRotateRightChild(BinaryNode p){
			rotationCount +=1;
			BinaryNode temp = rightChild.leftChild;
			rightChild.leftChild = temp.rightChild;
			temp.rightChild = this.rightChild;
			this.rightChild = temp;
			singleRotateRightChild(p);
			return this;
		}
		
		private BinaryNode doubleRotateLeftChild(BinaryNode p){
			rotationCount +=1;
			BinaryNode temp = leftChild.rightChild;
			leftChild.rightChild = temp.leftChild;
			temp.leftChild = this.leftChild;
			this.leftChild = temp;
			singleRotateLeftChild(p);
			return this;
		}
			
		private void flipColor(){
			if(this.color.equals(Color.RED)){
				this.color = Color.BLACK;
			}
			else{
				this.color = Color.RED;
			}
		}
		
		// the height() method recursively finds the height of the node
		private int height(){
			int leftHeight = 0;
			int rightHeight = 0;
			if(this.leftChild!=null){
				leftHeight = leftChild.height() + 1;
			}
			if(this.rightChild!=null){
				rightHeight = rightChild.height() + 1;
			}
			if(leftHeight > rightHeight){
				return leftHeight;
			}else 
				return rightHeight;
		}
	}
	
	
	private class MyBoolean{
		boolean value = true;
		
		private boolean getValue(){
			return value;
		}
		
		private void setFalse(){
			value = false;
		}
	}
	
	private class PreOrderIterator implements Iterator<FlexRedBlackTree.BinaryNode>{
		/** this is the root of the tree in order to know where to start
		// this is a stack used to put elements on to find the first element 
		// of the iterator
		*/
		Stack<BinaryNode> s = new Stack<BinaryNode>();  
		BinaryNode currentNode;
		int iModCount;
				
		/** this constructor insansiates the PreOrderIterator class with a 
		* binaryNode root and loads the root node on the stack
		*/
		PreOrderIterator(){
			iModCount = modCount;
			currentNode =null;
			if(root!=null){
			s.push(root);}
		}
		
		/** the next() method returns the next T element in the iterator
		* and throws a no such element exception if there is no next element
		*/
		public FlexRedBlackTree.BinaryNode next(){
			if(!hasNext()){
				throw new NoSuchElementException(); 
			}
			FlexRedBlackTree.BinaryNode temp = s.pop();
			if(temp.rightChild!=null){
				s.push(temp.rightChild);
			}
			if(temp.leftChild!=null){
				s.push(temp.leftChild);
			}
			currentNode = temp;
			return temp;
		}
		
		/** the hasNext() method returns true if there is a next Node and false otherwise
		*
		*/
		public boolean hasNext(){
			if(root==null){
				return false;
			}
			return !(s.empty());
		}

		/** the remove() method removes elements from the iterator
		 * 
		 */
		public void remove() {
			if(currentNode==null){
				throw new IllegalStateException();
			}
			if(modCount!=iModCount){
				throw new ConcurrentModificationException();
			}
			iModCount++;
			FlexRedBlackTree.this.remove(currentNode.element);
			currentNode=null;
		}
	}
	
}