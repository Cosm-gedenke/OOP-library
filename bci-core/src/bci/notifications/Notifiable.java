package bci.notifications;

public interface Notifiable {
    void addNotification(Notification notification);

    void removeNotification(Notification notification);
}
