package bci.exceptions;

public class WorkNotBorrowedByUserException extends Exception {
    private int userID, workID;

    public WorkNotBorrowedByUserException(int userID, int workID) {
        this.userID = userID;
        this.workID = workID;
    }

    public int getUserID() {
        return this.userID;
    }

    public int getWorkID() {
        return this.workID;
    }
}
