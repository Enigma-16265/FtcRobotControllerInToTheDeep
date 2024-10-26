package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * A version of McMuffin tuner code that has been turned into a class
 * callable in another robot teleop thing
 *
 */

public class RosesMcMuffinClass {

    Gamepad gamepad1;
    Servo wrist;
    Servo elbow;
    Servo leftFinger;
    Servo rightFinger;
    Servo shoulder;
    Servo leftLift;
    Servo rightLift;

    double speedAmount = 0.01;
    //double LiftLeftOffset = -.05;

    enum ServoTypes{
        SHOULDER,
        ELBOW,
        FINGER,
        WRIST,
        LIFT
    }
    ServoTypes which = ServoTypes.SHOULDER;

    public RosesMcMuffinClass(HardwareMap hardwareMap, Gamepad gamepad1) {
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
    public void run() {
        setServo();
        masterTuner();
        setExtra();
    }

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
        //telemetry.addData("Selected Servo = ", which.toString());
    }
    private void setExtra() {
        if (gamepad1.dpad_left) {
            which = ServoTypes.FINGER;
        }
    }

    /** @noinspection unused*/
    public double getPosition(String what) {
        switch (what) {
            case "shoulder":
                return shoulder.getPosition();
            case "wrist":
                return wrist.getPosition();
            case "elbow":
                return elbow.getPosition();
            case "rightFinger":
                return rightFinger.getPosition();
            case "leftFinger":
                return leftFinger.getPosition();
            case "rightLift":
                return rightLift.getPosition();
            case "leftLift":
                return leftLift.getPosition();
            default:
                throw new IllegalStateException("Unexpected value: " + what);
        }
    }
}
