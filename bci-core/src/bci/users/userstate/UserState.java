package bci.users.userstate;

import java.io.Serializable;
import java.util.Collection;

import bci.Visitable;
import bci.requests.Request;
import bci.users.User;

public abstract class UserState implements Visitable, Serializable {
    User user;

    public UserState(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return this.user;
    }

    public abstract boolean canMakeRequest();

    public abstract void update(Collection<Request> requests, int date);
}
