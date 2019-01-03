/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          (project name)
// FILE:             GroceryMatch.java
//
// TEAM:    Indivdual
// Authors: Nawal Dua
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: 
// 
// Cameron Fudeh : Friend : fudeh@wisc.edu 
// 		Helped me understand some of the loop logic in this file
//  
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class contains the main method and provides the methods where the recipes are matched to the ingredients
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Nawal Dua
 */

import java.util.*;
import java.io.*;

/**
 * This is the main class of p1.  It provides the user interface and 
 * matching algorithm to see if there are sufficient ingredients for each recipe.
 *
 * YOU WILL NEED TO COMPLETE METHODS IN THIS CLASS
 */
public class GroceryMatch {

	// DO NOT EDIT DATA MEMBERS (use where needed in GroceryMatch program)
	private static final String RECIPE_NAME_INPUT_PROMPT = "Please input recipe name";
	private static final String SERVING_NUMBER_INPUT_PROMPT = "Please input number of servings";
	private static final String RECIPE_READY = "Dish is ready";

	private static final String RECIPE_NAME_NOT_FOUND_ERROR_MSG = "Recipe not found";
	private static final String SERVING_NUMBER_INVALID_ERROR_MSG = "Please enter positive integer for number of servings";
	private static final String UNRECOGNIZED_COMMAND_ERROR_MSG = "Unrecognized command";

	private GroceryList groceries;
	private RecipeList recipes;


	/** 
	 * Calculate what is the maximum number of servings of this recipe using current GroceryList.
	 * All ingredients must have enough for the maximum number of servings.
	 * The maximum number of servings is 0 if any ingredient is not available in 
	 * sufficient quantity for one serving.
	 *
	 * For example: if an omelet requires 4 eggs and 1 milk
	 *              and there are 10 milk and 12 eggs in groceries,
	 *              then the max servings of omelet recipes is 3 
	 * 
	 * @param recipe The recipe that you want to serve.
	 * @return The maximum number of servings, return 0 if unable to serve a single serving.
	 */
	public Integer calcMaxNumServing( Recipe recipe ) {

		double maxServ = -1 ; //this variable determines the maximum number of servings

		//for each ingredient in the recipe
		for (int i = 0; i < recipe.getIngredients().size(); i++) {

			// initialize the name and quantity of that ingredient
			String rIName = recipe.getIngredients().get(i).getName().replaceAll(" ", "");
			double rIQuantity = recipe.getIngredients().get(i).getQuantity();
			double ratio = 0; // this double, determines how many servings can be made only considering 1 ingredient
			boolean have = false; // this variable represents if we have the ingredient

			// for each ingredient in the grocery list
			for (int j = 0; j < groceries.size(); j++) {

				// initialize the name of the gorcery
				String groceryName = groceries.get(j).getName();

				// if the ingredient in recipe is in the grocery
				if (rIName.equals(groceryName)) {

					// get how many recipes can be made (in terms of only one specific ingredient)
					have = true;
					ratio = groceries.get(j).getQuantity() / rIQuantity;
				}
			}
			// if the recipe ingredient is not in the grocery list return 0
			if (!have) {
				return new Integer(0);
			}
			// else if max serving is -1 (as initialized) or not the limiting factor, adjust the max servings
			else {
				if (maxServ == -1 || maxServ > ratio) {
					maxServ = ratio;
				}
			}
		}
		// if max serving is still negative one, return 0
		if (maxServ == -1) {
			return new Integer(0);
		}
		// return how many servings of that recipe can be made
		return new Integer ((int) maxServ);
	}

	/** 
	 * This method is called when the desired number of servings is greater than
	 * maximum possible number of servings.  
	 * 
	 * This method will print how many more ingredients need to be bought for 
	 * the insufficient ingredients. For sufficient ingredients, do not print.
	 * One ingredient per line, format is "name: quantity", no leading or trailing spaces.
	 * 
	 * @param recipe The recipe that you can not serve.
	 * @param numOfServing The number of servings of the recipe.
	 */    
	public void reportShortage ( Recipe recipe, Integer numOfServing ) {

		// for each ingredient in the recipe 
		for (int i = 0; i < recipe.getIngredients().size(); i++) { 

			boolean hI = false; // this variable represents if we have the ingredient
			//for each ingredient in the GroceryList
			for (int j = 0; j < groceries.size(); j++) {

				//if the ingredient in the recipe is same as ingredient from list
				if (recipe.getIngredients().get(i).getName().replaceAll(" ", "").equals(groceries.get(j).getName().replaceAll(" ", ""))) {
					hI = true;

					//if the ingredient amount in groceries is insufficient,
					if (groceries.get(j).getQuantity() < numOfServing*recipe.getIngredients().get(i).getQuantity()) {

						//print how many more needs to be bought.
						
						// this variable shows the amount of the ingredient that is still needed
						double amountNeeded = numOfServing*recipe.getIngredients().get(i).getQuantity() - groceries.get(j).getQuantity();
						System.out.println(groceries.get(j).getName() + ": " + amountNeeded + "00000");
					}
				}
			}
			//if the ingredient is not found in the GroceryList
			if (!hI) {
				System.out.println(recipe.getIngredients().get(i).getName() + ": " + recipe.getIngredients().get(i).getQuantity() + "00000");
			}
		}
	}

