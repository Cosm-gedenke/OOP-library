package bci.exceptions;

public class NoStockRuleFailedException extends BorrowingRuleFailedException {
    public NoStockRuleFailedException(int ruleID) {
        super(ruleID);
    }
}
