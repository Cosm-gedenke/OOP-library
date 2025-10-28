package bci.requests;

import bci.users.User;
import bci.works.Work;

import java.io.Serializable;

public abstract class Rule implements Serializable {
    private int ruleID;

    public Rule(int ruleID) {
        this.ruleID = ruleID;
    }

    /**
     * Checks the validity of a rule
	 * @param user user that is requesting a work
	 * @param work work that is being requested
	 * @return true if the request is valid according to some criteria; false
	 *         otherwise.
	 */
	public abstract boolean isValid(User user, Work work);

    public int getID() {
        return this.ruleID;
    }
    
}
