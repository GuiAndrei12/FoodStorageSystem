/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstoragesystem;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Queue;
/**
 *
 * @author guilh
 */
public class FoodQueue {
    
    // Maximum number of items
    private static final int MAX_CAPACITY = 8;
    
    private Queue<FoodItem> foods = new ArrayDeque<>();

    // Method to add the item
    public boolean addFood(FoodItem food){
          
        // Food name condition
        if (food == null) {
            System.out.println("\nInvalid food item");
            return false;
        }
        
        // Number of items condition
        if (foods.size() >= MAX_CAPACITY){
            System.out.println("\nStorage is full. Maximum capacity is 8.");
            return false;
        }
        
        // Food weight condition
        if (food.getWeight() <= 0){
            System.out.println("\nWeight must be greater than zero");
            return false;
        }
        
        
        LocalDate today = LocalDate.now();
        LocalDate bestBefore = food.getBestBefore();
        
        // Best Before conditions
        if (bestBefore == null
                || bestBefore.isBefore(today)
                || bestBefore.isAfter(today.plusWeeks(2))){
            System.out.println("\nBest-before date must be between today and two weeks from today.");
            return false;
        }
        
        // If all the conditions matched we add the item to the queue
        foods.offer(food);
        System.out.println(food.getName() + " added successfully.");
        return true;
    }
    
    // Method to remove the item
    public FoodItem removeFood() {
        
        if (foods.isEmpty()) {
            System.out.println("\nStorage is empty. Nothing to remove.");
            return null;
        }
        
        FoodItem removedFood = foods.poll();
        System.out.println(removedFood.getName() + " removed successfully.");
        return removedFood;
    
    }
    
    // Method to retrieve the first item
    public FoodItem peekFood() {
        
        if (foods.isEmpty()) {
            System.out.println("\nStorage is empty. Nothing to display.");
            return null;
        }
        
        return foods.peek();
    
    }
    
    // Method to display the first item
    public void displayFirstFood() {
        
        FoodItem food = peekFood();
        
        if (food != null) {
            System.out.println("\nFirst food in queue: " + food.getName());
        }    
    }
    
    // Method to search an item
    public FoodItem searchFood(String name) {
        
        if (name == null || name.trim().isEmpty()) {
            System.out.println("\nPlease enter a valid food name.");
            return null;
        }
        
        for (FoodItem food : foods) {
            if (food.getName().equalsIgnoreCase(name.trim())) {
                return food;
            }
        
        }
        
        return null;
    
    }
    
    public int getSize() {
        return foods.size();
    }
    
    public boolean isEmpty() {
        return foods.isEmpty();
    }
    
    public boolean isFull() {
        return foods.size() >= MAX_CAPACITY;
    }
   
}