	/**
	 * Reduce the quantities in GroceryList since you have used them for serving.
	 * 
	 * @param recipe The recipe you are serving.
	 * @param numOfServing The number of servings of the recipe.
	 */
	public void updateGroceries( Recipe recipe, Integer numOfServing ) {

		// for each ingredient in the recipe
		for (int i = 0; i < recipe.getIngredients().size(); i++) {

			//for each ingredient in the grocery list
			for (int j = 0; j < groceries.size(); j++) {

				//if the ingredient in the recipe is same as ingredient from list
				if (recipe.getIngredients().get(i).getName().replaceAll(" ", "").equals(groceries.get(j).getName().replaceAll(" ", ""))) {

					//deduct the recipe quantity times num servings from the grocery list ingredient quantity
					double remaining = groceries.get(j).getQuantity() - (numOfServing*recipe.getIngredients().get(i).getQuantity());
					groceries.get(j).setQuantity(remaining);

					//break out of inner loop to get to next recipe ingredient
					break;
				}
			}
		}
	}

	/**
	 * Handle the command when you try to (U)se a recipe.
	 * Input the recipe name and the number of servings, see if it is able to serve using the current GroceryList.
	 *     (1) If able to serve, update the quantities in GroceryList, and print a serving success message.
	 *     (2) If unable to serve, do not update the quantities in GroceryList, and do print the ingredients need to be bought.
	 *  
	 * @param stdin The scanner for input.
	 */
	public void handleUse(Scanner stdin){


		// Get recipe to make from user input
		System.out.println(RECIPE_NAME_INPUT_PROMPT);
		String recipe = stdin.nextLine().replaceAll(" ", "");
		// If recipe name is not in the recipe list, display RECIPE NAME NOT FOUND MESSAGE and return
		boolean flg = true; // this is a flag variable to help with the following if statements
		if(flg) {
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).getRecipeName().replaceAll(" ", "").equals(recipe)) {
					flg = false;
				}
			}
		}
		if(flg) { 
			System.out.println(RECIPE_NAME_NOT_FOUND_ERROR_MSG);
			return;
		}


		// Find recipe from the recipe list
		int recipePos = 0; // this shows the position of the recipe
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).getRecipeName().replaceAll(" ", "").equals(recipe)) {
				recipePos = i;
			}
		}
		// Get number of servings from user input (positive integer only)
		System.out.println(SERVING_NUMBER_INPUT_PROMPT);
		String sServings = stdin.nextLine();

		if (!sServings.matches("\\d+$")) {
			System.out.println(SERVING_NUMBER_INVALID_ERROR_MSG);
			return;
		}
		Integer servings = Integer.parseInt(sServings);


		// Calculate the maximum number of serving using current groceries.
		int maxServing = calcMaxNumServing(recipes.get(recipePos));

		// If the max number of servings is less than number of servings asked for, report shortage, do not update grocery
		// Otherwise, display RECIPE READY MESSAGE and update GroceryList
		if (maxServing < servings) {
			reportShortage (recipes.get(recipePos), servings);
		}
		else {
			System.out.println(RECIPE_READY);
			updateGroceries(recipes.get(recipePos), servings);
		}

	}

	/**
	 * Print all the ingredient names and quantities in a GroceryList. One ingredient each line.
	 * Do not sort the ingredients.  Display in the order added in the list.
	 *
	 *  name1: quantity1
	 *  name2: quantity2
	 *  name3: quantity3
	 *  name4: quantity4
	 * 
	 * @param groceries The GroceryList you want to print.
	 */
	public static void print( GroceryList groceries ) {

		// make new iterator
		Iterator<Ingredient> itr = groceries.iterator();

		// while there are still items in the grocerylist
		while(itr.hasNext()) {

			// get the item and print out the name and quantity
			Ingredient ing = itr.next();
			System.out.println(ing.getName() + ": " + ing.getQuantity() + "00000");

		}
	}

	/**
	 * Print all the recipes in a RecipeList. One recipe each line.
	 * Do not sort the recipes.  Display recipes in the order they were added to the list.
	 * Display ingredients in the order they were added to the recipe's ingredients.
	 * 
	 *  Output Format Example:
	 *  omelet -> milk: 1, eggs: 4
	 *  recipeName1 -> ingredient1: quantity1, ingredient2: quantity2, ...
	 *  recipeName2 -> ingredient1: quantity1, ingredient2: quantity2, ...
	 * 
	 * @param recipes The RecipeList that contains the recipes that you want to print.
	 */
	public static void print(RecipeList recipes) {

		// get a new iterator
		Iterator<Recipe> itr = recipes.iterator();

		// while there are still items in the iterator
		while(itr.hasNext()) {
			Recipe rsp = itr.next();

			// print out the name of the recipe
			System.out.print(rsp.getRecipeName() + " -> ");

			// print out all the names and quantities of the ingredients needed to make the recipe
			for (int i = 0; i < rsp.getIngredients().size(); i++) {
				Ingredient ing2 = rsp.getIngredients().get(i);
				System.out.print(ing2.getName() + ": " + ing2.getQuantity());
				if( i == rsp.getIngredients().size() - 1) {
					System.out.println();
				}
				else {
					System.out.print(", ");
				}
			}
		}
	}

	/** DO NOT EDIT THIS METHOD
	 * Handle the command when you try to show how many servings are possible.
	 * For each recipe in RecipeList, print the maximum number of servings using the current GroceryList.
	 * One recipe per line, format is "recipe-name: max-num-of-serving", no leading or trailing spaces.
	 * DO NOT EDIT THIS METHOD
	 */
	public void handleShow() {
		Iterator<Recipe> itr = recipes.iterator();
		while ( itr.hasNext() ) {
			Recipe recipe = itr.next();
			Integer maxNumServing = calcMaxNumServing(recipe);
			System.out.println( String.format("%s: %d", recipe.getRecipeName(), maxNumServing) );
		}
	}

	/** DO NOT EDIT THIS METHOD
	 * Main loop of GroceryMatch.
	 * The main loop accept input commands, execute them, and print results.
	 * The main loop accepts three types of commands:
	 * 
	 *   (1) q : Save current groceries to file and quit the program.
	 *   
	 *   (2) s : For all recipes, show how many servings are possible using the current GroceryList.
	 *   
	 *   (3) u : Use a recipe.
	 *   
	 * For other commands, print UNRECOGNIZED_COMMAND_ERROR and ignore.
	 * DO NOT EDIT THIS METHOD
	 * 
	 * @param stdin The Scanner for input.
	 */
	public void mainLoop(Scanner stdin) {
		String command = "";
		while( ! command.equalsIgnoreCase("q") ) {
			System.out.println("(s)ervings, (u)se, (q)uit? ");
			command = stdin.nextLine().trim();
			switch ( command ) {
			case "s": handleShow(); break;
			case "u": handleUse(stdin); break;
			case "q": Loaders.write(groceries,Loaders.OUTPUT_FILENAME); break;
			default: 
				System.out.println(UNRECOGNIZED_COMMAND_ERROR_MSG);
			}
		}
	}

	/** DO NOT EDIT THIS METHOD
	 * This method will initialize groceries and recipes.
	 * Return false when there is IOException.
	 * 
	 * @param groceryFile filename of groceries
	 * @param recipeFile filename of recipes
	 * @return Return true if GroceryList and RecipeList are successfully loaded. Return false if there are Exceptions.
	 */
	public Boolean initialize( String groceryFile, String recipeFile ) {
		try {
			groceries = Loaders.loadGroceriesFromFile(groceryFile);
			print(groceries);
			recipes = Loaders.loadRecipesFromFile(recipeFile);
			print(recipes);
			return true;
		} catch ( Exception e ) {
			return false;
		}
	}

	/** DO NOT EDIT CONSTRUCTOR */
	public GroceryMatch(){
		groceries = new GroceryList();
		recipes = new RecipeList();
	}

	/** DO NOT EDIT THIS METHOD
	 * The main method initializes the GroceryMatch object
	 * and call the initialize method before starting the main menu loop.
	 */
	public static void main(String[] args) throws IOException{
		Scanner stdin = new Scanner(System.in);
		GroceryMatch gm = new GroceryMatch();
		try {
			if ( ! gm.initialize(args[0],args[1]) ) {
				return;
			}
			gm.mainLoop(stdin);
		} catch (Exception e) {
			System.out.println("Usage: java GroceryMatch ingredientFileName recipeFileName");
		}
	}
}
