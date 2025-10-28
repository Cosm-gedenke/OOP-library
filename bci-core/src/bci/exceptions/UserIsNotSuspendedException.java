package bci.exceptions;

public class UserIsNotSuspendedException extends Exception {
    private int userId;

    public UserIsNotSuspendedException(int userId) {
        this.userId = userId;
    }

    public int getID() {
        return this.userId;
    }
}
