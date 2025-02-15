package org.firstinspires.ftc.teamcode.Link.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


/*
 * this class does all steps for outtake for deep robot
 * or at least it will
 *
 * to change which button does what, go to Outtake()
 */

public class DeepOuttake {

    // lifts
    // all servos are controlled through smart servo anyway
    private final DcMotor rightLift;
    private final DcMotor leftLift;

    // instance variables
    private final Gamepad gamepad;
    private final HardwareMap hardwareMap;

    // constructor
    public DeepOuttake(HardwareMap hardwareMap, Gamepad gamepad) {
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
            leftLift.setPower(1);
        } else if (gamepad.dpad_down) {
            rightLift.setPower(-0.49);
            leftLift.setPower(-0.49);
        } else {
            rightLift.setPower(0);
            leftLift.setPower(0);
        }


    }
}
