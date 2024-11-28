package org.firstinspires.ftc.teamcode.Classes;

import static java.lang.System.currentTimeMillis;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ControlClassFiles.subtleServoMoveThread;


/*
 * this class does all steps for outtake for deep robot
 * or at least it will
 *
 * to change which button does what, go to Outtake()
 */

public class DeepOuttake {

    // Servos
    private final Servo outtakeLeft; // outtake slides
    private final Servo outtakeRight;
    private final Servo lid; // open and close the outtake box
    private final Servo outtakeWrist;

    private final Gamepad gamepad;
    private final HardwareMap hardwareMap;

    double recordedTime = 0; // last time steps was run
    private outtakeSteps step = outtakeSteps.LIFT; // current step in sequence

    enum outtakeSteps {
        LIFT,
        RELEASE
    }

    // constructor
    public DeepOuttake(HardwareMap hardwareMap, Gamepad gamepad) {
        // initialize servos
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        lid = hardwareMap.get(Servo.class, "lid");
        outtakeWrist = hardwareMap.get(Servo.class, "idfk");

        // set directions
        outtakeRight.setDirection(Servo.Direction.REVERSE);

        // set instance variable gamepad
        this.gamepad = gamepad;
        this.hardwareMap = hardwareMap;
    }

    // this is the method that should be run by other classes
    public void Outake() {

        // if you press a it does the next step
        if (gamepad.a && (currentTimeMillis() > recordedTime + 1000 && startedTransfer == -1)) {
            nextOuttakeStep();
            recordedTime = currentTimeMillis();
        }

    }

    // runs the next step in the outtake sequence
    private void nextOuttakeStep() {
        if (step == outtakeSteps.LIFT) {
            liftUp();
            step = outtakeSteps.RELEASE;
        }
        else if (step == outtakeSteps.RELEASE) {
            releaseOuttake();
            step = outtakeSteps.LIFT;
        }
    }

    private void liftUp() {
        // outtake left and outtake right extend
        // move("deepouttakeslides",whatever the value is);
        // rotate outtake wrist
    }
    private void releaseOuttake() {
        // open lid
        // lid.setPosition(whatever it is);
    }


    // subtleServoMove slowly moves a servo to a given position
    // it does this by creating a subtleServoMoveThread and starting it
    // (and joining it)
    private void move(Servo servo, double position) {
        subtleServoMoveThread m = new subtleServoMoveThread(servo, position, hardwareMap);
        m.start();

        try {
            m.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void move(String servoType, double position) {
        subtleServoMoveThread m = new subtleServoMoveThread(servoType, position, hardwareMap);
        m.start();
        try {
            m.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }















    //possible transfer code
    double startedTransfer = -1;
    private void transfer() {
        // slideLeft and slideRight in
        // wait for slides then rotate intakePivot in
        // wait for intakePivot then open lid
        // wait for lid then slurpState = SlurpStates.spit;
        // wait for intake then close lid

        /* DO NOT RUN THIS CODE
        move("deepIntakeSlides",1);
        move(intakePivot,0);
        move(lid,1);
        slurpState = SlurpStates.spit;
        startedTransfer = currentTimeMillis();
        */


    }
    private void closeLid() {
        // move(lid,whatever is the close value);
        // move outtake wrist a little?
        // slurpState = SlurpStates.still;
    }

    // and this would be in Outtake
    // if transfer is complete close the lid
    // I dont know the correct timing for this, currently have one sec
        /*else if (startedTransfer + 1000 < currentTimeMillis()) {
            closeLid();
            startedTransfer = -1;
        }*/
}
