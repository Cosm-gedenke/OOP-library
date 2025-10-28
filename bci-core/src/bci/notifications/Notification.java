package bci.notifications;

import bci.Visitable;
import bci.works.Work;

import java.io.Serializable;

public abstract class Notification implements Visitable, Serializable {
    // store a copy of the work at the time of the notification
    private Work workCopy;

    public Notification(Work work) {
        this.workCopy = work.copy();
    }

    public Work getWork() {
        return this.workCopy;
    };
}
