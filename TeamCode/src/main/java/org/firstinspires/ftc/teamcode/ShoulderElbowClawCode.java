package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/*
 * This class runs the arm control code
 *
 */

public class ShoulderElbowClawCode {

    public Gamepad gamepad1;

    // Servos
    private final Servo    leftClaw;
    private final Servo    rightClaw;
    private final Servo    shoulder;
    private final Servo    elbow;
    private final Servo    wrist;
    private final Servo    leftLift;
    private final Servo    rightLift;

    // Offset Variables
    private double clawOffset = 0;
    private double shoulderOffset = 0;
    private double elbowOffset = 0;
    private double wristOffset = 0;

    // Middle of servo value
    private static final double MID_SERVO   =  0.5 ;

    // Various speeds
    private static final double CLAW_SPEED  = 0.02 ;// sets rate to move servo
    private static final double SHOULDER_SPEED = 0.01;
    private static final double ELBOW_SPEED = 0.01;
    private static final double WRIST_SPEED = 0.01;
    private static final double LIFT_SPEED = 0.01;

    // constructor initializes all servos
    public ShoulderElbowClawCode(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {
        leftClaw  = hardwareMap.get(Servo.class, "lFinger");
        rightClaw = hardwareMap.get(Servo.class, "rFinger");
        shoulder  = hardwareMap.get(Servo.class, "shoulder");
        elbow     = hardwareMap.get(Servo.class, "elbow");
        wrist     = hardwareMap.get(Servo.class, "wrist");
        leftLift = hardwareMap.get(Servo.class, "leftLift");
        rightLift = hardwareMap.get(Servo.class, "rightLift");

        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);
        // stole these initial positions from mcmuffin the revenge
        wrist.setPosition(0.575);
        shoulder.setPosition(0.425);
        elbow.setPosition(0.5);
        leftLift.setPosition(0.06);
        rightLift.setPosition(0.06);

        this.gamepad1 = gamepad1;
    }

    public void runShoulder() {
        // Use gamepad triggers to move shoulder
        // I have no idea what controls would be best so this is kinda a random choice
        shoulderOffset += SHOULDER_SPEED * gamepad1.right_trigger;
        shoulderOffset -= SHOULDER_SPEED * gamepad1.left_trigger;

        // move servo
        shoulderOffset = Range.clip(shoulderOffset, -0.3, 0.3);
        shoulder.setPosition(MID_SERVO + shoulderOffset);
    }

    public void runElbow() {
        // Use gamepad a and y to move elbow
        if (gamepad1.y)
            elbowOffset += ELBOW_SPEED;
        else if (gamepad1.a)
            elbowOffset -= ELBOW_SPEED;

        // move servo
        elbowOffset = Range.clip(elbowOffset, -0.5, 0.5);
        elbow.setPosition(MID_SERVO + elbowOffset);
    }

    public void runWrist() {
        // Use gamepad b and x to move wrist
        if (gamepad1.x)
            wristOffset += WRIST_SPEED;
        else if (gamepad1.b)
            wristOffset -= WRIST_SPEED;

        // move servo
        wristOffset = Range.clip(wristOffset, -0.5, 0.5);
        wrist.setPosition(MID_SERVO + wristOffset);
    }

    public void runClaw() {
        // Use gamepad left & right Bumpers to open and close the claw
        if (gamepad1.dpad_down)
            clawOffset += CLAW_SPEED;
        else if (gamepad1.dpad_up)
            clawOffset -= CLAW_SPEED;

        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        leftClaw.setPosition(MID_SERVO + clawOffset);
        rightClaw.setPosition(MID_SERVO - clawOffset);
    }

    public void runLift() {
        if (gamepad1.left_bumper) {
            leftLift.setPosition(leftLift.getPosition() - LIFT_SPEED);
            rightLift.setPosition(rightLift.getPosition() - LIFT_SPEED);
        }
        else if (gamepad1.right_bumper) {
            leftLift.setPosition(rightLift.getPosition() + LIFT_SPEED);
            rightLift.setPosition(rightLift.getPosition() + LIFT_SPEED);
        }
    }

    //public void grab() {

    //}

    //public String reportInfo() {
    //    return String.format("Shoulder = %d\nElbow = %d\nLeft Claw = %d\nRight Claw = %d\nWrist = %d\nLift = %d", shoulder.getPosition(),elbow.getPosition(),leftClaw.getPosition(),rightClaw.getPosition(),wrist.getPosition(),leftLift.getPosition()));
    //}
}
