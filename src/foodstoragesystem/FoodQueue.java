/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstoragesystem;
import java.time.LocalDate;

/**
 *
 * @author guilh
 */
public class FoodQueue {
    
    // Maximum number of items allowed
    private static final int MAX_CAPACITY = 8;
    
    // Fixed-size array to store the items
    private FoodItem[] foods;
    private int front;
    private int rear;
    private int size;

    // Initializes an empty circular queue
    // -1 indicates that no valid front or rear position exists yet
    public FoodQueue() {
        this.foods = new FoodItem[MAX_CAPACITY];
        this.front = -1;
        this.rear = -1;
        this.size = 0;
    }
    
    public boolean isEmpty() { 
        return size == 0;
    }
    
    public boolean isFull() {
        return size == MAX_CAPACITY;
    }
    
    // Method to add the item (Enqueue)
    public boolean addFood(FoodItem food){
          
        // Null item validaton
        if (food == null) {
            System.out.println("\nInvalid food item");
            return false;
        }
        
        // Number of items condition
        if (isFull()) {
            System.out.println("\nStorage is full. Maximum capacity is " + MAX_CAPACITY + ".");
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
        
        // All validation checks passed, so the food item can be added
        // If the queue is empty, initialize both front and rear at index 0
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            rear = (rear + 1) % MAX_CAPACITY; // Move rear to the next position, wrapping around when necessary
        }
        
        // Store the food item at the rear position and update the queue size
        foods[rear] = food;
        size++;
        System.out.println(food.getName() + " added successfully.");
        return true;
    }
    
    // Method to remove the item (Dequeue)
    public FoodItem removeFood() {
        
        if (isEmpty()) {
            System.out.println("\nStorage is empty. Nothing to remove.");
            return null;
        }
        
        FoodItem removedFood = foods[front]; // Retrieve the first food item before removing it
        foods[front] = null; // Clear the position so the removed item is no longer stored in the array
        size--; // Decrease the number of items currently stored
        
        
        // If the removed item was the last one, reset the queue
        if (front == rear) {
            front = -1;
            rear = -1;
        } else { 
            front = (front + 1) % MAX_CAPACITY; // Move front forward, wrapping around when necessary
        }
        
        System.out.println(removedFood.getName() + " removed successfully.");
        return removedFood;
    
    }
    
    // Returns the first food item without removing it from the queue
    public FoodItem peekFood() {
        
        if (isEmpty()) {
            System.out.println("\nStorage is empty. Nothing to display.");
            return null;
        }
        
        return foods[front];
    
    }
    
    // Displays the name of the first food item currently in the queue
    public void displayFirstFood() {
        
        FoodItem food = peekFood();
        
        if (food != null) {
            System.out.println("\nFirst food in queue: " + food.getName());
        }    
    }
    
    // Search for a food item by name, ignoring uppercase and lowercase differences
    public FoodItem searchFood(String name) {
        
        if (isEmpty() || name == null || name.trim().isEmpty()) {
            if (isEmpty()) {
                System.out.println("\nStorage is empty.");
            } else {
                System.out.println("\nPlease enter a valid food name.");
            }
            return null;
        }
        
        int i = front; // Start searching from the front of the queue
        while (true) {
            if (foods[i] != null && foods[i].getName().equalsIgnoreCase(name.trim())) {
                return foods[i];
            }
            if (i == rear) {
                break;
            }
            i = (i + 1) % MAX_CAPACITY;
        
        }
        
        return null;
    }
    
    // Method to retrieve the total quantity of items storaged
    public int getSize() {
        return size;
    }
   
}
