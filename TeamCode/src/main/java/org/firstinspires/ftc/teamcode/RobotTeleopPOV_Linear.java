/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Robot: Teleop POV", group="Robot")
//@Disabled
public class RobotTeleopPOV_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;
    public DcMotor leftHang = null;
    public DcMotor rightHang = null;


    double clawOffset = 0;


    public static final double CLAW_SPEED  = 0.02 ;                 // sets rate to move servo
    public static final double TRIGGER_THRESHOLD = 0.5;
    public static final double LEFT_FINGER_GRIP = 0.64;
    public static final double RIGHT_FINGER_GRIP = 0.331;
    public static final double LEFT_FINGER_INTAKE = 1;
    public static final double RIGHT_FINGER_INTAKE = 0;
    public static final double SERVO_TOLERANCE = 0.01;

    // rev blinkin driver
    RevBlinkinLedDriver leftLEDSBlinkin;
    RevBlinkinLedDriver rightLEDSBlinkin;


    // sensors
    private RevTouchSensor leftUpper;
    private RevTouchSensor rightUpper;
    private RevTouchSensor leftLower;
    private RevTouchSensor rightLower;

    // servos

    private Servo leftFinger;
    private Servo rightFinger;
    private Servo wrist;

    static class IntakePosition {
        double wristPosition;
    }

    private enum intakeState {
        IDLE,
        MOVING_ELBOW,
    }

    private enum scoreState {
        IDLE,
    }

    private enum driveState {
        IDLE,
        MOVING_WRIST,
    }

    private driveState currentDriveState = driveState.IDLE;

    private scoreState currentScoreState = scoreState.IDLE;

    private intakeState currentIntakeState = intakeState.IDLE;
    private IntakePosition activeIntakePosition = null;

    private boolean isServoAtPosition(Servo servo, double targetPosition, double tolerance) {
        double currentPosition = servo.getPosition();
        double normalizedTarget = Range.clip(targetPosition, 0.0, 1.0); // Ensure target is within valid range
        double normalizedCurrent = Range.clip(currentPosition, 0.0, 1.0); // Ensure current position is within valid range

        return Math.abs(normalizedCurrent - normalizedTarget) < tolerance;
    }

    private void hangCode() {
        // hanging
        leftHang  = hardwareMap.get(DcMotor.class, "leftHang");
        rightHang  = hardwareMap.get(DcMotor.class, "rightHang");

        leftLEDSBlinkin = hardwareMap.get(RevBlinkinLedDriver.class, "leftLEDS"); // Adjust the name as per your configuration
        rightLEDSBlinkin = hardwareMap.get(RevBlinkinLedDriver.class, "rightLEDS"); // Adjust the name as per your configuration

        leftUpper = hardwareMap.get(RevTouchSensor.class, "leftUpper");
        rightUpper = hardwareMap.get(RevTouchSensor.class, "rightUpper");
        leftLower = hardwareMap.get(RevTouchSensor.class, "leftLower");
        rightLower = hardwareMap.get(RevTouchSensor.class, "rightLower");

        if (gamepad1.right_trigger > TRIGGER_THRESHOLD){
           leftLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
           rightLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            if (!leftUpper.isPressed()) {
                leftHang.setDirection(DcMotor.Direction.FORWARD);
                leftHang.setPower(.9);
            }
            if (!rightUpper.isPressed()) {
                rightHang.setDirection(DcMotor.Direction.FORWARD);
                rightHang.setPower(.9);
            }
        } else if (gamepad1.left_trigger > TRIGGER_THRESHOLD){
           leftLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
           rightLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
            if (!leftLower.isPressed()) {
                leftHang.setDirection(DcMotor.Direction.REVERSE);
                leftHang.setPower(.9);
            }
            if (!rightLower.isPressed()) {
                rightHang.setDirection(DcMotor.Direction.REVERSE);
                rightHang.setPower(.9);
            }
        } else {
            leftHang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            leftHang.setPower(0);
            rightHang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rightHang.setPower(0);
        }
    }

    private void driveCode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;
        double strafe;
        double leftBackPower;
        double rightBackPower;

        leftFrontDrive  = hardwareMap.get(DcMotor.class, "leftFront");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "leftBack");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        drive = -gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x;
        strafe = gamepad1.left_stick_x;

        left = drive + turn + strafe;
        leftBackPower = drive + turn - strafe;
        right = drive - turn - strafe;
        rightBackPower = drive + turn + strafe;

        // Normalize the values so neither exceed +/- 1.0
        max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0)
        {
            left /= max;
            right /= max;
        }

        leftFrontDrive.setPower(left);
        leftBackDrive.setPower(leftBackPower);
        rightFrontDrive.setPower(right);
        rightBackDrive.setPower(rightBackPower);

        if (gamepad1.right_bumper)
            clawOffset += CLAW_SPEED;
        else if (gamepad1.left_bumper)
            clawOffset -= CLAW_SPEED;

        clawOffset = Range.clip(clawOffset, -0.5, 0.5);

        telemetry.addData("claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("left",  "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.update();

    }

   /* private void grapdropFunction() {

        leftLEDSBlinkin = hardwareMap.get(RevBlinkinLedDriver.class, "leftLEDS"); // Adjust the name as per your configuration
        rightLEDSBlinkin = hardwareMap.get(RevBlinkinLedDriver.class, "rightLEDS"); // Adjust the name as per your configuration

        if (gamepad1.right_bumper && gamepad1.dpad_up && (currentScoreState == scoreState.IDLE)) {
            leftFinger.setPosition(LEFT_FINGER_GRIP);
            rightFinger.setPosition(RIGHT_FINGER_GRIP);
            leftLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_MEDIUM );
            rightLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_MEDIUM );;
        } else if (gamepad1.right_bumper && gamepad1.dpad_left && (currentScoreState == scoreState.IDLE)) {
            leftFinger.setPosition(LEFT_FINGER_GRIP);
            leftLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_MEDIUM );
        } else if (gamepad1.right_bumper && gamepad1.dpad_right && (currentScoreState == scoreState.IDLE)) {
            rightFinger.setPosition(RIGHT_FINGER_GRIP);
            rightLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_MEDIUM );
        } else if (gamepad1.right_bumper && gamepad1.dpad_down && (currentScoreState == scoreState.IDLE)) {
            leftFinger.setPosition(LEFT_FINGER_INTAKE);
            rightFinger.setPosition(RIGHT_FINGER_INTAKE);
            leftLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
            rightLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        }
    }

    */

    private void grapDropFunction() {

        leftLEDSBlinkin = hardwareMap.get(RevBlinkinLedDriver.class, "leftLEDS");
        rightLEDSBlinkin = hardwareMap.get(RevBlinkinLedDriver.class, "rightLEDS");

        leftFinger = hardwareMap.get(Servo.class, "lFinger");
        rightFinger = hardwareMap.get(Servo.class, "rFinger");

        if (gamepad1.right_bumper && gamepad1.dpad_up) {
            leftFinger.getPosition();
            if (leftFinger.getPosition() != LEFT_FINGER_GRIP) {
                leftFinger.setPosition(LEFT_FINGER_GRIP);
            }
            rightFinger.getPosition();
            if(leftFinger.getPosition() != RIGHT_FINGER_GRIP) {
                rightFinger.setPosition(RIGHT_FINGER_GRIP);
            }
            leftLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_MEDIUM);
            rightLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_MEDIUM);
        }
        else if (gamepad1.right_bumper && gamepad1.dpad_down) {
            leftFinger.getPosition();
            if (leftFinger.getPosition() != LEFT_FINGER_INTAKE) {
                leftFinger.setPosition(LEFT_FINGER_INTAKE);
            }
            rightFinger.getPosition();
            if (rightFinger.getPosition() != RIGHT_FINGER_INTAKE) {
                rightFinger.setPosition(RIGHT_FINGER_INTAKE);
            }
            leftLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
            rightLEDSBlinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        }
    }

    private void handleIntakeSequence(IntakePosition intakePos) {
        switch (currentDriveState) {
            case MOVING_WRIST:
                // Move the wrist to intake position
                //moveServoGradually(wrist, intakePos.wristPosition);
                wrist.setPosition(intakePos.wristPosition);
                //moveServoWithTrapezoidalVelocity(wrist, intakePos.wristPosition, intakePos.accelerationMax, intakePos.velocityMax);
                if (isServoAtPosition(wrist, intakePos.wristPosition, SERVO_TOLERANCE)) {
                    currentIntakeState = intakeState.MOVING_ELBOW;
                }
                break;
        }
    }

    private void intakeFunction() {
        if (activeIntakePosition != null) {
            handleIntakeSequence(activeIntakePosition);
        }
    }


    @Override
    public void runOpMode() {

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");    //
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        while (opModeIsActive()) {
            driveCode();
            hangCode();
            grapDropFunction();
            intakeFunction();
            sleep(50);
        }
    }





