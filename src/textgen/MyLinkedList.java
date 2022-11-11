package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		this.head = new LLNode<E>(null);
		this.tail = new LLNode<E>(null);
		this.head.next = this.tail;
		this.tail.prev = this.head;
		this.size = 0;		
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("The added element is null.");
		}
		LLNode<E> newItem = new LLNode<E>(element);
		newItem.prev = tail.prev;
//		System.out.println("Line 37 work!");
		newItem.prev.next = newItem;
//		System.out.println("Line 39 work!");
		newItem.next = tail;
		tail.prev = newItem;
		
		this.size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if ((index < 0) || (index >= this.size)) {
			throw new IndexOutOfBoundsException();
		}
		
		int i = 0;
		LLNode<E> searchHead = this.head.next; // first node is dummy node
		while (i < index) {
			searchHead = searchHead.next;
			i++;
		}
		
		return searchHead.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		//  Null elements are not allowed in the list so if someone tries to insert one you should throw a NullPointerException.
		if (element == null) {
			throw new NullPointerException("Argument should not be null");
		}
		
		// Corner Case: adding to the end
		if (index == this.size) {
			this.add(element);
			return;
		}
		
		// You will want to throw an IndexOutOfBoundsException if the index provided is not reasonable for inserting an element.
		if ((index < 0) || (index > this.size)) {
			throw new IndexOutOfBoundsException("Argument should not be out of bounds");
		}
		
		// Normal: find the object at given index
		int i = 0;
		LLNode<E> iterItem = this.head.next; // first node is dummy node
		while (i < index) {
			iterItem = iterItem.next;
			i++;
		}
		
		// Insert element at that index
		LLNode<E> newItem = new LLNode<E>(element);		
		newItem.prev = iterItem.prev;
		iterItem.prev.next = newItem;
		newItem.next = iterItem;
		iterItem.prev = newItem;
		this.size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		// You will want to throw an IndexOutOfBoundsException if the index provided is not reasonable for removing an element.
		if ((index < 0) || (index >= this.size)) {
			throw new IndexOutOfBoundsException("Argument should not be out of bounds");
		}
		
		// Normal: find the object at given index
		int i = 0;
		LLNode<E> iterItem = this.head.next; // first node is dummy node
		while (i < index) {
			iterItem = iterItem.next;
			i++;
		}
		
		E removedItem = iterItem.data;
		iterItem.prev.next = iterItem.next;
		iterItem.next.prev = iterItem.prev;
		this.size--;
		return removedItem;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("Argument should not be null");
		}
		
		// Case: adding to the end
		if (index == this.size) {
			E replacedElement = this.tail.data; 
			this.tail.data = element;
			return replacedElement;
		}
		
		// You will want to throw an IndexOutOfBoundsException if the index provided is not reasonable for inserting an element.
		if ((index < 0) || (index > this.size)) {
			throw new IndexOutOfBoundsException("Argument should not be out of bounds");
		}
		
		// Normal: find the item at given index
		int i = 0;
		LLNode<E> iterItem = this.head.next; // first node is dummy node
		while (i < index) {
			iterItem = iterItem.next;
			i++;
		}
		
		// Set value at that item
		E iterItemOldData = iterItem.data; 
		iterItem.data = element;
		return iterItemOldData; 
	}   
	
	public static void main (String[] args) {
		MyLinkedList<String> shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		
		shortList.add(1, "C");
		shortList.add(3, "D");
		
		shortList.set(2, "E");
		shortList.remove(2);
		
//		LLNode<String> iter = shortList.head;
//		while (iter != null) {
////			System.out.println(iter.data);
//			System.out.println(iter.toString());
//			iter = iter.next;
//		}
		System.out.println(shortList);
		
		System.out.println(shortList.size);
	}
	
//	public String toString() {
//		LLNode<E> iter = this.head.next;
//		StringBuilder representationString = new StringBuilder("[ ");
//		while (iter.next != null) {
//			representationString.append(iter.toString() + ", ");
//			iter = iter.next;
//		}
//		
//		if (this.size != 0) {
//			representationString.delete(representationString.length() - 2, representationString.length());
//			representationString.append("]");
//		} else {
//			representationString = new StringBuilder("[]");
//		}
//		
//		return representationString.toString();
//	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public String toString() {
		return String.valueOf(data);
	}
}
