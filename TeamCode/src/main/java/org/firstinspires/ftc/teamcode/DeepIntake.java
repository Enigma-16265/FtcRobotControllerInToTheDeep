package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DeepIntake {

    // servos
    private final Servo slideLeft;
    private final Servo slideRight;
    private final Servo outtakeLeft;
    private final Servo outtakeRight;
    private final Servo lid; // open and close the little box thing
    private final Servo intakePivot;
    private final Servo intake;

    private final Gamepad gamepad;

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

    public void Intake() {
        if (gamepad.a) {
            nextIntakeStep();
        }
        if (gamepad.b) {
            step--;
        }

    }

    private int step = 0;
    private void nextIntakeStep() {
        if (step == 0) {
            // first step
        }
        else if (step == 1) {
            // second step
        }
        // etc

        step++;
        if (step > 1) step = 0;
    }

    // steps:
    // grab the thing

    // pull in
    // transfer from intake to outtake
    // close outtake

    // lift up
    // release

}
