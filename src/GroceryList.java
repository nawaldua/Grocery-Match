/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          (project name)
// FILE:             GroceryMatch.java
//
// TEAM:    Indivdual
// Authors: Nawal Dua
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: avoid web searches to solve your problems, but if you do 
// search, be sure to include Web URLs and description of 
// of any information you find. 
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class implements the ListADT containing a ingredient object.
 * It has methods that can be used on an ingredientlist
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Nawal Dua
 */

import java.util.ArrayList;
import java.util.Iterator;


public class GroceryList implements ListADT<Ingredient>  {
	
	
	private ArrayList<Ingredient> items; //
	
	
	//Constructor
	public GroceryList() {
		this.items =  new ArrayList<Ingredient> ();
	}
	
	// calling the iterator
	public Iterator<Ingredient> iterator(){
    	return this.items.iterator();
    }
	
	/**
	 * adds an ingredient item to the ingredient list
	 * @param (item) The ingredient that needs to be added to the grocery list
	 * @return void
	 */
    public void add(Ingredient item) {
    	this.items.add(item);
    }
  
    // add item at position pos in the List, moving the items originally in positions 
    // pos through size()-1 one place to the right to make room 
    // (error if pos is less than 0 or greater than size())
    // throw IndexOutOfBoundsException if pos is less than zero or greater than size
    /**
	 * adds an ingredient item to the ingredient list at a specific position
	 * @param (item) The ingredient that needs to be added to the grocery list
	 * @return void
	 */
    public void add(int pos, Ingredient item) {
    	if (items.size() <= pos || pos < 0) {
    		throw new IndexOutOfBoundsException();
    	}
    	else {
    		items.add(pos,item);
    	}
    }

    // return true if item is in the List (i.e., there is an item x in the List such that x.equals(item))
    /**
	 * checks if an ingredient is already in the grocerylist
	 * @param (item) The ingredient being checked for
	 * @return void
	 */
    public boolean contains(Ingredient item) {
    	if (item ==  null) {
    		return false;
    	}
    	else {
    		for (int i = 0; i < items.size(); i++) {
    			if (items.get(i).getName().equals(item.getName())) {
    				return true;
    			}
    		}
    	}
		return false;
    }

    /**
	 * finds the size of the grocerylist
	 * @param 
	 * @return (int) the size of the grocerylist
	 */
    // return the number of items in the List.
    public int size() {
    	return items.size();
    }
    
    
    /**
	 * finds out if the grocerylist is empty
	 * @param 
	 * @return (boolean) true if the grocery list is empty
	 */
    // return if the List is empty.
    public boolean isEmpty() {
    	if (items.size() == 0) {
    		return true;
    	}
    	else return false;
    }
    
    /**
	 * returns an ingredient from the grocery list at a specific position
	 * @param (pos) the position in the grocery list
	 * @return the ingredient item at position pos
	 */
    //  return the item at position pos in the List 
    // throw IndexOutOfBoundsException if pos is less than zero or greater than or equal to size
    public Ingredient get(int pos) {
    	if (this.items.size() < pos || pos < 0) {
    		throw new IndexOutOfBoundsException();
    	}
    	else {
    		return this.items.get(pos);
    	}
    			
    }

    
    /**
	 * removes and returns an ingredient item at a specific position from the grocery list 
	 * @param (pos) The position of the ingredient that needs to be removed
	 * @return (ingredient) The ingredient that was removed
	 */
    // remove and return the item at position pos in the List, 
    // shifting the items originally in positions pos+1 through size()-1 
    // one place to the left to fill in the gap
    // throw IndexOutOfBoundsException if pos is less than zero or greater than or equal to size
    public Ingredient remove(int pos) {
    	if (items.size() <= pos || pos < 0) {
    		throw new IndexOutOfBoundsException();
    	}
    	else {
    		Ingredient i = items.get(pos);
    		items.remove(pos);
    		return i;
    	}
    }
}
