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
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.DriveCodeAbstract;
import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.LorelaisDriveCode;
import org.firstinspires.ftc.teamcode.Mantas.mechanisms.RandomMovementControls;
import org.firstinspires.ftc.teamcode.Mantas.mechanisms.liftPositions;
import org.firstinspires.ftc.teamcode.Mantas.mechanisms.seconedLiftCode;

/*
 * This code calls other classes and runs their code
 * It runs the wheel drive code and the arm movement code, so the robot can
 * move around and stuff
 */

@TeleOp(name="Robot: Roses Teleop POV", group="Robot")
//@Disabled
public class RosesMantasOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {

        // Define and initialize wheels and declare wheelCode
        DriveCodeAbstract wheelCode = new LorelaisDriveCode(hardwareMap,gamepad1);
        RandomMovementControls spinCode = new RandomMovementControls(hardwareMap, gamepad1);
        liftPositions liftcode = new liftPositions(hardwareMap, gamepad1);

        // Define and initialize ALL installed servos and declare armCode
        //RosesMcMuffinClass mcMuffin = new RosesMcMuffinClass(hardwareMap, gamepad1);

        DcMotor leftLift = hardwareMap.get(DcMotor.class, "leftHang");
        DcMotor rightLift = hardwareMap.get(DcMotor.class, "rightHang");

        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");
        //telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // run various control code
            wheelCode.runWheels();

           spinCode.spinAroundFunction();
           liftcode.makeLiftsWork();

            // Send telemetry message to signify robot running
            whatServoAt();
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }

    private void whatServoAt() {
        telemetry.addData("leftLift", hardwareMap.get(DcMotor.class, "leftHang").getCurrentPosition());
        telemetry.addData("leftLift", hardwareMap.get(DcMotor.class, "leftHang").isBusy());
    }
}
