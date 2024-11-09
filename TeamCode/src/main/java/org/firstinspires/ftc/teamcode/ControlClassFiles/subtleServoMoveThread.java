package org.firstinspires.ftc.teamcode.ControlClassFiles;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * A thread to move a servo slowly, because setPosition is more than a little aggressive
 *
 * Maybe should edit it to ask for a speed as well
*/

/** @noinspection unused*/
public class subtleServoMoveThread extends Thread {

    // instance variables
    enum servoTypes {
        LIFT,
        CLAW
    }

    private final HardwareMap hardwareMap;
    private final subtleServoMoveThread previousThread;
    double position;
    double speed = 1;

    // usually, either the servo or the servoType should be null
    Servo servo;
    servoTypes servoType;


    // overloaded constructor
    public subtleServoMoveThread(Servo whatServo, double where, HardwareMap hardwareMap) {
        this.servo = whatServo;
        this.position = where;
        this.hardwareMap = hardwareMap;
        previousThread = null;
    }
    public subtleServoMoveThread(String type, double where, HardwareMap hardwareMap) {
        this.servoType = servoTypes.valueOf(type.toUpperCase());

        this.position = where;
        this.hardwareMap = hardwareMap;
        previousThread = null;
    }
    /** @noinspection unused*/
    public subtleServoMoveThread(String type, double where, subtleServoMoveThread previous, HardwareMap hardwareMap) {
        this.servoType = servoTypes.valueOf(type.toUpperCase());

        this.position = where;
        this.hardwareMap = hardwareMap;
        this.previousThread = previous;
    }


    public subtleServoMoveThread(Servo whatServo, double where, double speed, HardwareMap hardwareMap) {
        this.servo = whatServo;
        this.position = where;
        this.speed = speed;
        this.hardwareMap = hardwareMap;
        previousThread = null;
    }
    public subtleServoMoveThread(String type, double where, double speed, HardwareMap hardwareMap) {
        this.servoType = servoTypes.valueOf(type.toUpperCase());
        this.position = where;
        this.speed = speed;
        this.hardwareMap = hardwareMap;
        previousThread = null;
    }
    public subtleServoMoveThread(String type, double where, double speed, subtleServoMoveThread previous, HardwareMap hardwareMap) {
        this.servoType = servoTypes.valueOf(type.toUpperCase());

        this.position = where;
        this.hardwareMap = hardwareMap;
        this.previousThread = previous;
        this.speed = speed;
    }


    // runs concurrently
    public void run() {
        if (previousThread != null) {
            try {
                previousThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (servo == null) {
            runType();
        }
        else if (servo == hardwareMap.get(Servo.class, "leftLift") || servo == hardwareMap.get(Servo.class, "rightLift")) {
            // if youre trying to move only one lift, you get an error. I dont trust myself lmao
            throw new IllegalArgumentException("You cannot move only one lift");
        }
        else {
            runServo(servo, position);
        }

    }

    // slowly moves a single servo to a specific position
    // pre: Servo is defined and not a lift
    private void runServo(Servo servo, double position) {
        while (servo.getPosition() < position-0.03 || servo.getPosition() > position+0.03) {
            if (servo.getPosition() > position) {
                servo.setPosition(servo.getPosition() - 0.01 * speed);
            }
            else if (servo.getPosition() < position) {
                servo.setPosition(servo.getPosition() + 0.01 * speed);
            }
            try {
                //noinspection BusyWait
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // do actions related to a particular servo type
    private void runType() {
        if (servoType == servoTypes.LIFT) {
            Servo lift1 = hardwareMap.get(Servo.class, "leftLift");
            Servo lift2 = hardwareMap.get(Servo.class, "rightLift");
            moveTwoServos(lift1, lift2);
        }
        else if (servoType == servoTypes.CLAW) {
            // deal with this later
            double rightPosition = 0;
            if (position == 1) {rightPosition = 0;}
            else if (position == 0.4) {rightPosition = 0.6;}

            runServo(hardwareMap.get(Servo.class, "lFinger"), position);
            runServo(hardwareMap.get(Servo.class, "rFinger"), rightPosition);

        }
    }

    // move two servos at once in the same direction
    private void moveTwoServos(Servo a, Servo b) {
        while (a.getPosition() < position-0.03 || a.getPosition() > position+0.03) {
            if (a.getPosition() > position) {
                a.setPosition(a.getPosition() - 0.01 * speed);
                b.setPosition(a.getPosition() - 0.01 * speed);
            }
            else if (a.getPosition() < position) {
                a.setPosition(a.getPosition() + 0.01 * speed);
                b.setPosition(a.getPosition() + 0.01 * speed);
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
