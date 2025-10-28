package bci.notifications;

import bci.Visitor;
import bci.works.*;

public class RequestNotification extends Notification {
    /**
     * Creates a new requestnotification
     * @param work work that is being requested
     */
    public RequestNotification(Work work) {
        super(work);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
