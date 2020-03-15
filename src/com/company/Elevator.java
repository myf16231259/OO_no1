package com.company;

import com.oocourse.TimableOutput;
import com.oocourse.elevator3.PersonRequest;
import java.util.ArrayList;

public class Elevator extends Thread {
    private int curfloor;
    private String na;
    private ArrayList<PersonRequest> req;
    private ArrayList<PersonRequest> reqonele;
    private ArrayList<Transfer> trans;
    private ArrayList<Transfer> transreq;
    private ArrayList<Transfer> transreqon;
    private boolean hasreq;
    private PersonRequest request;
    private Transfer transf;
    private int direction;
    private int len;
    private int hasout;
    private int hasin;
    private int dooropen;
    private int hasret = 0;
    private int runtime;
    private int passengernum;
    private int maxpassenger;
    private int getfloor;
    private int dir;
    private int id;
    private int tranlen;
    private int in;

    public Elevator(String a, ArrayList<PersonRequest> b,int c,
                    ArrayList<Transfer> d,int e,
                    ArrayList<Transfer> f,
                    ArrayList<PersonRequest> g) {
        na = a;
        curfloor = 1;
        req = b;
        hasreq = true;
        reqonele = new ArrayList<PersonRequest>();
        runtime = c;
        passengernum = 0;
        trans = d;
        maxpassenger = e;
        transreq = f;
        transreqon = new ArrayList<>();
        reqonele = g;
    }

    public void changeHasreq() {
        this.hasreq = false;
    }

    private boolean Getreq() {
        if (req.size() > 0) {
            //System.out.println("true");
            return true;
        } else {
            //System.out.println("false");
            return false;
        }
    }

    private boolean Gettransreq() {
        if (transreq.size() > 0) {
            //System.out.println("true");
            return true;
        } else {
            //System.out.println("false");
            return false;
        }
    }

    private boolean Getreqon() {
        if (reqonele.size() > 0 || transreqon.size() > 0) {
            //System.out.println("true");
            return true;
        } else {
            //System.out.println("false");
            return false;
        }
    }

    private void setdirection(PersonRequest a) {
        if (a.getFromFloor() > a.getToFloor()) {
            direction = 0;
        } else {
            direction = 1;
        }
    }

    private void settrandirection(Transfer a) {
        if (a.getFromFloor() > a.getToFloor()) {
            direction = 0;
        } else {
            direction = 1;
        }
    }

    int getdirection(int a, int b) {
        if (a > b) {
            return 0;
        } else {
            return 1;
        }
    }

    private void countfloor(int a) {
        if (a == 1) {
            if (curfloor == -1) {
                curfloor = 1;
            } else {
                curfloor = curfloor + 1;
            }
        } else {
            if (curfloor == 1) {
                curfloor = -1;
            } else {
                curfloor = curfloor - 1;
            }
        }
    }

    private void getdata() {
        if (Getreq()) {
            request = req.get(0);
            reqonele.add(request);
            setdirection(request);
            req.remove(0);
            passengernum++;
            //System.out.println(reqnum);
            getfloor = request.getFromFloor();
            dir = getdirection(curfloor, getfloor);
            id = request.getPersonId();
        } else {
            transf = transreq.get(0);
            transreqon.add(transf);
            settrandirection(transf);
            transreq.remove(0);
            passengernum++;
            //System.out.println(reqnum);
            getfloor = transf.getFromFloor();
            dir = getdirection(curfloor, getfloor);
            id = transf.getPersonId();
        }
    }

    public void run() {
        try {
            while (hasreq) {
                if (Getreq() || Gettransreq()) {
                    getdata();
                    while (curfloor != getfloor) {
                        Thread.sleep(runtime);
                        countfloor(dir);
                        TimableOutput.println(
                                String.format("ARRIVE-%d-%s", curfloor,na));
                    }
                    TimableOutput.println(
                            String.format("OPEN-%d-%s", getfloor,na));
                    TimableOutput.println(
                            String.format("IN-%d-%d-%s", id, getfloor,na));
                    dooropen = 1;
                    while (reqonele.size() > 0 || transreqon.size() > 0) {
                        //System.out.println(reqonele.size());
                        handlesubreq();
                        if (reqonele.size() > 0 || transreqon.size() > 0) {
                            Thread.sleep(runtime);
                            countfloor(direction);
                            TimableOutput.println(
                                    String.format("ARRIVE-%d-%s",curfloor,na));
                        }
                    }
                    Thread.yield();
                } else {
                    Thread.sleep(1);
                    Thread.yield();
                }
            }
        } catch (Exception e) {
            System.out.println("run");
        }
    }

