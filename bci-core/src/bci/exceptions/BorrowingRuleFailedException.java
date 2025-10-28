package bci.exceptions;

public class BorrowingRuleFailedException extends Exception {
    private int ruleID;

    public BorrowingRuleFailedException(int ruleID) {
        this.ruleID = ruleID;
    }

    public int getRuleID() {
        return ruleID;
    }
    
}
