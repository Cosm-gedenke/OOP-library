package bci.requests;

import bci.users.User;
import bci.works.Work;

public class RuleLimitPrice extends Rule {
    public RuleLimitPrice() {
        super(6);
    }
    
    @Override
    public boolean isValid(User user, Work work) {
        return user.getBehaviour().canRequestBigWork() || (work.getPrice() <= 25);
    }
}
