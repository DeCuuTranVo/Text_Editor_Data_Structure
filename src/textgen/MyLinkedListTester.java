/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
//		System.out.println("shortList " + shortList); // shortList [A, B]
//		System.out.println("emptyList " + emptyList); // emptyList []
//		System.out.println("longerList " + longerList); // longerList [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//		System.out.println("list1 " + list1); // list1 [65, 21, 42]	
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// Case 1. Test when an element is remove in an empty list at unreasonable index -> IndexOutOfBoundsException
		try {
			emptyList.remove(1);
			fail("Check out-of-bound index");
		} catch (IndexOutOfBoundsException e) {
			;
		}
		
		try {
			longerList.remove(-1);
			fail("Check out-of-bound index");
		} catch (IndexOutOfBoundsException e) {
			;
		}
		
		try {
			longerList.remove(longerList.size());
			fail("Check out-of-bound index");
		} catch (IndexOutOfBoundsException e) {
			;
		}
		
		// Case 2. Test when an element is added to an empty list 
		// Case 2.1. Test return value
		int removedItem1 = longerList.remove(0);
		int removedItem2 = longerList.remove(4);
		int removedItem3 = longerList.remove(longerList.size()-1);
		assertEquals("Check removed element at position " + 0, removedItem1, 0);
		assertEquals("Check removed element at position " + 4, removedItem2, 5);
		assertEquals("Check removed element at position " + 9, removedItem3, 9);
		
		// Case 2.2, Test list value
		assertEquals("Check list's size", LONG_LIST_LENGTH -3, longerList.size());
		
		assertEquals("Check list's element at position " + 0, String.valueOf(1), longerList.get(0).toString());
		assertEquals("Check list's element at position " + 1, String.valueOf(2), longerList.get(1).toString());
		assertEquals("Check list's element at position " + 2, String.valueOf(3), longerList.get(2).toString());
		assertEquals("Check list's element at position " + 3, String.valueOf(4), longerList.get(3).toString());
		assertEquals("Check list's element at position " + 4, String.valueOf(6), longerList.get(4).toString());
		
		assertEquals("Check list's element at position " + 5, String.valueOf(7), longerList.get(5).toString());
		assertEquals("Check list's element at position " + 6, String.valueOf(8), longerList.get(6).toString());
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		//  Null elements are not allowed in the list so if someone tries to insert one you should throw a NullPointerException.
		// Be sure to think about what happens when you add an element to an empty list as well as a list with already existing elements. 
		
		// Case 0. Inserted class is different from list's elements' class
		//
		// Case 1. Test null element is inserted in the list -> NullPointerException
		try {
			shortList.add(null);
			fail("Check null element");
		} catch (NullPointerException e) {
			;
		}
		// Case 2. Test when an element is added to an empty list -> check size, check item
		emptyList.add(1);
		assertEquals("Check list's element at position 1", "1", emptyList.get(0).toString());
		assertEquals("Check list's size", 1, emptyList.size());
		
		// Case 3. Test when a list with already existing elements 
		// Check the list have full entry, same elements in order
		longerList.add(10);
		assertEquals("Check list's size", (long) LONG_LIST_LENGTH + 1, (long) longerList.size());
		for (int i = 0; i < longerList.size(); i++) {
			assertEquals("Check list's element at position " + String.valueOf(i), String.valueOf(i), longerList.get(i).toString());
		}
		
