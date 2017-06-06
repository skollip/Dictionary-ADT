package Transpose;

import java.util.NoSuchElementException;

/**
 * This class creates the custom linked list used for keeping track
 * of key value pairs.
 * @author Siddhartha
 *
 * @param <T> The variable type of the list
 */
public class MultiPurposeList<T> {
	
	/** The start of the list */
	private Node head;
	/** The iterator used to traverse the data */
	private Node iterator;
	/** Exception message if there are no more values in a list. */
	public static final String EXP_NO_MORE_VALUES_IN_LIST = "No more values in list.";
	/** Exception message if the item is null when adding to a list. */
	public static final String EXP_LIST_ITEM_NULL = "Item is null";
	/** Exception message if the index is out of bounds in a list. */
	public static final String EXP_INDEX_OUT_OF_BOUNDS = "Index out of bounds.";
	
	
	/**
	 * An internal class that creates the place holders for data
	 * in the linked list.
	 * @author Siddhartha
	 *
	 */
	private class Node {
		public T data;
		public Node next;
		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	/**
	 * Builds MultiPurposeList and sets the head to null
	 */
	public MultiPurposeList() {
		head = null;
	}
	
	/**
	 * Resets the position of the iterator to the head
	 */
	public void resetIterator() {
		iterator = head;
	}
	
	/**
	 * Checks to see if there is another entry in the list
	 * @return true if another entry exists, otherwise false
	 */
	public boolean hasNext() {
		return iterator != null;
	}
	
	/**
	 * Moves the iterator forward one step
	 * @return the object information stored at that location in the list
	 * @throws NoSuchElementException if the iterator is null
	 */
	public T next() {
		if(iterator == null) {
			throw new NoSuchElementException(EXP_NO_MORE_VALUES_IN_LIST);
		}
		T info = iterator.data;
		iterator = iterator.next;
		return info;		
	}
	
	/**
	 * Adds an item to the list
	 * @param position the index of where you want the object to be added
	 * @param data that that will be added to the list
	 * @throws NullPointerException if the parameter is null
	 * @throws IndexOutOfBoundsException if the position is lower than 0 or larger than the size of the list
	 */
	public void addItem(int position, T data) {
		if(data == null) {
			throw new NullPointerException(EXP_LIST_ITEM_NULL); 
		}
		if(position < 0 || position > size()) {
			throw new IndexOutOfBoundsException(EXP_INDEX_OUT_OF_BOUNDS);
		}
		if(position == 0) {
			head = new Node(data, head);
		}
		else if(head != null && position > 0) {
			Node traveler = head;
			while(traveler != null && position > 1) {
				traveler = traveler.next;
				position--;
			}
			if(traveler != null) {
				traveler.next = new Node(data, traveler.next);
			}
		}
		resetIterator();
	}
	
	/**
	 * Status of whether list is empty or not
	 * @return true if list is empty, otherwise false
	 */
	public boolean isEmpty() {
		return head == null ;
	}
	
	/**
	 * Returns an object at the given position in the list
	 * @param position the location of the object of interest
	 * @return the object that is located at the position parameter
	 * @throws IndexOutOfBoundsException if the position is lower than 0 or larger than the size of the list
	 */
	public T lookAtItemN(int position) {
		if(position < 0 || position >= size()) {
			throw new IndexOutOfBoundsException(EXP_INDEX_OUT_OF_BOUNDS);
		}
		Node traveler = head;
		for(int i = 0; i < position; i++) {
			traveler = traveler.next;
		}
		return traveler.data;
	}
	
	/**
	 * Adds an object to the rear of the list
	 * @param the object being added to the rear of the list
	 * @throws NullPointerException if the book parameter is null
	 */
	public void addToRear(T data) {
		if(data == null) {
			throw new NullPointerException(EXP_LIST_ITEM_NULL); 
		}
		if(head == null) {
			head = new Node(data, head);
		} else {
			Node traveler = head;
			while(traveler.next != null) {
				traveler = traveler.next;
			}
			traveler.next = new Node(data, traveler.next);
		}
		resetIterator();
	}
	
	/**
	 * Removes an object from the list
	 * @param position the index of the object that will be removed
	 * @return the object that is removed from the list
	 * @throws IndexOutOfBoundsException if the position is lower than 0 or larger than the size of the list
	 */
	public T remove(int position) {
		if(position < 0 || position >= size()) {
			throw new IndexOutOfBoundsException(EXP_INDEX_OUT_OF_BOUNDS);
		}
		Node current = head;
		Node previous = null;
		while(current != null && position > 0) {
			previous = current;
			current = current.next;
			position--;
		}
		if(current != null) {
			if(current.equals(head)) {
				head = head.next;
			}
			else {
				previous.next = current.next;
			}
			resetIterator();
			return current.data;
		}
		return null;
	}
	
	/**
	 * Moves an object ahead one position in the list
	 * @param position the location of the object that needs to be moved ahead
	 * @throws IndexOutOfBoundsException if the position is lower than 0 or larger than the size of the list
	 */
	public void moveAheadOne(int position) {
		if(position < 0 || position >= size()) {
			throw new IndexOutOfBoundsException(EXP_INDEX_OUT_OF_BOUNDS);
		}
		int psn = position;
		Node current = head;
		Node previous = null;
		while(current != null && psn > 0) {
			previous = current;
			current = current.next;
			psn--;
		}
		if(current != null) {
			if(current.equals(head)) {
				head = current;
			}
			else {
				previous.next = current.next;
				addItem(position - 1, current.data);
			}
		}
	}
	
	/**
	 * Returns the size of the list
	 * @return the size of the list
	 */
	public int size() {
		int size = 0;
		Node s = head;
		while(s != null) {
			size++;
			s = s.next;
		}
		return size;
	}
}
