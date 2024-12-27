package org.firstinspires.ftc.teamcode.clawTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles.RosesMcMuffinClass;
import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.DriveCodeAbstract;
import org.firstinspires.ftc.teamcode.hypotheticals.ColorLocator_Loralei;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Concept: Test", group = "Teleop")
public class IntakeClaw extends ColorLocator_LoraleiButEditedToWorkForIntakeClaw {
    RosesMcMuffinClass movement = new RosesMcMuffinClass(hardwareMap, gamepad1);
    DriveCodeAbstract wheelCode = new DriveCodeAbstract(hardwareMap, gamepad1) {
        @Override
        public void runWheels() {
        }
    };

    @Override
    public void runOpMode() {
        super.Setup();
        //Take the arcos to find the angle
        double ydist = myBoxCorners[0].y - myBoxCorners[1].y;
        double xdist = myBoxCorners[0].x - myBoxCorners[1].x;
        double disthypot = Math.pow(xdist, 2) + Math.pow(ydist, 2);
        double angle = Math.acos((ydist) / (Math.sqrt(disthypot)));
        //whatever servo you're using .setPosition(angle + expirimental modifier)
        double strafe = 0;
        double drive = 0;
        if (super.centerX > 0) {
            strafe = 0.5;
        } else if (super.centerX < 0) {
            strafe = -0.5;
        }
        if (super.centerY > 0) {
            drive = 0.5;
        } else if (super.centerY < 0) {
            drive = -0.5;
        }
        double left = drive + strafe;
        double leftBackPower = drive - strafe;
        double right = drive - strafe;
        double rightBackPower = drive + strafe;
        wheelCode.leftFrontDrive.setPower(left * wheelCode.speed);
        wheelCode.leftBackDrive.setPower(leftBackPower * wheelCode.speed);
        wheelCode.rightFrontDrive.setPower(right * wheelCode.speed);
        wheelCode.rightBackDrive.setPower(rightBackPower * wheelCode.speed);
    }
}