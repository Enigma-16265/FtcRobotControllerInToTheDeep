package org.firstinspires.ftc.teamcode.Classes;

import static java.lang.System.currentTimeMillis;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ControlClassFiles.subtleServoMoveThread;


/*
 * this class does all steps for intake and outtake for deep robot
 * or at least it will
 *
 * Possible Robot Names:

 * Best:
     * Sea Cucumber
     * Titanic
     * A-Rray
     * Caution: Wet Floor

 * Kinda Ok
     * Numenor
     * Europa
     * Electronic Coral Reef
     * ALVIN
     * Sea Slug
     * Clipper

 * Mildly Awful
     * Algae
     * Sunken Green Treasure
     * Space Pirate
     * Glub Glub
     * Generally the builders decide that

 * Just No
     * Piracy Machine
     * Jesse Pinkman
     * Shrug
     * Yeah
     * RobuottyMcRobut
 *
 * to change which button does what, go to Intake()
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
    private int step = 0; // current step in sequence

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
        // if transfer is complete close the lid
        // I dont know the correct timing for this, currently have one sec
        /*else if (startedTransfer + 1000 < currentTimeMillis()) {
            closeLid();
            startedTransfer = -1;
        }*/

    }

    // runs the next step in the outtake sequence
    private void nextOuttakeStep() {
        if (step == 0) {
            liftUp();
        }
        else if (step == 1) {
            releaseOuttake();
        }

        // increment step. loop when finish sequence
        step++;
        if (step > 1) step = 0;
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
}
