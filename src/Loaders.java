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
// Moses Wong Cheng Qing : Friend : mwong29@wisc.edu
// 		Helped me understand some of the syntax needed for this class 
//
// Nick : CS Tutor: stigsell@wisc.edu
// 		Helped me understand why my printwriter wasn't working
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * this class provides the file reader methods for reading ingredient data files and recipe data files 
 * for the GroceryMatch program
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Nawal Dua
 */

import java.util.*;
import java.io.*;

/**
 * This class provides the file reader methods for reading ingredient data files and recipe data files 
 * for the GroceryMatch program.  Do not change method signatures.  Do implement the missing method bodies.
 */
public class Loaders {

	// DO NOT CHANGE THESE CLASS CONSTANTS
	public static final String GROCERY_FILE_IO_ERROR_MSG = "IOError when loading grocery lists";
	public static final String RECIPE_FILE_IO_ERROR_MSG = "IOError when loading recipes";
	public static final String OUTPUT_FILENAME = "remaining.txt" ;

	/**
	 * 1. Load groceries from file, each line of the file indicates an ingredient and its quantity.
	 * 2. Each ingredient is in the format of "name : quantity", the number of spaces between name, colon and quantity can be any.
	 *    And there may be leading and trailing spaces in a line.
	 * 3. Name of ingredient may have duplicate, this means there may be multiple lines with the same ingredient name.
	 *    If names are duplicated, their quantities should be summed up.
	 * 4. If a line does not match the above mentioned format, ignore the line and continue reading the next line of ingredients.
	 * 5. If an IOException happens, print GROCERY_FILE_IO_ERROR_MSG, and throw the exception.
	 * 
	 * @param filename The name of the file that contains the list of ingredients for the grocery.
	 * @return A grocery list that includes all of the ingredients that were were properly read from the file.
	 * @throws IOException if the filename does not exist, the error msg is displayed and the exception is thrown to calling method
	 */
	public static GroceryList loadGroceriesFromFile(String filename) throws IOException {



		try {
			GroceryList gl = new GroceryList();
			File file = new File(filename);
			Scanner scnr = new Scanner(file);

			while (scnr.hasNext()) {
				String temp = scnr.nextLine().replaceAll(" ", "");

				String[] parts = temp.split(":");
				if (parts.length == 2 && parts[1].matches("^[0-9]+(\\.[0-9]{1})?$")) {
					Double iQuantity = Double.parseDouble(parts[1]);
					String iName = parts[0].toLowerCase();
					Ingredient ing = new Ingredient(iName, iQuantity);
					if (gl.contains(ing)) {
						for(int k = 0; k < gl.size(); k++) {
							if(gl.get(k).getName().toLowerCase().equals(iName)) {
								double newQ = gl.get(k).getQuantity() + iQuantity;
								gl.get(k).setQuantity(newQ);
							}
						}
					}
					else {
						gl.add(ing);
					}
				}

			}
			return gl;
		}
		catch (Exception e) {
			System.out.print(GROCERY_FILE_IO_ERROR_MSG);
			e.printStackTrace();
			return null;

		}



	}

	/**
	 * 1. Load recipes from file, each line of the file indicates a recipe.
	 * 2. Each recipe is in the format "name -> ingredient1-name: ingredient1-quantity, ingredient2-name: ingredient2-quantity"
	 * 3. The number of ingredients in a recipe can be any.
	 * 4. The number of spaces between name and quantity can be any, and there may be leading and trailing spaces.
	 * 5. For simplicity, names of recipes will not have duplication, names of ingredients in a recipe will not have duplication, the format of the recipe is guaranteed to be correct.
	 * 6. Names of ingredients might not be in GroceryList, this means you need to buy this ingredient if you want to use this recipe.
	 * 7. If an IOException happens, print RECIPE_FILE_IO_ERROR_MSG, and throw the exception.
	 * 
	 * @param filename The name of a file containing recipe information.
	 * @return A recipe list containing the recipes read from the named file.
	 * @throws IOException if the filename does not exist, the error msg is displayed and the exception is thrown to calling method
	 */
	public static RecipeList loadRecipesFromFile( String filename ) throws IOException {

		RecipeList rl = new RecipeList();

		try {
			Scanner scnr = new Scanner(new File(filename));

			while (scnr.hasNextLine()) {
				String temp = scnr.nextLine();

				String[] parts = temp.split("->");
				String rName = parts[0];
				String[] parts2 = parts[1].toString().split(",");
				ArrayList<Ingredient> ing = new ArrayList<Ingredient> ();
				for (int i = 0; i < parts2.length; i++) {

					String[] parts3 = parts2[i].toString().split(":");

					String iName = parts3[0];


					Double iQuantity = Double.parseDouble(parts3[1]);
					
					ing.add(new Ingredient(iName, iQuantity));
					



				}
				Recipe rcp = new Recipe(rName, ing);
				rl.add(rcp);

			}
			return rl;
		}
		catch (IOException e) {
			System.out.print(RECIPE_FILE_IO_ERROR_MSG);
			return rl;
		}
	}

	/** 
	 * Write the GroceryList items to the specified file.
	 *
	 * Each ingredient is written to the file in the order that the ingredient is found in the GrocerList
	 * the format for each line is:
	 *
	 * ingredient_name: amount
	 *
	 * @param grocery list of ingredients
	 * @param name of the file to write them to.
	 */
	public static void write(GroceryList groceries, String filename) {

		try {
			File fw = new File(filename);
			PrintWriter pw = new PrintWriter(fw);
			for (int i = 0; i < groceries.size(); i++) {
				pw.println(groceries.get(i).getName() + ":" + " " + groceries.get(i).getQuantity());
			}
			pw.flush();
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.out.print("File not found");
		}
	}


}
