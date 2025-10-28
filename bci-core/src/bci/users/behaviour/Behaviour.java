package bci.users.behaviour;

import java.io.Serializable;

import bci.Visitable;

public abstract class Behaviour implements Visitable, Serializable {
    public abstract int getMaxRequestsNumber();

    public abstract boolean canRequestBigWork();

    public abstract int returnDate(int stock);
    
    public Behaviour update(int properReturns, int lateReturns,Behaviour state) {
        if(lateReturns >= 3) {
            return new Defaulter();
        }
        if(properReturns >= 5) {
            return new Dutiful();
        }
        return new Normal();
    }

        
}
