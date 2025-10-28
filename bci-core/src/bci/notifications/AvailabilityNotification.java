package bci.notifications;

import bci.Visitor;
import bci.works.*;

public class AvailabilityNotification extends Notification {
    /**
     * Creates a new availabilitynotification
     * @param work work that is being requested
     */
    public AvailabilityNotification(Work work) {
        super(work);
        // Available stock will always be 1 when this notification is thrown
        getWork().setAvailableStock(1);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
