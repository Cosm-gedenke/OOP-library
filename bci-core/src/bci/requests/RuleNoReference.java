package bci.requests;

import bci.users.User;
import bci.works.Work;

import bci.works.Category;

public class RuleNoReference extends Rule {
    public RuleNoReference() {
        super(5);
    }
    
    @Override
    public boolean isValid(User user, Work work) {
      return work.getCategory() != Category.REFERENCE; 
    }
}
