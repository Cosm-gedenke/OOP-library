package bci.requests;

import java.io.Serializable;

import bci.works.Work;

public class Request implements Serializable {
    private Work work;
    private int returnDate;
    
    public Request(Work work, int date) {
        this.work = work;
        this.returnDate = date;
    }

    public Work getWork() {
        return work;
    }

    public boolean isOverdue(int day) {
        return this.returnDate < day;
    }

    public int processPayment(int date) {
        int dividend = date - this.returnDate;
        if(dividend <= 0) {
            return 0;
        }
        return dividend * 5;  
    }

    public void setWork(Work work) {
        this.work = work;
    }
}
