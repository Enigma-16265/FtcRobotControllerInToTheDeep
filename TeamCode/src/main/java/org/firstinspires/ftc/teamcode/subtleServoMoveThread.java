package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * A thread to move a servo slowly, because setPosition is more than a little aggressive
 *
 * Maybe should edit it to ask for a speed as well
*/

public class subtleServoMoveThread extends Thread {

    // instance variables
    enum servoTypes {
        LIFT
    }

    private final HardwareMap hardwareMap;
    double position;

    // usually, either the servo or the servoType should be null
    Servo servo;
    servoTypes servoType;


    // overridden constructor
    public subtleServoMoveThread(Servo whatServo, double where, HardwareMap hardwareMap) {
        this.servo = whatServo;
        this.position = where;
        this.hardwareMap = hardwareMap;
    }
    public subtleServoMoveThread(String type, double where, HardwareMap hardwareMap) {
        if (type.equalsIgnoreCase("LIFT")) {
            servoType = servoTypes.LIFT;
        }

        this.position = where;
        this.hardwareMap = hardwareMap;
    }


    // runs concurrently
    public void run() {
        if (servo == null) {
            runType();
        }
        else if (servo == hardwareMap.get(Servo.class, "leftLift") || servo == hardwareMap.get(Servo.class, "rightLift")) {
            // if youre trying to move only one lift, you get an error. I dont trust myself lmao
            throw new IllegalArgumentException("You cannot move only one lift");
        }
        else {
            runServo();
        }

    }

    private void runServo() {
        while (servo.getPosition() < position-0.03 || servo.getPosition() > position+0.03) {
            if (servo.getPosition() > position) {
                servo.setPosition(servo.getPosition() - 0.01);
            }
            else if (servo.getPosition() < position) {
                servo.setPosition(servo.getPosition() + 0.01);
            }
            try {
                //noinspection BusyWait
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void runType() {
        if (servoType == servoTypes.LIFT) {
            Servo lift1 = hardwareMap.get(Servo.class, "leftLift");
            Servo lift2 = hardwareMap.get(Servo.class, "rightLift");
            moveTwoServos(lift1, lift2);
        }
    }

    private void moveTwoServos(Servo a, Servo b) {
        // you guessed it, move two servos at once. (in the same direction)
        while (a.getPosition() < position-0.03 || a.getPosition() > position+0.03) {
            if (a.getPosition() > position) {
                a.setPosition(a.getPosition() - 0.01);
                b.setPosition(a.getPosition() - 0.01);
            }
            else if (a.getPosition() < position) {
                a.setPosition(a.getPosition() + 0.01);
                b.setPosition(a.getPosition() + 0.01);
            }
            try {
                //noinspection BusyWait
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
