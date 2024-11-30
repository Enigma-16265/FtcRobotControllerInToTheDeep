package org.firstinspires.ftc.teamcode.Classes;

import static java.lang.System.currentTimeMillis;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/*
 * this class does all steps for outtake for deep robot
 * or at least it will
 *
 * to change which button does what, go to Outtake()
 */

public class DeepOuttake {

    private final Servo outtakeLeft; // outtake wrist i believe?
    private final Servo outtakeRight;
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Servo lid; // open and close the outtake box

    private final Gamepad gamepad;

    double recordedTime = 0; // last time steps was run
    private outtakeSteps step = outtakeSteps.WRIST; // current step in sequence

    enum outtakeSteps {
        WRIST,
        RELEASE
    }

    // constructor
    public DeepOuttake(HardwareMap hardwareMap, Gamepad gamepad) {
        // initialize servos
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        lid = hardwareMap.get(Servo.class, "lid");
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");

        lid.setDirection(Servo.Direction.REVERSE);

        // set instance variable gamepad
        this.gamepad = gamepad;
    }

    // this is the method that should be run by other classes
    public void outtake() {

        // if you press a it does the next step
        if (gamepad.a && (currentTimeMillis() > recordedTime + 1000)) {
            nextOuttakeStep();
            recordedTime = currentTimeMillis();
        }

        // control the lifts with some other buttons
        if (gamepad.dpad_up) {
            rightLift.setPower(0.1);
            leftLift.setPower(0.1);
        }
        else if (gamepad.dpad_down) {
            rightLift.setPower(-0.1);
            leftLift.setPower(-0.1);
        }
        else {
            rightLift.setPower(0);
            leftLift.setPower(0);
        }

        if (gamepad.dpad_left) {
            outtakeRight.setPosition(outtakeRight.getPosition()+0.1);
            outtakeLeft.setPosition(outtakeLeft.getPosition()+0.1);
        }
        else if (gamepad.dpad_right) {
            outtakeRight.setPosition(outtakeRight.getPosition()-0.1);
            outtakeLeft.setPosition(outtakeLeft.getPosition()-0.1);
        }

    }

    // runs the next step in the outtake sequence
    private void nextOuttakeStep() {
        if (step == outtakeSteps.WRIST) {
            outtakeRight.setPosition(0.5);
            outtakeLeft.setPosition(0.5);
            step = outtakeSteps.RELEASE;
        }
        else if (step == outtakeSteps.RELEASE) {
            lid.setPosition(0.6);
            step = outtakeSteps.WRIST;
        }
    }
}
