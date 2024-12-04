package org.firstinspires.ftc.teamcode.deepBot.Classes;

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

public class DeepOuttake {

    // lifts
    // all servos are controlled through smart servo anyway
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Servo outtakeRight;
    private final Servo outtakeLeft;

    // instance variables
    private final Gamepad gamepad;
    private final HardwareMap hardwareMap;

    // control variables
    boolean lidToggle = false;
    boolean yWasPressed = false;

    double recordedTime = 0; // last time steps was run
    private outtakeSteps step = outtakeSteps.CLOSE; // current step in sequence

    enum outtakeSteps {
        CLOSE,
        WRIST,
        RELEASE,
        RETURN
    }

    // constructor
    public DeepOuttake(HardwareMap hardwareMap, Gamepad gamepad) {
        // initialize servos
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");

        // reverse necessary motor
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);



        // set instance variables
        this.gamepad = gamepad;
        this.hardwareMap = hardwareMap;
    }

    // this is the method that should be run by other classes
    public void outtake() {

        if (gamepad.y && (yWasPressed == false)) {
            if (lidToggle == false) {
                lidToggle = true;
                SmartServo.setSmartPos(hardwareMap,"lid", 0.6);
            }
            if (lidToggle == true) {
                lidToggle = false;
                SmartServo.setSmartPos(hardwareMap,"lid", 0.1);
            }
        }

        // if you press a it does the next step
        if (gamepad.a && (currentTimeMillis() > recordedTime + 1000)) {
            nextOuttakeStep();
            recordedTime = currentTimeMillis();
        }

        // control the lifts with some other buttons
        if (gamepad.dpad_up) {
            rightLift.setPower(1);
            leftLift.setPower(1);
        }
        else if (gamepad.dpad_down) {
            rightLift.setPower(-0.5);
            leftLift.setPower(-0.5);
        }
        else {
            rightLift.setPower(0);
            leftLift.setPower(0);
        }

        if (gamepad.dpad_left) {
            SmartServo.setSmartPos(hardwareMap,"outtakeRight",outtakeRight.getPosition()+0.1);
            SmartServo.setSmartPos(hardwareMap,"outtakeLeft",outtakeLeft.getPosition()+0.1);
        }
        else if (gamepad.dpad_right) {
            SmartServo.setSmartPos(hardwareMap,"outtakeRight",outtakeRight.getPosition()-0.1);
            SmartServo.setSmartPos(hardwareMap,"outtakeLeft",outtakeLeft.getPosition()-0.1);
        }

        yWasPressed = gamepad.y;

    }

    // runs the next step in the outtake sequence
    private void nextOuttakeStep() {
        if (step == outtakeSteps.CLOSE) {
            // close lid, move to next step
            SmartServo.setSmartPos(hardwareMap,"lid", 0);
            step = outtakeSteps.WRIST;
        }
        else if (step == outtakeSteps.WRIST) {
            // make sure lid is closed, turn wrist, move to next step
            SmartServo.setSmartPos(hardwareMap,"lid", 0);
            SmartServo.setSmartPos(hardwareMap,"outtakeRight",1);
            SmartServo.setSmartPos(hardwareMap,"outtakeLeft",1);
            step = outtakeSteps.RELEASE;
        }
        else if (step == outtakeSteps.RELEASE) {
            // open lid, move to next step
            SmartServo.setSmartPos(hardwareMap,"lid",0.6);
            step = outtakeSteps.RETURN;
        }
        else if (step == outtakeSteps.RETURN) {
            // return to transfer position
            SmartServo.setSmartPos(hardwareMap,"outtakeRight", 0);
            SmartServo.setSmartPos(hardwareMap,"outtakeLeft", 0);
            SmartServo.setSmartPos(hardwareMap,"lid", 0.6);
            step = outtakeSteps.CLOSE;
        }
    }
}