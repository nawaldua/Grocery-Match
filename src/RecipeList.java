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
 * This class implements the ListADT containing a recipe object.
 * It has methods that can be used on a recipeList
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Nawal Dua
 */

import java.util.ArrayList;
import java.util.Iterator;


public class RecipeList implements ListADT<Recipe> {


	// get the iterator of this List.
	public Iterator<Recipe> iterator(){
		return this.recipes.iterator();
	}

	private ArrayList<Recipe> recipes; ///

	//Constructor
	public RecipeList() {
		this.recipes =  new ArrayList<Recipe> ();
	}


	/**
	 * adds an recipe item to the ingredient list
	 * @param (item) The recipe that needs to be added to the recipe list
	 * @return void
	 */	
	public void add(Recipe item) {
		recipes.add(item);
	}


	/**
	 * adds a recipe item to the recipe list at a specific position
	 * @param (item) The recipe that needs to be added to the grocery list
	 * @return void
	 */
	// add item at position pos in the List, moving the items originally in positions 
	// pos through size()-1 one place to the right to make room 
	// (error if pos is less than 0 or greater than size())
	// throw IndexOutOfBoundsException if pos is less than zero or greater than size
	public void add(int pos, Recipe item) {
		if (recipes.size() <= pos || pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		else {
			recipes.add(pos,item);
		}
	}


	/**
	 * checks if an recipe is already in the recipe list
	 * @param (item) The recipe being checked for
	 * @return void
	 */
	// return true iff item is in the List (i.e., there is an item x in the List such that x.equals(item))
	public boolean contains(Recipe item) {
		if (item ==  null) {
			return false;
		}
		else {
			for (int i = 0; i < size(); i++) {
				if (recipes.get(i).getRecipeName().equals(item.getRecipeName())) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * finds the size of the recipe list
	 * @param 
	 * @return (int) the size of the recipe list
	 */
	// return the number of items in the List.
	public int size() {
		return recipes.size();
	}


	/**
	 * finds out if the recipe list is empty
	 * @param 
	 * @return (boolean) true if the recipe list is empty
	 */
	// return if the List is empty.
	public boolean isEmpty() {
		if (recipes.size() == 0) {
			return true;
		}
		else return false;
	}


	/**
	 * returns an recipe from the recipe list at a specific position
	 * @param (pos) the position in the recipe list
	 * @return the recipe item at position pos
	 */
	//  return the item at position pos in the List 
	// throw IndexOutOfBoundsException if pos is less than zero or greater than or equal to size
	public Recipe get(int pos) {
		if (recipes.size() <= pos || pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		else {
			return recipes.get(pos);
		}
	}


	/**
	 * removes and returns a recipe item at a specific position from the recipe list 
	 * @param (pos) The position of the recipe that needs to be removed
	 * @return (recipe) The recipe that was removed
	 */
	// remove and return the item at position pos in the List, 
	// shifting the items originally in positions pos+1 through size()-1 
	// one place to the left to fill in the gap
	// throw IndexOutOfBoundsException if pos is less than zero or greater than or equal to size
	public Recipe remove(int pos) {
		if (recipes.size() <= pos || pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		else {
			Recipe r = recipes.get(pos);
			recipes.remove(pos);
			return r;
		}
	}  
}
