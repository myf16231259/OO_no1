package com.company;

import com.oocourse.TimableOutput;
import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.PersonRequest;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        ArrayList<PersonRequest> reqaon;
        reqaon = new ArrayList<>();
        ArrayList<PersonRequest> reqbon;
        reqbon = new ArrayList<>();
        ArrayList<PersonRequest> reqcon;
        reqcon = new ArrayList<>();
        ArrayList<Transfer> trans = new ArrayList<Transfer>();
        ArrayList<Transfer> transa = new ArrayList<Transfer>();
        ArrayList<Transfer> transb = new ArrayList<Transfer>();
        ArrayList<Transfer> transc = new ArrayList<Transfer>();
        ArrayList<PersonRequest> reqa = new ArrayList<>();
        ArrayList<PersonRequest> reqb = new ArrayList<>();
        ArrayList<PersonRequest> reqc = new ArrayList<>();
        Elevator elv1 = new Elevator("A",reqa,400,trans,6,
                transa,reqaon);
        ArrayList<PersonRequest> req = new ArrayList<>();
        Dispatch disp = new Dispatch(req,reqa,reqb,reqc,trans,
                transa,transb,transc,reqaon,reqbon,reqcon);
        TimableOutput.initStartTimestamp();
        disp.start();
        elv1.start();
        Elevator elv2 = new Elevator("B",reqb,500,trans,8,
                transb,reqbon);
        elv2.start();
        Elevator elv3 = new Elevator("C",reqc,600,trans,7,
                transc,reqcon);
        elv3.start();
        int reqnum = 0;
        while (true) {
            PersonRequest request = elevatorInput.nextPersonRequest();
            // when request == null
            // it means there are no more lines in stdin
            //System.out.println("Get");
            try {
                if (request == null) {
                    //System.out.println(reqnum);
                    while (true) {
                        //System.out.println(elv1.getReqnum());
                        if (reqnum == disp.getReqnum()) {
                            //System.out.println("here");
                            disp.changeHasreq();
                            while (true) {
                                if (disp.getHasend()) {
                                    elv1.changeHasreq();
                                    elv2.changeHasreq();
                                    elv3.changeHasreq();
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                } else {
                    req.add(request);
                    reqnum++;
                    //System.out.println(reqnum);
                    // a new valid request
                    Thread.yield();
                }
            } catch (Exception e) {
                System.out.println("Exception");
            }

        }

        //System.out.println("here");
        elevatorInput.close();
    }
}

