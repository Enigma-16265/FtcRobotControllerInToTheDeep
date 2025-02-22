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
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import javax.script.*;

@TeleOp(name="Olivers Drive Code") //, group="Linear OpMode")
public class OliversDriveCode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;

    // Used to reverse the controls if necessary
    private int driveReverse = 1;
    private int turnReverse = 1;
    private int rightReverse = 1;
    private int leftReverse = 1;

    // Variables to prevent control loops
    private boolean xPressed = false;
    private boolean yPressed = false;
    private boolean aPressed = false;
    private boolean bPressed = false;
    private boolean rbPressed = false; //Right Bumper
    private boolean rtPressed = false; //Right Trigger
    private boolean lbPressed = false; //Left Bumper
    private boolean ltPressed = false; //Left Trigger
    private boolean rsPressed = false; //Right Stick
    private boolean lsPressed = false; //Left Stick


    private enum Buttons {
        aButton,
        bButton,
        xButton,
        yButton,
        rightBumper,
        rightTrigger,
        leftBumper,
        leftTrigger,
        rightStick,
        leftStick,
        dpadUp,
        dpadDown,
        dpadRight,
        dpadLeft,
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
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double drivePower = gamepad1.left_stick_x;
            double turnPower = gamepad1.left_stick_y;

            // Check if X/Y is pressed on controller
            // If so reverse drive and turn functions
            if (gamepad1.x && !xPressed) {
                xPressed = true;
                driveReverse *= -1;
            } else if (!gamepad1.x) {
                xPressed = false;
            }

            if (gamepad1.y && !yPressed) {
                yPressed = true;
                turnReverse *= -1;
            } else if (!gamepad1.y) {
                yPressed = false;
            }



            // Calculate power from variables
            double rightPower = Range.clip(drivePower + (turnPower * turnReverse), -1.0, 1.0) * driveReverse;
            double leftPower = Range.clip(drivePower - (turnPower * turnReverse), -1.0, 1.0) * driveReverse;

            // Send calculated power to wheels
            rightFront.setPower(rightPower);
            rightBack.setPower(rightPower);
            leftFront.setPower(leftPower);
            leftBack.setPower(leftPower);

            // Stuff to print to the console (clears every loop)
            telemetry.addData("Version", "1.1");
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Reverses", "Drive: [%d], Turn: [%d]", driveReverse, turnReverse);
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }

    private static class bind {
         public class add<T> {
             Buttons button;
             T variable;
             T modifier;

             public add(Buttons Button, T Variable, T Modifier) {
                 this.button = Button;
                 this.variable = Variable;
                 this.modifier = Modifier;
             }

             public void check() {

             }
         }
    }
}
