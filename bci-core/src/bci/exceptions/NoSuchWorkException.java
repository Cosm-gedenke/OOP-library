package bci.exceptions;

public class NoSuchWorkException extends Exception {
    private int ID;

    public NoSuchWorkException(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }
}
