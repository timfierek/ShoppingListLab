import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A simple console based shopping list application
 * 
 * @author Tim Fierek
 *
 */
public class GuenthersMarket {
	
	private static Scanner in;
	private static Map<String, Double> items = new HashMap<>();
	private static List<String> orderNames = new ArrayList<>();
	private static List<Double> orderPrices = new ArrayList<>();
	private static List<Integer> orderQuantity = new ArrayList<>();

	
	public static void main(String[] args) {
		// Fill map and print initial menu
		in = new Scanner(System.in);
		System.out.println("Welcome to Tim's Market!\n");
		fillItemsMap();
		printMenu();

		// Continue adding to cart until user ends it
		String item;
		Boolean loop = true;

		while (loop) {
			item = collectChoice();
			
			// Print items added to cart and add them to array lists 
			System.out.println("Adding " + item + " to cart at $" + items.get(item));
			if(orderNames.indexOf(item) == -1) {
				orderNames.add(item);
				orderPrices.add(items.get(item));
				orderQuantity.add(1);
			}
			else {
				int curItem = orderNames.indexOf(item);
				orderQuantity.set(curItem, (orderQuantity.get(curItem)) + 1);
			}

			// Figure out if user would like to continue
			loop = consoleYesOrNo();
		}
		
		System.out.println("\nThanks for your order!");
		if(orderNames.size() != 0) {
			System.out.println("Here's what you got:");
			for(int i = 0; i < orderNames.size(); i++) {
				System.out.printf("%-16s%-1s%-5.2f%-4s%n", orderNames.get(i), "$", orderPrices.get(i),
						("(" + orderQuantity.get(i) + ")"));
			}
		}
		
		System.out.printf("%s%-5.2f%n", "Average price per item in order was $", avgCost());
		
		System.out.println("The most expensive item ordered was " + orderNames.get(findMostExpensive()));
		
		System.out.println("The least expensive item ordered was " + orderNames.get(findLeastExpensive()));
		in.close();

	}
	
	/**
	 * Prompts and collects yes or no answer and converts it to a boolean
	 * 
	 * @return true if user enters "y" or false if user enters "n"
	 */
	private static boolean consoleYesOrNo() {
		System.out.print("\nWould you like to order anything else (y\\n)? ");
		String choice = in.nextLine().toLowerCase();
		while (!choice.equals("n") && !choice.equals("y")) {
			System.out.print("\nInvalid input (please enter \"y\" or \"n\"): ");
			choice = in.nextLine();
		}
		if(choice.equals("y")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Prints prompt and collects and verifys user input for an item from the menu
	 * 
	 * @return the string 
	 */
	private static String collectChoice() {
		String item = "";
		int indexNum = -1;

		System.out.print("\nWhat item would you like to order? (enter a number): ");

		while (!items.containsKey(item)) {
			try {
				indexNum = in.nextInt();
				switch (indexNum) {
				case 1:
					item = "tea";
					break;
				case 2:
					item = "bread";
					break;
				case 3:
					item = "candy";
					break;
				case 4:
					item = "coffee";
					break;
				case 5:
					item = "lunch meat";
					break;
				case 6:
					item = "water";
					break;
				case 7:
					item = "apples";
					break;
				case 8:
					item = "lettuce";
					break;
				default:
					System.out.print("\nItem not found, please enter a number from the menu: ");
				}
			} catch (java.util.InputMismatchException e) {
				System.out.print("\nPlease ensure your input is a number, try again: ");
				in.nextLine();
			}
		}
		in.nextLine();
		return item;
	}

	/**
	 * Simple void method to fill the "items" map with various products
	 */
 	private static void fillItemsMap() {
		items.put("coffee", 2.75);
		items.put("tea", 1.98);
		items.put("bread", 2.49);
		items.put("water", 0.99);
		items.put("lunch meat", 6.99);
		items.put("apples", 0.49);
		items.put("lettuce", 1.05);
		items.put("candy", 3.00);
	}
	
	/**
	 * Simple void method to print out the possible purchase options
	 */
	public static void printMenu() {
		
		System.out.printf("%-3s%-16s%-14s%n","#", "Item", "Price");
		System.out.println("==============================");
		int i = 1;
		
		for(Map.Entry<String, Double> item : items.entrySet()) {
			System.out.printf("%-3s%-16s%1s%-5.2f%n",(i + ")"), item.getKey(), "$", item.getValue());
			i++;
		}
	}
	
	/**
	 * Calculates average cost 
	 * 
	 * @return average cost per item from cart
	 */
	public static double avgCost() {	
		double avg = 0;
		
		for(int i = 0; i < orderPrices.size(); i++) {
			avg += (orderPrices.get(i) * orderQuantity.get(i));
		}
		avg /= getNumItems();
		
		return avg;
	}
	
	/**
	 * finds the number of items orders
	 * 
	 * @return the total quantity of every item ordered by the user
	 */
	public static int getNumItems() {
		int num = 0;
		for(Integer i : orderQuantity) {
			num += i;
		}
		
		return num;
	}
	
	/**
	 * locates the index of the most expensive item ordered.
	 * 
	 * @return the index of the most expensive item ordered
	 */
	public static int findMostExpensive() {
		int mostExpensive = -1;
		double highestPrice = -1.0;
		for(int i = 0; i < orderPrices.size(); i++) {
			if(orderPrices.get(i) > highestPrice) {
				mostExpensive = i;
				highestPrice = orderPrices.get(i);
			}
		}
		
		return mostExpensive;
	}
	
	/**
	 * locates the index of the least expensive item ordered.
	 * 
	 * @return the index of the least expensive item ordered
	 */
	public static int findLeastExpensive() {
		int leastExpensive = -1;
		double lowestPrice = Double.MAX_VALUE;
		for(int i = 0; i < orderPrices.size(); i++) {
			if(orderPrices.get(i) < lowestPrice) {
				leastExpensive = i;
				lowestPrice = orderPrices.get(i);
			}
		}
		
		return leastExpensive;
	}
}
