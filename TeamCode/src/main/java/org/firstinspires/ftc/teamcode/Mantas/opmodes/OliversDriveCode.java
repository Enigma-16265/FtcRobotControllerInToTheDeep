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

import org.firstinspires.ftc.teamcode.StuffWeMayUseSomeday.OliveTemplate;

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

    private OliveTemplate OliveUtils = new OliveTemplate() {
        public void runOpMode() throws InterruptedException {}
    };

    // Button Stuff
    /*private boolean buttonPressed[] = new boolean[14];

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
    }*/

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

            // Run functions for driving
            /*if (strafeToggle) {
                telemetry.addData("Strafe Toggle", "[TRUE]");
                StrafeDrive();
            } else {
                telemetry.addData("Strafe Toggle", "[FALSE]");
                DriveStandard();
            }*/

            OliveUtils.test();

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

        // Send calculated power to wheels e
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


    private void guh() {
        //something for auto drive
    }
}
