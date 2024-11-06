package org.firstinspires.ftc.teamcode.ControlClassFiles;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * A version of McMuffin tuner code that has been turned into a class
 * callable in another opmode
 *
 */

/** @noinspection unused*/
public class RosesMcMuffinClass {

    // gamepad
    Gamepad gamepad1;

    // Servos
    Servo wrist;
    Servo elbow;
    Servo leftFinger;
    Servo rightFinger;
    Servo shoulder;
    Servo leftLift;
    Servo rightLift;

    // speed
    double speedAmount = 0.01;

    // types of servos
    enum ServoTypes{
        SHOULDER,
        ELBOW,
        FINGER,
        WRIST,
        LIFT
    }
    ServoTypes which = ServoTypes.SHOULDER;

    // constructor
    public RosesMcMuffinClass(HardwareMap hardwareMap, Gamepad gamepad1) {
        // set gamepad
        this.gamepad1 = gamepad1;

        // initialize servos
        wrist = hardwareMap.get(Servo.class, "wrist");
        elbow = hardwareMap.get(Servo.class, "elbow");
        leftFinger = hardwareMap.get(Servo.class, "lFinger");
        rightFinger = hardwareMap.get(Servo.class, "rFinger");
        shoulder = hardwareMap.get(Servo.class, "shoulder");
        rightLift = hardwareMap.get(Servo.class, "rightLift");
        leftLift = hardwareMap.get(Servo.class, "leftLift");

        // set directions
        shoulder.setDirection(Servo.Direction.REVERSE);
        wrist.setDirection(Servo.Direction.REVERSE);

        // set initial positions
        wrist.setPosition(0.575);
        shoulder.setPosition(0.425);
        elbow.setPosition(0.5);
        rightFinger.setPosition(0.27);
        leftFinger.setPosition(0.74);
        leftLift.setPosition(0.06);
        rightLift.setPosition(0.06);
    }

    // this is the function that should be called in other classes
    public void run() {
        setServo();
        masterTuner();
    }

    // moves the currently selected servo
    private void masterTuner() {
        if (gamepad1.left_bumper) {
            if (which == ServoTypes.SHOULDER) {
                shoulder.setPosition(shoulder.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.ELBOW) {
                elbow.setPosition(elbow.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.FINGER) {
                leftFinger.setPosition(leftFinger.getPosition() - speedAmount);
                rightFinger.setPosition(rightFinger.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.WRIST) {
                wrist.setPosition(wrist.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.LIFT) {
                leftLift.setPosition(leftLift.getPosition() - speedAmount);
                rightLift.setPosition(rightLift.getPosition() - speedAmount);
            }
        }
        else if (gamepad1.right_bumper) {
            if (which == ServoTypes.SHOULDER) {
                shoulder.setPosition(shoulder.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.ELBOW) {
                elbow.setPosition(elbow.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.FINGER) {
                leftFinger.setPosition(leftFinger.getPosition() + speedAmount);
                rightFinger.setPosition(rightFinger.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.WRIST) {
                wrist.setPosition(wrist.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.LIFT) {
                leftLift.setPosition(rightLift.getPosition() + speedAmount);
                rightLift.setPosition(rightLift.getPosition() + speedAmount);
            }
        }
    }

    // changes which servo is selected
    private void setServo() {
        if (gamepad1.y) {
            which = ServoTypes.SHOULDER;
        }
        else if (gamepad1.x) {
            which = ServoTypes.ELBOW;
        }
        else if (gamepad1.b) {
            which = ServoTypes.WRIST;
        }
        else if (gamepad1.a) {
            which = ServoTypes.LIFT;
        }
        else if (gamepad1.dpad_right) {
            which = ServoTypes.FINGER;
        }
    }
}
