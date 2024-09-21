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

/*
 * This OpMode executes a POV Game style Teleop for a direct drive robot
 * The code is structured as a LinearOpMode
 *
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the arm using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

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

    private enum scoreState {
        IDLE,
        MOVING_LIFT,
        MOVING_SHOULDER,
        MOVING_WRIST,
        MOVING_ELBOW,
        COMPLETED
    }
    private scoreState currentScoreState = scoreState.IDLE;


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

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
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

    private void grapdropFunction() {

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

    @Override
    public void runOpMode() {

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");    //
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        while(opModeIsActive()){
            driveCode();
            hangCode();
            grapdropFunction();
        }

        sleep(50);
    }
}
