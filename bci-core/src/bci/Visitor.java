package bci;

import java.io.Serializable;

import bci.users.User;
import bci.users.userstate.*;
import bci.users.behaviour.*;
import bci.works.*;
import bci.notifications.*;

public abstract class Visitor<T> implements Serializable {
    
    public abstract T visit(User user);

    public abstract T visit(ActiveState state);

    public abstract T visit(SuspendedState state);

    public abstract T visit(Defaulter behaviour);

    public abstract T visit(Dutiful behaviour);

    public abstract T visit(Normal behaviour);

    public abstract T visit(Book book);

    public abstract T visit(DVD dvd);

    public abstract T visit(Category category);

    public abstract T visit(RequestNotification notification);

    public abstract T visit(AvailabilityNotification notification);
}
