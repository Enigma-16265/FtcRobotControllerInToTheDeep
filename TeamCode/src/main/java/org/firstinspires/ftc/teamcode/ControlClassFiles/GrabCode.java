package org.firstinspires.ftc.teamcode.ControlClassFiles;


import static java.lang.System.*;



import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * This class runs the arm control code
 * well actually it should eventually just do grab
 *
 */

public class GrabCode {

    public Gamepad gamepad1;
    HardwareMap hardwareMap;

    // Servos
    private final Servo    leftClaw;
    private final Servo    rightClaw;
    private final Servo    shoulder;
    private final Servo    elbow;
    private final Servo    wrist;

    // Middle of servo value
    private static final double MID_SERVO   =  0.5 ;

    // constructor initializes all servos
    public GrabCode(@NonNull com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {
        leftClaw  = hardwareMap.get(Servo.class, "lFinger");
        rightClaw = hardwareMap.get(Servo.class, "rFinger");
        shoulder  = hardwareMap.get(Servo.class, "shoulder");
        elbow     = hardwareMap.get(Servo.class, "elbow");
        wrist     = hardwareMap.get(Servo.class, "wrist");
        Servo leftLift = hardwareMap.get(Servo.class, "leftLift");
        Servo rightLift = hardwareMap.get(Servo.class, "rightLift");

        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);
        // stole these initial positions from mcmuffin the revenge
        wrist.setPosition(0.575);
        shoulder.setPosition(0.425);
        elbow.setPosition(0.5);
        leftLift.setPosition(0.06);
        rightLift.setPosition(0.06);

        this.gamepad1 = gamepad1;
        this.hardwareMap = hardwareMap;
    }


    private int stage = 0;
    private double time = 0;

    public void grab() {
        if (currentTimeMillis() > time + 1000) {

            if (stage == 5) { // reset
                restState();
            }
            else if (stage == 4) { // drop
                release();
            }
            else if (stage == 3) {
                reachUp();
            }
            else if (stage == 2) {
                restState();
            }
            else if (stage == 1) {
                closeHand();
            }
            else if (stage == 0) {
                readyToGrab();
            }

            time = currentTimeMillis();
            stage++;
            if (stage > 5) stage = 0;
        }
    }

    public void readyToGrab() {
        // lying flat ready to grab stuff
        subtleServoMove(shoulder, 0.5);
        subtleServoMove(elbow, 0.8);
        subtleServoMove(wrist, 0.5);

        rightClaw.setPosition(0);
        leftClaw.setPosition(1);
    }
    public void closeHand() {
        // grab
        rightClaw.setPosition(0.6);
        leftClaw.setPosition(0.4);
    }
    public void restState() {
        // set to rest state
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);
        // stole these initial positions from mcmuffin the revenge
        subtleServoMove(wrist, 0.575);
        subtleServoMove(shoulder, 0.425);
        subtleServoMove(elbow, 0.5);
        subtleServoMove("lift",0.06);
    }
    public void reachUp() {
        // ready to place in basket (still need to add lift)
        subtleServoMove(shoulder, 0.8);
        subtleServoMove(elbow, 0.5);
        subtleServoMove(wrist, 0.5);

        rightClaw.setPosition(0.6);
        leftClaw.setPosition(0.4);

        subtleServoMove("lift",0.7);
    }
    public void release() {
        rightClaw.setPosition(0);
        leftClaw.setPosition(1);
    }





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
