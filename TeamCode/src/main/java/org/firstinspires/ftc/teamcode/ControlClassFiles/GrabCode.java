package org.firstinspires.ftc.teamcode.ControlClassFiles;


import static java.lang.System.*;



import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/*
 * This class runs the grab code
 *
 */

public class GrabCode {

    /** @noinspection FieldCanBeLocal, unused */ // Instance Variables
    private final Gamepad gamepad1;
    private final HardwareMap hardwareMap;

    // Servos
    private final Servo    leftClaw;
    private final Servo    rightClaw;
    private final Servo    shoulder;
    private final Servo    elbow;
    private final Servo    wrist;

    // Middle of servo value
    private static final double MID_SERVO   =  0.5 ;

    // grab variables
    private int stage = 0;
    private double time = 0;

    // constructor initializes all servos
    public GrabCode(@NonNull com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {

        // set all servos to the correct values
        leftClaw  = hardwareMap.get(Servo.class, "lFinger");
        rightClaw = hardwareMap.get(Servo.class, "rFinger");

        shoulder  = hardwareMap.get(Servo.class, "shoulder");
        elbow     = hardwareMap.get(Servo.class, "elbow");
        wrist     = hardwareMap.get(Servo.class, "wrist");

        // set initial positions
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);
        wrist.setPosition(0.6);
        shoulder.setPosition(0.5);
        elbow.setPosition(0.5);

        // set gamepad and hardware map variables
        this.gamepad1 = gamepad1;
        this.hardwareMap = hardwareMap;
    }

    // runs the steps of grab: whenever it's called it does the next stage
    public void grab() {
        // will only run if it has been at least a second since it was last run
        if (currentTimeMillis() > time + 1000) {

            // steps start at 0 and go up
            if (stage == 5) { // reset
                restState();
            }
            else if (stage == 4) { // drop
                release();
            }
            else if (stage == 3) { // reach upward
                reachUp();
            }
            else if (stage == 2) { // reset
                restState();
            }
            else if (stage == 1) { // grab
                closeHand();
            }
            else if (stage == 0) { // prepare to grab
                readyToGrab();
            }

            // reset time variable so it will wait until it can run again
            time = currentTimeMillis();

            // increase stage
            stage++;
            // loops so you can score multiple times
            if (stage > 5) stage = 0;
        }
    }

    // sets arm to lying flat ready to grab stuff
    public void readyToGrab() {
        subtleServoMove(shoulder, 0.6);
        subtleServoMove(elbow, 0.8);
        subtleServoMove(wrist, 0.5);

        rightClaw.setPosition(0);
        leftClaw.setPosition(1);
    }

  // sets are to reaching over bar and ready to grab something from inside submersible
  /*  public void readyToGrabOverBar() {
        subtleServoMove(shoulder, 0.6);
        subtleServoMove(elbow, 0.8);
        subtleServoMove(wrist, 0.7);

        rightClaw.setPosition(0);
        leftClaw.setPosition(1);
    }*/

    // grab
    public void closeHand() {
        rightClaw.setPosition(0.6);
        leftClaw.setPosition(0.4);
    }
    // set to rest state
    public void restState() {
        // initial positions
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);
        subtleServoMove(wrist, 0.6);
        subtleServoMove(shoulder, 0.5);
        subtleServoMove(elbow, 0.5);
        subtleServoMove("lift",0.06);
    }
    // ready to place in basket
    public void reachUp() {
        subtleServoMove(shoulder, 0.2);
        subtleServoMove(elbow, 0.5);
        subtleServoMove(wrist, 0.5);

        rightClaw.setPosition(0.6);
        leftClaw.setPosition(0.4);

        subtleServoMove("lift",0.7);
    }
    // open claw
    public void release() {
        rightClaw.setPosition(0);
        leftClaw.setPosition(1);
    }



    // subtleServoMove slowly moves a servo to a given position
    // it does this by creating a subtleServoMoveThread and starting it
    private void subtleServoMove(Servo servo, double position) {
        subtleServoMoveThread m = new subtleServoMoveThread(servo, position, hardwareMap);
        m.start();
    }

    /** @noinspection SameParameterValue*/
    private void subtleServoMove(String type, double position) {
        subtleServoMoveThread m = new subtleServoMoveThread(type, position, hardwareMap);
        m.start();
    }
}
