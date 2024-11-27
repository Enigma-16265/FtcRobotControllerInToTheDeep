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
        if (gamepad.a && (currentTimeMillis() > recordedTime + 1000 && startedTransfer == -1)) {
            nextIntakeStep();
            recordedTime = currentTimeMillis();
        }
        // if transfer is complete close the lid
        // I dont know the correct timing for this, currently have one sec
        else if (startedTransfer + 1000 < currentTimeMillis()) {
            closeLid();
            startedTransfer = -1;
        }

        // refers to the intake. Slurp means its currently
        // pulling in, spit when pushing out
        // maybe dont really need functions for this but wtv
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

        // increment step. loop when finish sequence
        step++;
        if (step > 3) step = 0;
    }

    private void reachOut() {
        // slideLeft and slideRight out
        /* DO NOT RUN THIS CODE
        move("deepintakeslides",1);
         */
        // start slurping to pull samples in
        slurpState = SlurpStates.slurp;
        // should it stop pulling in when it has a
        // sample? also what about the code for seeing what
        // color the sample is? lorelai was working on that right?
    }
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
    private void liftUp() {
        // outtake left and outtake right extend
        // move("deepouttakeslides",whatever the value is);
        // rotate outtake wrist? i think thats a thing but I didnt
        // see it on the list of servos
    }
    private void releaseOuttake() {
        // open lid
        // lid.setPosition(whatever it is);
    }

    private void slurp() {
        // pull in with intake
        // how do i do this???
        // I think its moving the intake servo a specific amount in the proper
        // direction... it doesnt look like the build team knows how that works anyway
        // intake.setPosition(intake.getPosition+0.1); ???
    }
    private void spit() {
        // spit out with intake
        // how do i do this???
        // intake.setPosition(intake.getPosition-0.1); ???
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
