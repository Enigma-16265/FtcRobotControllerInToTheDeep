package org.firstinspires.ftc.teamcode;

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

public class DeepIntake {

    // Servos
    private final Servo slideLeft; // intake slides
    private final Servo slideRight;
    private final Servo outtakeLeft; // outtake slides
    private final Servo outtakeRight;
    private final Servo lid; // open and close the outtake box
    private final Servo intakePivot; // intake wrist
    private final Servo intake; // slurpy thing

    private final Gamepad gamepad;
    private final HardwareMap hardwareMap;

    double recordedTime = 0; // last time intake steps was run
    private int step = 0; // current step in intake sequence

    private enum SlurpStates {
        slurp,
        spit,
        still
    }
    SlurpStates slurpState = SlurpStates.slurp;

    // constructor
    public DeepIntake(HardwareMap hardwareMap, Gamepad gamepad) {
        // initialize servos
        slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        slideRight = hardwareMap.get(Servo.class, "slideRight");
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        lid = hardwareMap.get(Servo.class, "lid");
        intake = hardwareMap.get(Servo.class, "intake");
        intakePivot = hardwareMap.get(Servo.class, "intakePivot");

        // set directions
        outtakeRight.setDirection(Servo.Direction.REVERSE);
        slideLeft.setDirection(Servo.Direction.REVERSE);

        // set instance variable gamepad
        this.gamepad = gamepad;
        this.hardwareMap = hardwareMap;

    }

    // this is the method that should be run by other classes
    public void Intake() {

        // if you press a it does the next step
        if (gamepad.a && (currentTimeMillis() > recordedTime + 1000)) {
            nextIntakeStep();
            recordedTime = currentTimeMillis();
        }

        // if you press b it goes back a step.
        // this is probably a bad idea
        // what should I do instead? something that resets?
        //if (gamepad.b) {
        //    step--;
        //}

        if (slurpState == SlurpStates.slurp) {
            slurp();
        }
        else if (slurpState == SlurpStates.spit) {
            spit();
        }

    }

    // runs the next step in the intake sequence
    private void nextIntakeStep() {
        if (step == 0) {
            reachOut();
        }
        else if (step == 1) {
            transfer();
        }
        else if (step == 2) {
            liftUp();
        }
        else if (step == 3) {
            releaseOuttake();
        }

        // increment step. lope when finish sequence
        step++;
        if (step > 3) step = 0;
    }

    private void reachOut() {
        // slideLeft and slideRight out
        slurpState = SlurpStates.slurp;
    }
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
        // somehow wait until it spits it out? idk how it works still
        move(lid,0);
         */

        //slurpState = SlurpStates.still?
    }
    private void liftUp() {
        // outtake left and outtake right extend
    }
    private void releaseOuttake() {
        // open lid
    }

    private void slurp() {
        // pull in with intake
        // how do i do this???
    }
    private void spit() {
        // spit out with intake
        // how do i do this???
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

}
