package bci.users.behaviour;

import bci.Visitor;

public class Defaulter extends Behaviour {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int getMaxRequestsNumber() {
        return 1;
    }

    @Override
    public boolean canRequestBigWork() {
        return false;
    }

    @Override
    public int returnDate(int stock) {
        return 2;
    }

    @Override
     public Behaviour update(int properReturns, int lateReturns,Behaviour state) {
        if(lateReturns >= 3) {
            return new Defaulter();
        }
        if(properReturns >= 5) {
            return new Dutiful();
        }
        if(properReturns >= 3) {
            return new Normal();
        }
        return state;
    }

}
