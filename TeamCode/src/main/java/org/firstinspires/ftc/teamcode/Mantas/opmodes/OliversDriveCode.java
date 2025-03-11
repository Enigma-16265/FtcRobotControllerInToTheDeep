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

package org.firstinspires.ftc.teamcode.Mantas.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Olivers Drive Code") //, group="Linear OpMode")
public class OliversDriveCode extends LinearOpMode {

    // Variables for the robot
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    // Variables for opmode
    private int driveReverse = 1;
    private int turnReverse = 1;
    private boolean strafeToggle;

    // Button Stuff
    private boolean buttonPressed[] = new boolean[14];

    private enum Buttons {
        aButton,
        bButton,
        xButton,
        yButton,
        leftBumper,
        leftTrigger,
        rightBumper,
        rightTrigger,
        leftStick,
        rightStick,
        dpadUp,
        dpadDown,
        dpadLeft,
        dpadRight,
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Button handling
            if (checkButton(Buttons.leftTrigger)) driveReverse *= -1;

            if (checkButton(Buttons.leftBumper)) turnReverse *= -1;

            if (checkButton(Buttons.rightTrigger)) strafeToggle = !strafeToggle;

            // Run functions for driving
            if (strafeToggle) {
                telemetry.addData("Strafe Toggle", "[TRUE]");
                StrafeDrive();
            } else {
                telemetry.addData("Strafe Toggle", "[FALSE]");
                DriveStandard();
            }

            // Print telemetry data to console.
            telemetry.update();
        }
    }

    private void DriveStandard() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double drivePower = gamepad1.left_stick_x;
        double turnPower = gamepad1.left_stick_y;

        // Calculate power from variables
        double rightPower = Range.clip(drivePower + (turnPower * turnReverse), -1.0, 1.0) * driveReverse;
        double leftPower = Range.clip(drivePower - (turnPower * turnReverse), -1.0, 1.0) * driveReverse;

        // Send calculated power to wheels
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);
        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);

        telemetry.addData("Motors", "Drive: (%d), Turn: (%d)", driveReverse, turnReverse);
        telemetry.addData("Motors", "Left: (%d), Right(%d)", leftPower, rightPower);
    }

    private void StrafeDrive() {
        telemetry.addData("aw hell nah", "you expect me to write strafe code??? im so cooked");
    }

    private boolean checkButton(Buttons button, boolean held) {
        /*
            CRAPPY CODE AHEAD!!! BEWARE!! ⚠️⚠️⚠️🚨‼️‼️
            I dont know how I would improve it...

            If held not referenced; defaults to false
            If held = true; returns true when button is held
            If held = false; returns true only when user begins to press the button

            if(checkButton(Buttons.[BUTTON], [BOOL])) [CODE];
        */
        switch (button) {
            case aButton:
                if (gamepad1.a) {
                    if (!buttonPressed[0]) {
                        buttonPressed[0] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[0] = false;
                }
                break;

            case bButton:
                if (gamepad1.b) {
                    if (!buttonPressed[1]) {
                        buttonPressed[1] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[1] = false;
                }
                break;

            case xButton:
                if (gamepad1.x) {
                    if (!buttonPressed[2]) {
                        buttonPressed[2] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[2] = false;
                }
                break;

            case yButton:
                if (gamepad1.y) {
                    if (!buttonPressed[3]) {
                        buttonPressed[3] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[3] = false;
                }
                break;

            case leftBumper:
                if (gamepad1.left_bumper) {
                    if (!buttonPressed[4]) {
                        buttonPressed[4] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[4] = false;
                }
                break;

            case leftTrigger:
                if (gamepad1.left_trigger > 0.5) {
                    if (!buttonPressed[5]) {
                        buttonPressed[5] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[5] = false;
                }
                break;

            case rightBumper:
                if (gamepad1.right_bumper) {
                    if (!buttonPressed[6]) {
                        buttonPressed[6] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[6] = false;
                }
                break;

            case rightTrigger:
                if (gamepad1.right_trigger > 0.5) {
                    if (!buttonPressed[7]) {
                        buttonPressed[7] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[7] = false;
                }
                break;

            case leftStick:
                if (gamepad1.left_stick_button) {
                    if (!buttonPressed[8]) {
                        buttonPressed[8] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[8] = false;
                }
                break;

            case rightStick:
                if (gamepad1.right_stick_button) {
                    if (!buttonPressed[9]) {
                        buttonPressed[9] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[9] = false;
                }
                break;

            case dpadUp:
                if (gamepad1.dpad_up) {
                    if (!buttonPressed[10]) {
                        buttonPressed[10] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[10] = false;
                }
                break;

            case dpadDown:
                if (gamepad1.dpad_down) {
                    if (!buttonPressed[11]) {
                        buttonPressed[11] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[11] = false;
                }
                break;

            case dpadLeft:
                if (gamepad1.dpad_left) {
                    if (!buttonPressed[12]) {
                        buttonPressed[12] = true;
                    } else if (held) return true;
                } else {
                    buttonPressed[12] = false;
                }
                break;

            case dpadRight:
                if (gamepad1.dpad_right) {
                    if (!buttonPressed[13]) {
                        buttonPressed[13] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[13] = false;
                }
                break;
        }

        return false;
    }

    private boolean checkButton(Buttons button) {
        return checkButton(button, false);
    }

    private void guh() {
        //something for auto drive
    }
}
