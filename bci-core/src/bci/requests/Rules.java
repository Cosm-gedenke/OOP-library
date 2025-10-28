package bci.requests;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import bci.users.User;
import bci.works.Work;
import bci.exceptions.BorrowingRuleFailedException;
import bci.exceptions.NoStockRuleFailedException;

public class Rules implements Serializable {
    private List<Rule> rules = new ArrayList<Rule>();

    private void setList() {
        rules.add(new RuleDoubleRequest());
        rules.add(new RuleNoSuspension());
        rules.add(new RuleNoStock());
        rules.add(new RuleLimitRequests());
        rules.add(new RuleNoReference());
        rules.add(new RuleLimitPrice());

    }

    public Rules() {
        setList();
    }

    public void validateAll(User user, Work work)
        throws BorrowingRuleFailedException, NoStockRuleFailedException {
            for (Rule rule : rules) {
                if (!rule.isValid(user, work)) {
                    if (rule.getID() == 3) {
                        throw new NoStockRuleFailedException(rule.getID()); // rule 3 is an exception
                    }
                    throw new BorrowingRuleFailedException(rule.getID());
                }
            }
    }
}
