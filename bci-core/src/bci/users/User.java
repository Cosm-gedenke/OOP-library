package bci.users;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import bci.users.behaviour.*;
import bci.users.userstate.*;
import bci.Visitable;
import bci.Visitor;
import bci.notifications.*;
import bci.requests.Request;
import bci.works.Work;
import bci.exceptions.UserIsNotSuspendedException;
import bci.exceptions.WorkNotBorrowedByUserException;

public class User implements Comparable<User>, Visitable, Serializable, Notifiable {
    /**
     * Unique ID of the user
     */
    private int ID;

    /**
     * Dividends that the user owes to the library
     */
    private int debt = 0;

    /**
     * Name of the user
     */
    private String name;

    /**
     * The user's email address
     */
    private String email;

    /**
     * Current state of the user
     */
    private UserState state = new ActiveState(this);

    /**
     * Current behaviour of the user
     */
    private Behaviour behaviour = new Normal();

    /**
     * Current List of notifcations
     */
    private List<Notification> notifications = new ArrayList<Notification>();
    
    private Map<Integer, Notification> interests = new HashMap<Integer, Notification>();

    /**
     * List of all the active requests the user has
     */
    private Map<Integer, Request> payments = new HashMap<Integer, Request>();
    private Map<Integer, Request> requests = new HashMap<Integer, Request>();

    private int properReturns = 0;

    private int lateReturns = 0;

    /**
     * Creates a new user
     * @param name name of the user
     * @param email email of the user
     * @param ID ID of the user
     */
    public User(String name, String email, int ID) {
        this.name = name;
        this.email = email;
        this.ID = ID;
    }

    /**
     * Get the ID of the user
     * @return ID of the user
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Get the name of the user
     * @return name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the email of the user
     * @return email of the user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get the behaviour of the user
     * @return behaviour of the user
     */
    public Behaviour getBehaviour() {
        return this.behaviour;
    }

    /**
     * Get the state of the user
     * @return state of the user
     */
    public UserState getState() {
        return this.state;
    }
  
    /**
     * Get the debt of the user
     * @return debt of the user
     */
    public int getDebt() {
        return this.debt;
    }

    public List<Notification> getNotifications() {
        return this.notifications;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public void updateState(int date) {
        this.state.update(this.requests.values(), date);
    }

    public boolean canMakeRequest() {
        return this.state.canMakeRequest();
    }

    /**
     * Adds a request to the user
     * @param request request to add
     */
    public void addRequest(Request request) {
        this.requests.put(request.getWork().getID(), request);
    }

    /**
     * Removed a request from the user
     * @param request request to remove
     */
    public void removeRequest(Request request) {
        this.requests.remove(request.getWork().getID());
    }

    public void removeInterest(int workId) {
        interests.remove(workId);
    }

    /**
     * Gets the requests of a user
     */
    public Collection<Request> getRequests() {
        return requests.values();
    }

    public boolean hasDuplicateRequest(int workId) {
        return this.requests.containsKey(workId);
    }

    public void updateUserBehaviour() {
        this.behaviour = behaviour.update(properReturns, lateReturns, this.behaviour);
    }

    public boolean returnWork(Work work, int date) throws WorkNotBorrowedByUserException {
            Request request = getRequest(work.getID());
            
            if (request == null) {
                throw new WorkNotBorrowedByUserException(this.ID, work.getID());
            }
            
            return processReturn(request, date); // return wether it was out of stock before
    }

    private Request getRequest(int workId) throws WorkNotBorrowedByUserException {
        Request request = this.requests.get(workId);
        if (request == null) {
            throw new WorkNotBorrowedByUserException(this.ID, workId);
        }
        return request;
    }
     private boolean processReturn(Request request, int date) {
        Work work = request.getWork();
        
        this.debt += request.processPayment(date);

        if (this.debt > 0) {
            lateReturns++;
            properReturns = 0;
        }
        else {
            properReturns++;
            lateReturns = 0;
        }

        removeRequest(request);
        work.incrementAvailableStock();
        this.state.update(requests.values(), date);
        updateUserBehaviour();
        return work.getAvailableStock() == 1;
    }
    public int getFine(int date) {
          boolean datedrequests = false;
          int paiddebt = this.debt;
          this.state.update(requests.values(), date);
          return paiddebt;
    }

    public int payFine(int date) throws UserIsNotSuspendedException {
          if(getState().canMakeRequest()) {throw new UserIsNotSuspendedException(ID);}
          int paiddebt = this.debt;
          this.debt=0;
          this.state.update(requests.values(), date);
          return paiddebt;
    }

    public void addInterestAvailable(Notification notification) {
        interests.put(notification.getWork().getID(), notification);
    }

    public void checkInterest(int workID) {
        Notification notification = interests.get(workID);
        if (notification != null) {
            addNotification(notification);
        }
    }

    @Override 
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public int compareTo(User other) {
        int cmp = this.name.compareToIgnoreCase(other.name);
        if (cmp != 0) {
            return cmp;
        }
        return Integer.compare(this.ID, other.ID);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

       @Override
    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }

}
