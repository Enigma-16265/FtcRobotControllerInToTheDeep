package org.firstinspires.ftc.teamcode.Link.Classes;

import static java.lang.System.currentTimeMillis;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/*
 * this class does all steps for outtake for deep robot
 * or at least it will
 *
 * to change which button does what, go to Outtake()
 */

public class DeepOuttake2 {

    // lifts
    // all servos are controlled through smart servo anyway
    private final DcMotor rightLift;
    private final DcMotor leftLift;

    // instance variables
    private final Gamepad gamepad;
    private final HardwareMap hardwareMap;

    // constructor
    public DeepOuttake2(HardwareMap hardwareMap, Gamepad gamepad) {
        // initialize servos
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");


        // reverse necessary motor
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);


        // set instance variables
        this.gamepad = gamepad;
        this.hardwareMap = hardwareMap;
    }

    // this is the method that should be run by other classes
    public void outtake() {

        // control the lifts with some other buttons
        if (gamepad.dpad_up) {

            rightLift.setPower(1);
            rightLift.setTargetPosition(6188);
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if (gamepad.dpad_down) {

            rightLift.setPower(1);
            rightLift.setTargetPosition(0);
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            // runs the next step in the outtake sequence
        }
    }
}