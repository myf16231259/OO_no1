package com.company;

import com.oocourse.elevator3.PersonRequest;
import java.util.ArrayList;

public class Dispatch extends Thread {
    private ArrayList<PersonRequest> req;
    private ArrayList<PersonRequest> reqa;
    private ArrayList<PersonRequest> reqb;
    private ArrayList<PersonRequest> reqc;
    private ArrayList<PersonRequest> reqaon;
    private ArrayList<PersonRequest> reqbon;
    private ArrayList<PersonRequest> reqcon;
    private ArrayList<Transfer> transfer;
    private boolean hasreq;
    private PersonRequest request;
    private volatile int reqnum;
    private Transfer tran;
    private ArrayList<Transfer> transa;
    private ArrayList<Transfer> transb;
    private ArrayList<Transfer> transc;
    private volatile boolean hasend = false;

    public int getReqnum() {
        return reqnum;
    }

    public void changeHasreq() {
        this.hasreq = false;
    }

    public Dispatch(ArrayList<PersonRequest> a,
                    ArrayList<PersonRequest> b,
                    ArrayList<PersonRequest> c,
                    ArrayList<PersonRequest> d,
                    ArrayList<Transfer> e,
                    ArrayList<Transfer> f,
                    ArrayList<Transfer> g,
                    ArrayList<Transfer> h,
                    ArrayList<PersonRequest> i,
                    ArrayList<PersonRequest> j,
                    ArrayList<PersonRequest> k) {
        req = a;
        reqa = b;
        reqb = c;
        reqc = d;
        hasreq = true;
        reqnum = 0;
        transfer = e;
        transa = f;
        transb = g;
        transc = h;
        reqaon = i;
        reqbon = j;
        reqcon = k;
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

    private boolean Gettrans() {
        if (transfer.size() > 0) {
            //System.out.println("true");
            return true;
        } else {
            //System.out.println("false");
            return false;
        }
    }

    private boolean ifA() {
        int a = request.getFromFloor();
        int b = request.getToFloor();
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

    private boolean ifB() {
        int a = request.getFromFloor();
        int b = request.getToFloor();
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

    private boolean ifC() {
        int a = request.getFromFloor();
        int b = request.getToFloor();
        if (a <= 15 && a >= 1 && a % 2 != 0
             && b <= 15 && b >= 1 && b % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean iftranB() {
        int a = tran.getFromFloor();
        int b = tran.getToFloor();
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

    private boolean iftranC() {
        int a = tran.getFromFloor();
        int b = tran.getToFloor();
        if (a <= 15 && a >= 1 && a % 2 != 0
                && b <= 15 && b >= 1 && b % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }

    private void choose() {
        int a = request.getFromFloor();
        if (a <= 15 && a >= 1 && a % 2 != 0) {
            reqc.add(request);
        } else if ((a <= 2 && a >= -2) || (a <= 15 && a >= 4)) {
            reqb.add(request);
        } else {
            reqa.add(request);
        }
    }

    private boolean ifend() {
        if (hasreq) {
            return true;
        } else {
            if (
                transfer.size() == 0 &&
                transa.size() == 0 &&
                transb.size() == 0 &&
                transc.size() == 0 &&
                reqaon.size() == 0 &&
                reqbon.size() == 0 &&
                reqcon.size() == 0) {
                if (reqa.size() == 0 &&
                    reqb.size() == 0 &&
                    reqc.size() == 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    public boolean getHasend() {
        return hasend;
    }

    public void run() {
        try {
            while (ifend()) {
                if (Getreq()) {
                    request = req.get(0);
                    req.remove(0);
                    reqnum++;
                    if (ifC()) {
                        reqc.add(request);
                    } else if (ifB()) {
                        reqb.add(request);
                    } else if (ifA()) {
                        reqa.add(request);
                    } else {
                        choose();
                    }
                } else if (Gettrans()) {
                    //System.out.println("herere");
                    tran = transfer.get(0);
                    transfer.remove(0);
                    if (iftranC()) {
                        transc.add(tran);
                    } else if (iftranB()) {
                        transb.add(tran);
                    } else {
                        transa.add(tran);
                    }
                } else {
                    Thread.sleep(1);
                    Thread.yield();
                }
            }
            hasend = true;
        } catch (Exception e) {
            System.out.println("Dispatch");
        }

    }
}
