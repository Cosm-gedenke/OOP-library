package bci.users.userstate;

import java.util.Collection;

import bci.Visitor;
import bci.requests.Request;
import bci.users.User;

public class ActiveState extends UserState {
    public ActiveState(User user) {
        super(user);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean canMakeRequest() {
        return true;
    }

    @Override
    public void update(Collection<Request> requests, int date) {
        User user = getUser();

        if (user.getDebt() > 0) {
            user.setState(new SuspendedState(user));
        }

        for(Request request : requests) {
            if (request.isOverdue(date)) {
                user.setState(new SuspendedState(user));
            }
        }
    }
}