//		System.out.println("shortList " + shortList); // shortList [A, B]
//		System.out.println("emptyList " + emptyList); // emptyList []
//		System.out.println("longerList " + longerList); // longerList [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//		System.out.println("list1 " + list1); // list1 [65, 21, 42]	
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("Check empty list size: ", 0, emptyList.size());
		assertEquals("Check longer list size: ", 10, longerList.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		//  Null elements are not allowed in the list so if someone tries to insert one you should throw a NullPointerException.
		
		//  You will want to throw an IndexOutOfBoundsException if the index provided is not reasonable for inserting an element.
		
		// Case 1. Test null element is inserted in the list -> NullPointerException
		try {
			shortList.add(0, null);
			fail("Check null element");
		} catch (NullPointerException e) {
			;
		}
		
		// Case 2. Test when an element is added to an empty list at unreasonable index -> IndexOutOfBoundsException
		try {
			emptyList.add(1, 3);
			fail("Check out-of-bound index");
		} catch (IndexOutOfBoundsException e) {
			;
		}
		
		try {
			longerList.add(-1, 2);
			fail("Check out-of-bound index");
		} catch (IndexOutOfBoundsException e) {
			;
		}
		
		// Case 3. Test when an element is added to an empty list -> check size, check item
		emptyList.add(0, 4);
		assertEquals("Check list's size", 1, emptyList.size());
		assertEquals("Check list's element at position " + 0, "4", emptyList.get(0).toString());

		// Case 4. Test when a list with already existing elements 
		// Check the list have full entry, same elements in order
		longerList.add(0, -10);
		longerList.add(4, 20);
		longerList.add(longerList.size(), 30);
//		System.out.println(longerList);
		
		assertEquals("Check list's size", LONG_LIST_LENGTH + 3, longerList.size());
		
		assertEquals("Check list's element at position " + 0, String.valueOf(-10), longerList.get(0).toString());
		assertEquals("Check list's element at position " + 1, String.valueOf(0), longerList.get(1).toString());
		assertEquals("Check list's element at position " + 2, String.valueOf(1), longerList.get(2).toString());
		assertEquals("Check list's element at position " + 3, String.valueOf(2), longerList.get(3).toString());
		assertEquals("Check list's element at position " + 4, String.valueOf(20), longerList.get(4).toString());
		
		assertEquals("Check list's element at position " + 5, String.valueOf(3), longerList.get(5).toString());
		assertEquals("Check list's element at position " + 6, String.valueOf(4), longerList.get(6).toString());
		assertEquals("Check list's element at position " + 7, String.valueOf(5), longerList.get(7).toString());
		assertEquals("Check list's element at position " + 8, String.valueOf(6), longerList.get(8).toString());
		assertEquals("Check list's element at position " + 9, String.valueOf(7), longerList.get(9).toString());
		
		assertEquals("Check list's element at position " + 10, String.valueOf(8), longerList.get(10).toString());
		assertEquals("Check list's element at position " + 11, String.valueOf(9), longerList.get(11).toString());
		assertEquals("Check list's element at position " + 12, String.valueOf(30), longerList.get(12).toString());

		
//		System.out.println("shortList " + shortList); // shortList [A, B]
//		System.out.println("emptyList " + emptyList); // emptyList []
//		System.out.println("longerList " + longerList); // longerList [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//		System.out.println("list1 " + list1); // list1 [65, 21, 42]	
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		// Case 1. Test null element is set in the list -> NullPointerException
		try {
			shortList.set(0, null);
			fail("Check null element");
		} catch (NullPointerException e) {
			;
		}
		
		// Case 2. Test when an element is set in an empty list at unreasonable index -> IndexOutOfBoundsException
		try {
			emptyList.set(1, 3);
			fail("Check out-of-bound index");
			
			longerList.set(-1, 2);
			fail("Check out-of-bound index");
		} catch (IndexOutOfBoundsException e) {
			;
		}
	    
		// Case 3. Test when a list with already existing elements 
		// Check the list have full entry, same elements in order
		int replacedItem1 = longerList.set(0, -10);
		int replacedItem2 = longerList.set(4, 20);
		int replacedItem3 = longerList.set(longerList.size()-1, 30);
		assertEquals("Check replaced element at position " + 0, replacedItem1, 0);
		assertEquals("Check replaced element at position " + 4, replacedItem2, 4);
		assertEquals("Check replaced element at position " + 9, replacedItem3, 9);
		//	System.out.println(longerList);
		
		assertEquals("Check list's size", LONG_LIST_LENGTH, longerList.size());
		
		assertEquals("Check list's element at position " + 0, String.valueOf(-10), longerList.get(0).toString());
		assertEquals("Check list's element at position " + 1, String.valueOf(1), longerList.get(1).toString());
		assertEquals("Check list's element at position " + 2, String.valueOf(2), longerList.get(2).toString());
		assertEquals("Check list's element at position " + 3, String.valueOf(3), longerList.get(3).toString());
		assertEquals("Check list's element at position " + 4, String.valueOf(20), longerList.get(4).toString());
		
		assertEquals("Check list's element at position " + 5, String.valueOf(5), longerList.get(5).toString());
		assertEquals("Check list's element at position " + 6, String.valueOf(6), longerList.get(6).toString());
		assertEquals("Check list's element at position " + 7, String.valueOf(7), longerList.get(7).toString());
		assertEquals("Check list's element at position " + 8, String.valueOf(8), longerList.get(8).toString());
		assertEquals("Check list's element at position " + 9, String.valueOf(30), longerList.get(9).toString());
		
	}
	
	
	// TODO: Optionally add more test methods.
	
}
