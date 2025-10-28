package bci.works;

import java.io.Serializable;

import bci.Visitable;
import bci.exceptions.NotEnoughInventoryException;

public abstract class Work implements Comparable<Work>, Visitable , Serializable {
    /**
     * Unique ID of the work
     */
    private int ID;

    /**
     * Total number of copies of this work in the library
     */
    private int totalStock;

    /**
     * Number of the currently available copies of this work in the library
     */
    private int availableStock;

    /**
     * Price of the work
     */
    private int price;

    /**
     * Title of the work
     */
    private String title;

    /**
     * Category of the work
     */
    private Category category;

    public Work(int ID, int stock, int price, String title, Category category) {
        this.ID = ID;
        this.totalStock = stock;
        this.availableStock = stock;
        this.price = price;
        this.title = title;
        this.category = category;
    }

    /**
     * Gets the title of the work
     * @return Title of the work
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the category of the work
     * @return category of the work
     */
    public Category getCategory() {
      return this.category;
    }

    /**
     * Gets the price of the work
     * @return price of the work
     */
    public int getPrice() {
      return this.price;
    }

    /**
     * Gets the total stock of the work
     * @return total stock of the work
     */
    public int getTotalStock() {
      return this.totalStock;
    }

    /**
     * Gets the currently available stock of the work
     */
    public int getAvailableStock() {
        return this.availableStock;
    }
    
    /**
     * Gets the id of the work
     * @return id of the work
     */
    public int getID() {
        return this.ID;
    }

    public void setAvailableStock(int stock) {
        this.availableStock = stock; // Does not check for validity
    }

    public abstract Work copy();

    public abstract boolean containsTerm(String searchterm);
    
    public void updateInventory(int amount) throws NotEnoughInventoryException {
        if (this.availableStock + amount < 0) {
            throw new NotEnoughInventoryException(this.ID, amount);
        }

        this.totalStock += amount;
        this.availableStock += amount;
    }

    public void incrementAvailableStock() {
        this.availableStock++;
    }

    public void decrementAvailableStock() {
        this.availableStock--;
    }

    @Override
    public int compareTo(Work other) {
        return this.title.compareToIgnoreCase(other.title);
    }
   
}
