package bci.requests;

import bci.users.User;
import bci.works.Work;

public class RuleNoStock extends Rule {
    public RuleNoStock() {
        super(3);
    }
    
    @Override
    public boolean isValid(User user, Work work) {
      return work.getAvailableStock() > 0;
    }
}
