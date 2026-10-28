/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package foodstoragesystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/**
 *
 * @author guilh
 */
public class FoodStorageSystem {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final FoodQueue foodQueue = new FoodQueue();
    
    public static void main(String[] args) {
        
        // Keeps the program running until the user chooses to exit
        boolean running = true;
        
        System.out.println("Welcome to Food Storage System!");
        
        while (running) {
            
            System.out.println("\n===== FOOD STORAGE MENU =====");
            System.out.println("1. Add food");
            System.out.println("2. Remove first food");
            System.out.println("3. Display first food");
            System.out.println("4. Search food by name");
            System.out.println("5. Display queue size");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    addFood();
                    break;
                    
                case "2":
                    removeFood();
                    break;
                
                case "3":
                    foodQueue.displayFirstFood();
                    break;
                
                case "4":
                    searchFood();
                    break;
                
                case "5":
                    System.out.println("\nFoods in storage: " + foodQueue.getSize() + "/8");
                    break;
                
                case "6":
                    running = false;
                    System.out.println("\nGoodbye!");
                    break;
                
                default:
                    System.out.println("\nInvalid option. Please choose from 1 to 6.");
               
            
            }
        
        
        }
        
        scanner.close();
      
    }
    
    // Collects food information, validates the input and sends the item to the queue
    private static void addFood() {
        
        System.out.println("\nAvailable food types:");
        System.out.println("Burger, Pizza, Fries, Sandwich, Hotdog");
        System.out.println("Enter food name: ");
        
        String name = scanner.nextLine().trim();
        
        // Checks whether the name matches one of the five allowed food types
        if (!isValidFoodName(name)) {
            System.out.println("\nInvalid food type. Please try again.");
            return;
        }
        
        System.out.println("\nEnter weight in grams: ");
        String weightInput = scanner.nextLine().trim();
        
        int weight;
        
        // Converts the weight from text to an integer
        // The try-catch prevents the program from crashing if the input is not a whole number
        try {
            weight = Integer.parseInt(weightInput);
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid weight. Enter a whole number.");
            return;
        }
        
        // Rejects zero or negative weights because food must have a positive weight
        if (weight <= 0) {
            System.out.println("\nWeight must be greater than zero.");
            return;
        }
        
        System.out.println("\nEnter best-before date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine().trim();
        
        LocalDate bestBefore;
        
        try {
            bestBefore = LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            System.out.println("\nInvalid date. Use the format yyyy-MM-dd.");
            return;
        }
        
        // Create a FoodItem object using the validated information
        FoodItem food = new FoodItem(name, weight, bestBefore);
        
        if (foodQueue.addFood(food)) {
            System.out.println("\nFood stored successfully.");
        }
    
    }
    
    // Removes the first food item according to the FIFO principle
    private static void removeFood() {
        
        FoodItem food = foodQueue.removeFood();
        
        if (food != null) {
            System.out.println("\nRemoved food details:");
            displayFoodDetails(food);
        }
    }
    
    
    private static void searchFood() {
        
        System.out.println("\nEnter the food name to search: ");
        String name = scanner.nextLine().trim();
        
        FoodItem food = foodQueue.searchFood(name);
        
        if (food == null) {
            System.out.println("\nFood not found.");
        } else {
            System.out.println("\nFood found:");
            displayFoodDetails(food);
        }
    }
    
    private static boolean isValidFoodName(String name) {
        return name.equalsIgnoreCase("Burger")
            || name.equalsIgnoreCase("Pizza")
            || name.equalsIgnoreCase("Fries")
            || name.equalsIgnoreCase("Sandwich")
            || name.equalsIgnoreCase("Hotdog");
    }
    
    private static void displayFoodDetails(FoodItem food) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        System.out.println("Name: " + food.getName());
        System.out.println("Weight: " + food.getWeight() + " g");
        System.out.println("Best-before date: " + food.getBestBefore());
        System.out.println("Time added: " + food.getTimeAdded().format(timeFormat));
    }
    
}