    private boolean ifA(int a,int b) {
        if ((a <= 1 && a >= -3) || (a <= 20 && a >= 15)) {
            if ((b <= 1 && b >= -3) || (b <= 20 && b >= 15)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean ifB(int a,int b) {
        if ((a <= 2 && a >= -2) || (a <= 15 && a >= 4)) {
            if ((b <= 2 && b >= -2) || (b <= 15 && b >= 4)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean ifC(int a,int b) {
        if (a <= 15 && a >= 1 && a % 2 != 0
                && b <= 15 && b >= 1 && b % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean handletrans(int a, int i) {
        int b = reqonele.get(i).getToFloor();
        int id = reqonele.get(i).getPersonId();
        //System.out.println(b);
        if (ifA(a,b) || ifB(a,b) || ifC(a,b)) {
            Transfer temp = new Transfer(id,a,b);
            trans.add(temp);
            return true;
        } else {
            return false;
        }
    }

    private boolean cannotout(int a) {
        if (na.equals("A")) {
            if ((a <= 1 && a >= -3) || (a <= 20 && a >= 15)) {
                return false;
            } else {
                return true;
            }
        } else if (na.equals("B")) {
            if ((a <= 2 && a >= -2) || (a <= 15 && a >= 4)) {
                return false;
            } else {
                return true;
            }
        } else {
            if (a <= 15 && a >= 1 && a % 2 != 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    private void handleout() {
        int i;
        len = reqonele.size();
        tranlen = transreqon.size();
        if (ifout()) {
            for (i = 0; i < len; i++) {
                if (curfloor == reqonele.get(i).getToFloor()) {
                    if (hasout == 0) {
                        TimableOutput.println(
                                String.format("OPEN-%d-%s", curfloor,na));
                        hasout = 1;
                    }
                    int id = reqonele.get(i).getPersonId();
                    TimableOutput.println(
                            String.format("OUT-%d-%d-%s",id, curfloor,na));
                    passengernum--;
                    reqonele.remove(i);
                    i--;
                    len--;
                } else {
                    if (cannotout(reqonele.get(i).getToFloor())) {
                        if (handletrans(curfloor,i)) {
                            if (hasout == 0) {
                                TimableOutput.println(
                                        String.format("OPEN-%d-%s",
                                                curfloor,na));
                                hasout = 1;
                            }
                            int id = reqonele.get(i).getPersonId();
                            TimableOutput.println(
                                    String.format("OUT-%d-%d-%s",id,
                                            curfloor,na));
                            passengernum--;
                            reqonele.remove(i);
                            i--;
                            len--;
                        }
                    }
                }
            }
            for (i = 0; i < tranlen;i++) {
                //System.out.println(transreqon.get(i).getToFloor());
                if (curfloor == transreqon.get(i).getToFloor()) {
                    if (hasout == 0) {
                        TimableOutput.println(
                                String.format("OPEN-%d-%s", curfloor,na));
                        hasout = 1;
                    }
                    int id = transreqon.get(i).getPersonId();
                    TimableOutput.println(
                            String.format("OUT-%d-%d-%s",id, curfloor,na));
                    passengernum--;
                    transreqon.remove(i);
                    i--;
                    tranlen--;
                }
            }
        }
    }

    private boolean ifout() {
        int a = curfloor;
        if (na.equals("A")) {
            if ((a <= 1 && a >= -3) || (a <= 20 && a >= 15)) {
                return true;
            } else {
                return false;
            }
        } else if (na.equals("B")) {
            if ((a <= 2 && a >= -2) || (a <= 15 && a >= 4)) {
                return true;
            } else {
                return false;
            }
        } else if (na.equals("C")) {
            if (a <= 15 && a >= 1 && a % 2 != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void handlereqin() {
        try {
            if (curfloor == req.get(in).getFromFloor()) {
                int id = req.get(in).getPersonId();
                int getflr = req.get(in).getFromFloor();
                int toflr = req.get(in).getToFloor();
                if (direction == getdirection(getflr,toflr)) {
                    reqonele.add(req.get(in));
                    if (hasout == 0 && dooropen == 0 && hasin == 0) {
                        TimableOutput.println(
                                String.format("OPEN-%d-%s",
                                        curfloor, na));
                    }
                    TimableOutput.println(
                            String.format("IN-%d-%d-%s",
                                    id, getflr, na));
                    passengernum++;
                    req.remove(in);
                    //reqnum++;
                    in--;
                    len--;
                    hasin = 1;
                } else {
                    if (reqonele.size() == 0 &&
                        transreqon.size() == 0) {
                        TimableOutput.println(
                                String.format("IN-%d-%d-%s",
                                        id, getflr, na));
                        reqonele.add(req.get(in));
                        setdirection(req.get(in));
                        passengernum++;
                        //reqnum++;
                        req.remove(in);
                        in--;
                        len--;
                        hasin = 1;
                        dooropen = 1;
                        while (reqonele.size() > 0) {
                            handlesubreq();
                            if (reqonele.size() > 0) {
                                Thread.sleep(runtime);
                                countfloor(direction);
                                int cu = curfloor;
                                TimableOutput.println(
                                        String.format("ARRIVE-%d-%s",
                                                cu, na));
                            }
                        }
                        hasret = 1;
                        Thread.yield();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("handlereqin");
        }
    }

    private void handletranin() {
        try {
            if (curfloor == transreq.get(in).getFromFloor()) {
                int id = transreq.get(in).getPersonId();
                int getflr = transreq.get(in).getFromFloor();
                int toflr = transreq.get(in).getToFloor();
                if (direction == getdirection(getflr,toflr)) {
                    transreqon.add(transreq.get(in));
                    if (hasout == 0 && dooropen == 0 && hasin == 0) {
                        TimableOutput.println(
                                String.format("OPEN-%d-%s",
                                        curfloor, na));
                    }
                    TimableOutput.println(
                            String.format("IN-%d-%d-%s",
                                    id, getflr, na));
                    passengernum++;
                    transreq.remove(in);
                    //reqnum++;
                    in--;
                    tranlen--;
                    hasin = 1;
                } else {
                    if (reqonele.size() == 0 &&
                            transreqon.size() == 0) {
                        TimableOutput.println(
                                String.format("IN-%d-%d-%s",
                                        id, getflr, na));
                        transreqon.add(transreq.get(in));
                        settrandirection(transreq.get(in));
                        passengernum++;
                        //reqnum++;
                        transreq.remove(in);
                        in--;
                        tranlen--;
                        hasin = 1;
                        dooropen = 1;
                        while (reqonele.size() > 0 ||
                               transreqon.size() > 0) {
                            handlesubreq();
                            if (reqonele.size() > 0 ||
                                    transreqon.size() > 0) {
                                Thread.sleep(runtime);
                                countfloor(direction);
                                int cu = curfloor;
                                TimableOutput.println(
                                        String.format("ARRIVE-%d-%s",
                                                cu, na));
                            }
                        }
                        hasret = 1;
                        Thread.yield();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("handletranin");
        }
    }

    private void handlein() {
        try {
            len = req.size();
            tranlen = transreq.size();
            for (in = 0; in < len; in++) {
                if (passengernum < maxpassenger) {
                    handlereqin();
                }
            }
            for (in = 0; in < tranlen; in++) {
                if (passengernum < maxpassenger) {
                    handletranin();
                }
            }
        } catch (Exception e) {
            System.out.println("handlein");
        }
    }

    private void handlesubreq() {
        try {
            if (Getreq() || Gettransreq() || Getreqon()) {
                hasin = 0;
                hasout = 0;
                handleout();
                handlein();
                if (hasret != 1) {
                    if (hasin == 1 || hasout == 1 || dooropen == 1) {
                        Thread.sleep(400);
                        dooropen = 0;
                        TimableOutput.println(
                                String.format("CLOSE-%d-%s", curfloor,na));
                    }
                } else {
                    hasret = 0;
                }
            }
        } catch (Exception e) {
            System.out.println("handlesubreq");
        }
    }
}
