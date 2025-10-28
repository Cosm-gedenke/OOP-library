package bci.users.userstate;

import bci.Visitor;
import bci.requests.Request;
import bci.users.User;

import java.util.Collection;

public class SuspendedState extends UserState {
    public SuspendedState(User user) {
        super(user);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean canMakeRequest() {
        return false;
    }

    @Override
    public void update(Collection<Request> requests, int date) {
        for (Request request : requests) {
            if (request.isOverdue(date)) { 
                return;
            }
        }
        
        getUser().setState(new ActiveState(getUser()));
    }
}
