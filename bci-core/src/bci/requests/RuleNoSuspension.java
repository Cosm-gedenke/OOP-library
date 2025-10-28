package bci.requests;

import bci.users.User;
import bci.works.Work;

public class RuleNoSuspension extends Rule {
    public RuleNoSuspension() {
        super(2);
    }
    
    @Override
    public boolean isValid(User user, Work work) {
        return (user.canMakeRequest());
    }
}
