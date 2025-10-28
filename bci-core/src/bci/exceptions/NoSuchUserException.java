package bci.exceptions;

public class NoSuchUserException extends Exception {
    private int ID;

    public NoSuchUserException(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }
}
