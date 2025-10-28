package bci.requests;

import bci.users.User;
import bci.works.Work;

public class RuleLimitRequests extends Rule {
    public RuleLimitRequests() {
        super(4);
    }
    
    @Override
    public boolean isValid(User user, Work work) {
        return user.getRequests().size() < user.getBehaviour().getMaxRequestsNumber();
    }
}
