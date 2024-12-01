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

package org.firstinspires.ftc.teamcode.deepBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Classes.DeepOuttake;
import org.firstinspires.ftc.teamcode.Classes.DeepDriveCode;
import org.firstinspires.ftc.teamcode.Classes.IntakeClass;
import org.firstinspires.ftc.teamcode.Classes.SmartServo;
import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.DriveCodeAbstract;

/*
 * This code calls other classes and runs their code
 * It runs the wheel drive code and the intake code, so the robot can
 * move around and stuff
 *
 * If you make new code for the deep robot, add it to this!
 */

@TeleOp(name="Robot: Deep Teleop", group="Robot")
//@Disabled
public class DeepOpMode extends LinearOpMode {


    @Override
    public void runOpMode() {

        // Prepare Drive Code
        DriveCodeAbstract wheelCode = new DeepDriveCode(hardwareMap, gamepad1);
        DeepOuttake outtakeCode = new DeepOuttake(hardwareMap, gamepad2);
        IntakeClass intakeCode = new IntakeClass(hardwareMap, gamepad1, gamepad2);

        initialize();

        //Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");
        telemetry.update();



        // Wait for driver to press START
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            wheelCode.runWheels();
            outtakeCode.outtake();
            intakeCode.runIntake();

            // Send telemetry message to signify robot running
            whatServoAt();
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
            idle();
        }
    }

    private void whatServoAt() {
        telemetry.addData("slideLeft    = ", hardwareMap.get(Servo.class,"slideLeft").getPosition());
        telemetry.addData("slideRight   = ", hardwareMap.get(Servo.class,"slideRight").getPosition());
        telemetry.addData("outtakeLeft  = ", hardwareMap.get(Servo.class,"outtakeLeft").getPosition());
        telemetry.addData("outtakeRight = ", hardwareMap.get(Servo.class,"outtakeRight").getPosition());
        telemetry.addData("lid          = ", hardwareMap.get(Servo.class,"lid").getPosition());
        telemetry.addData("intakePivot  = ", hardwareMap.get(Servo.class,"intakePivot").getPosition());
        //telemetry.addData("if transfer requested?", intakeCode.transferRequested);
    }

    private void initialize() {
        Servo lid = hardwareMap.get(Servo.class, "lid");
        Servo slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        Servo slideRight = hardwareMap.get(Servo.class, "slideRight");
        DcMotor leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        CRServo intake = hardwareMap.get(CRServo.class, "intake");


        slideLeft.setDirection(Servo.Direction.REVERSE);
        slideRight.setDirection(Servo.Direction.REVERSE);
        lid.setDirection(Servo.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);

        hardwareMap.get(DcMotor.class, "rightLift").setPower(0);
        hardwareMap.get(DcMotor.class, "leftLift").setPower(0);

        SmartServo.setSmartPos(hardwareMap,"slideLeft", 0.0 + IntakeClass.extendoOffset);
        SmartServo.setSmartPos(hardwareMap,"slideRight", 0.0);
        SmartServo.setSmartPos(hardwareMap,"intakePivot", 0.44);
        SmartServo.setSmartPos(hardwareMap,"outtakeRight", 0.51);
        SmartServo.setSmartPos(hardwareMap,"outtakeLeft", 0.51);
        SmartServo.setSmartPos(hardwareMap,"lid", 0.6);
    }
}
