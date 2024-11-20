package org.firstinspires.ftc.teamcode;

import static java.lang.System.currentTimeMillis;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/*
 * this class does all steps for intake and outtake for deep robot
 * or at least it will
 *
 * to change which button does what, go to Intake()
 *
 * How to slurp in with intake? how does it work??? what???
 * Should slurping have a stationary state?
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

    double recordedTime = 0; // last time intake steps was run
    boolean slurping = true; // whether or not intake is currently intaking (slurping)
    private int step = 0; // current step in intake sequence

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

        if (slurping) {
            slurp();
        }
        else {
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
        slurping = true;
    }
    private void transfer() {
        // slideLeft and slideRight in
        // wait for slides then rotate intakePivot in
        // wait for intakePivot then open lid
        // wait for lid then slurping = false;
        // wait for intake then close lid

        // reset slurping?
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
}
