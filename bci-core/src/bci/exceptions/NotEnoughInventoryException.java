package bci.exceptions;

public class NotEnoughInventoryException extends Exception {
    private int workID;
    private int amount;

    public NotEnoughInventoryException(int id, int amount) {
        this.workID = id;
        this.amount = amount;
    }

    public int getID() {
        return this.workID;
    }
    
    public int getAmount() {
        return this.amount;
    }
}
