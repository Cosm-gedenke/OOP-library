package bci.users.behaviour;

import bci.Visitor;

public class Normal extends Behaviour {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int getMaxRequestsNumber() {
        return 3;
    }

    @Override
    public boolean canRequestBigWork() {
        return false;
    }

    @Override
    public int returnDate(int stock) {
        if(stock == 1) {return 3;}
        if(stock <= 5 ) {return 8;}
        return 15;
    }

}
