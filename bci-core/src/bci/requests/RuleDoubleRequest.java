package bci.requests;

import bci.users.User;
import bci.works.Work;

public class RuleDoubleRequest extends Rule {
    public RuleDoubleRequest() {
        super(1);
    }
    
    @Override
    public boolean isValid(User user, Work work) {
        return !user.hasDuplicateRequest(work.getID());
    }
}
