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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Link.Classes.SmartServo;


@Autonomous(name="JankierAuto", group="Robot")
public class NinteenDollars extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor         leftDrive   = null;
    private DcMotor         rightDrive  = null;
    private DcMotor rightLift;
    private DcMotor leftLift;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;
    private Servo lid;

    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    private void left() {
        leftFront.setPower(-0.5);
        rightBack.setPower(-0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(0.5);
    }
    private void right() {
        leftFront.setPower(0.5);
        rightBack.setPower(0.5);
        leftBack.setPower(-0.5);
        rightFront.setPower(-0.5);
    }
    private void forward() {
        leftFront.setPower(0.5);
        rightBack.setPower(0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(0.5);
    }
    private void fastForward() {
        leftFront.setPower(1);
        rightBack.setPower(1);
        leftBack.setPower(1);
        rightFront.setPower(1);
    }
    private void backward() {
        leftFront.setPower(-0.2);
        rightBack.setPower(-0.2);
        leftBack.setPower(-0.2);
        rightFront.setPower(-0.2);
    }
    private void rotateLeft() {
        leftFront.setPower(0.5);
        rightBack.setPower(-0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(-0.5);
    }
    private void rotateRight() {
        leftFront.setPower(-0.5);
        rightBack.setPower(0.5);
        leftBack.setPower(-0.5);
        rightFront.setPower(0.5);
    }
    private void brake() {
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
    }
    private void zzz(long howlong) {
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        sleep(howlong);
    }
    private void specimen() {
        SmartServo.setSmartPos(hardwareMap,"lid",0.2);
        sleep(1003);

        leftLift.setPower(0.5);
        rightLift.setPower(0.5);
        sleep(1500);

        leftLift.setPower(0.2);
        rightLift.setPower(0.2);
        sleep(100);


        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.8);
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.8);
        sleep(5000);

        leftLift.setPower(-0.1);
        rightLift.setPower(-0.1);
        sleep(600);

        fastForward();
        sleep(200);

        brake();
        sleep(1500);

        leftLift.setPower(-0.2);
        rightLift.setPower(-0.2);
        sleep(1000);
    }

    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        //leftDrive  = hardwareMap.get(DcMotor.class, "rightFront");
        //rightDrive = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        lid = hardwareMap.get(Servo.class, "lid");


        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");

        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        lid.setDirection(Servo.Direction.REVERSE);

        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // Step through each leg of the path, ensuring that the OpMode has not been stopped along the way.

        // Step 1:  Drive forward for 3 seconds
        //leftFront.setPower(FORWARD_SPEED);
        //rightFront.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        telemetry.addData("Status", "Running.");
        telemetry.update();
        sleep(500);
        backward();
        sleep(4500);
        brake();
        sleep(500);
        forward();
        sleep(50);
        brake();
        sleep(200);
        specimen();
        sleep(500);

        /*
        // Step 2:  Spin right for 1.3 seconds
        leftDrive.setPower(TURN_SPEED);
        rightDrive.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3:  Drive Backward for 1 Second
        leftDrive.setPower(-FORWARD_SPEED);
        rightDrive.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 3: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 4:  Stop
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        */

    }
}
