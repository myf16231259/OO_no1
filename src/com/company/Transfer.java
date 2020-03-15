package com.company;

public class Transfer {
    private int personId;
    private int fromFloor;
    private int gotoFloor;

    public Transfer(int a,int b, int c) {
        personId = a;
        fromFloor = b;
        gotoFloor = c;
    }

    public int getPersonId() {
        return personId;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return gotoFloor;
    }
}
