package bci.users.behaviour;

import bci.Visitor;

public class Dutiful extends Behaviour {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int getMaxRequestsNumber() {
        return 5;
    }

    @Override
    public boolean canRequestBigWork() {
        return true;
    }

    @Override
    public int returnDate(int stock) {
        if(stock == 1) {return 8;}
        if(stock <= 5 ) {return 15;}
        return 30;
    }

}
