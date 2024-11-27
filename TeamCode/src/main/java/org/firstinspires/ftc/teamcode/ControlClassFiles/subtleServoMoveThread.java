package org.firstinspires.ftc.teamcode.ControlClassFiles;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * A thread to move a servo slowly, because setPosition is more than a little aggressive
 * and I don't want to hurt the servos, especially the lift. Also I feel like I might hit the
 * wrist on something
 *
 * I just realized... I have it moving the servos by getting the current position and incrementing.
 * That's not good. I could accidentally try to set a servo to a value > 1 or < -1... or maybe that
 * was what was happening in the happy dance code. What could I do instead? I could save the value
 * instead of going off of the servo, like mcMuffin does. But that could interfere very annoyingly.
 * Can I synchronise it somehow?
*/

/** @noinspection unused*/
public class subtleServoMoveThread extends Thread {

    // position to move a servo to, and the relative speed to do so
    double position;
    double speed = 1;

    // hardware map for accessing servos
    private final HardwareMap hardwareMap;

    // if previousThread is set to another subtleServoMoveThread it will wait
    // for it to complete before running
    private subtleServoMoveThread previousThread = null;

    // enum for types of servos that can move together
    // (is this necessary? I take string input for this anyway)
    enum servoTypes {
        LIFT,
        CLAW
    }

    // if servo is set, code will slowly move that particular servo.
    // if servoType is set, it will move that servo category together
    Servo servo;
    servoTypes servoType;


    // overloaded constructors
    // constructors always require a position to move to, and a reference to the
    // hardware map. They can either take a particular servo or a type of servo.
    // can also take speed, default is 1, as well as a reference to a thread to wait for
    // (I just kept adding more of these as I needed them lol)
    public subtleServoMoveThread(Servo whatServo, double where, HardwareMap hardwareMap) {
        this.servo = whatServo;
        this.position = where;
        this.hardwareMap = hardwareMap;
    }
    public subtleServoMoveThread(String type, double where, HardwareMap hardwareMap) {
        this.servoType = servoTypes.valueOf(type.toUpperCase());
        this.position = where;
        this.hardwareMap = hardwareMap;
    }
    public subtleServoMoveThread(String type, double where, subtleServoMoveThread previous, HardwareMap hardwareMap) {
        this(type, where, hardwareMap);
        this.previousThread = previous;
    }
    public subtleServoMoveThread(Servo whatServo, double where, double speed, HardwareMap hardwareMap) {
        this(whatServo, where, hardwareMap);
        this.speed = speed;
    }
    public subtleServoMoveThread(String type, double where, double speed, HardwareMap hardwareMap) {
        this(type, where, hardwareMap);
        this.speed = speed;
    }
    public subtleServoMoveThread(String type, double where, double speed, subtleServoMoveThread previous, HardwareMap hardwareMap) {
        this(type, where, speed, hardwareMap);
        this.previousThread = previous;
    }




    // runs concurrently
    public void run() {
        // if previousThread has been set, wait for it to complete before moving
        // ive used this to move lifts up then down etc
        if (previousThread != null) {
            try {
                previousThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // if there isn't a particular servo set, run type of servo
        if (servo == null) {
            runType();
        }

        // if you're trying to move only one lift, you get an error. I don't trust myself
        // (this is because moving only one lift will break the robot)
        else if (servo == hardwareMap.get(Servo.class, "leftLift") || servo == hardwareMap.get(Servo.class, "rightLift")) {
            throw new IllegalArgumentException("You cannot move only one lift");
        }

        // otherwise move the given servo to the given position
        else {
            runServo(servo, position);
        }

    }

    /** @noinspection SynchronizationOnLocalVariableOrMethodParameter*/ // slowly moves a single servo to a specific goal
    // pre: Servo is defined and not a lift
    private void runServo(Servo servo, double goal) {
        synchronized(servo) {
            // repeatedly run while loop until the servo's value is close enough to goal
            while (servo.getPosition() < goal - 0.03 || servo.getPosition() > goal + 0.03) {
                // if servo value is greater than goal, make it smaller
                if (servo.getPosition() > goal) {
                    servo.setPosition(servo.getPosition() - 0.01 * speed);
                }
                // if servo value is smaller than goal, make it bigger
                else if (servo.getPosition() < goal) {
                    servo.setPosition(servo.getPosition() + 0.01 * speed);
                }
                // make thread sleep for 50 mils so other stuff can run
                try {
                    //noinspection BusyWait
                    sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // do actions related to a particular servo type
    private void runType() {
        // if servo type is lift run both lifts simultaneously
        if (servoType == servoTypes.LIFT) {
            Servo lift1 = hardwareMap.get(Servo.class, "leftLift");
            Servo lift2 = hardwareMap.get(Servo.class, "rightLift");
            synchronized(lift1) {
                synchronized(lift2) {
                    moveTwoServos(lift1, lift2, position);
                }
            }
        }
        // if servo type is claw, run both claws in opposite directions
        /*else if (servoType == servoTypes.CLAW) {
            // deal with this later
            // wait why did i say that whats wrong with it?
            // ahh I see this only works if you are opening or closing claw,
            // not for values in between. I'll deal with it later
            double rightPosition;
            if (position == 1) {rightPosition = 0;}
            else if (position == 0.4) {rightPosition = 0.6;}
            else {
                throw new IllegalArgumentException("Only open or close claw");
            }

            // wait this code will only move one then the other. Need to fix.
            runServo(hardwareMap.get(Servo.class, "lFinger"), position);
            runServo(hardwareMap.get(Servo.class, "rFinger"), rightPosition);

        }*/
    }

    // move two servos at once in the same direction
    private void moveTwoServos(Servo a, Servo b, double goal) {
        // run repeatedly until close enough to goal
        while (a.getPosition() < goal - 0.03 || a.getPosition() > goal + 0.03) {
            // if position is greater than goal, decrease it
            if (a.getPosition() > goal) {
                a.setPosition(a.getPosition() - 0.01 * speed);
                b.setPosition(a.getPosition() - 0.01 * speed);
            }


            // if position is less than goal, increase it
            else if (a.getPosition() < goal) {
                a.setPosition(a.getPosition() + 0.01 * speed);
                b.setPosition(a.getPosition() + 0.01 * speed);
            }
            // make this thread sleep 50 mils
            try {
                //noinspection BusyWait
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
