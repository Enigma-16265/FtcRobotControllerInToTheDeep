package org.firstinspires.ftc.teamcode.clawTest;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Link.Classes.DeepDriveCode;
import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.LorelaisDriveCode;

public class intakeColorMover{
    public intakeColorMover(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {
       this.hardwareMap = hardwareMap;
       this.gamepad1 = gamepad1;
       wheelCode = new LorelaisDriveCode(hardwareMap, gamepad1);
       locator = new ColorLocator_LoraleiButEditedToWorkForIntakeClaw(hardwareMap);
       locator.Setup();
    }
    HardwareMap hardwareMap;
    Gamepad gamepad1;
    LorelaisDriveCode wheelCode;
    ColorLocator_LoraleiButEditedToWorkForIntakeClaw locator;
    double strafe = 0;
    double drive = 0;
    double driveSpec = 0;
    double strafeSpec = 0;
    public boolean weSawBlue = false;
    public boolean problem;
    public void colorMove() {
        locator.colorFinder();
        //Closest finder: For the camera mounted on the drivetrain
        double lowestCenter = 100000000;
        int arrayPos = 16265;
        for (int i = 0; i < locator.centerY.length; i++) {
            if (locator.centerY[i] != 16265) {
                if (lowestCenter > locator.centerY[i]) {
                    lowestCenter = locator.centerY[i];
                    arrayPos = i;
                }
            }
        }
        if (arrayPos != 16265) {
            if (locator.centerX[arrayPos] != 16265 || locator.centerY[arrayPos] != 16265) {
                strafe = (((locator.centerX[arrayPos] / 320.0) - 1) / 1.5) - 0.5;
                drive = ((locator.centerY[arrayPos] / 240.0) - 2) / 3;
            }
            if (strafe <= 0.13 && strafe > 0 || strafe >= -0.13 && strafe < 0) {
                strafe = 0;
            }
            if (drive <= 0.1 && drive > 0 || drive >= -0.1 && drive < 0) {
                drive = 0;
            }
            double left = -drive + strafe;
            double leftBackPower = -drive - strafe;
            double right = -drive - strafe;
            double rightBackPower = -drive + strafe;
            driveSpec = 0;
            strafeSpec = 0;
            double max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1) {
                left /= max;
                right /= max;
            }
            wheelCode.leftFrontDrive.setPower(left * wheelCode.speed);
            wheelCode.leftBackDrive.setPower(leftBackPower * wheelCode.speed);
            wheelCode.rightFrontDrive.setPower(right * wheelCode.speed);
            wheelCode.rightBackDrive.setPower(rightBackPower * wheelCode.speed);
        }
    }
    public void colorMoveSpecific() {
        // Precise Mover: For the camera mounted on the arm
        locator.colorFinder();
        problem = locator.problem;
        double lowestCenterSpec = 100000000;
        int arrayPosSpec = 16265;
        for (int i = 0; i < locator.centerY.length; i++) {
            if (locator.centerY[i] != 16265 && locator.centerX[i] != 162565) {
                if (lowestCenterSpec > locator.centerY[i]){
                    lowestCenterSpec = locator.centerY[i];
                    arrayPosSpec = i;
                    weSawBlue = true;
                }
            }
        }
        if (arrayPosSpec != 16265) {
            if (locator.centerX[arrayPosSpec] != 16265 || locator.centerY[arrayPosSpec] != 16265) {
                strafeSpec = (((locator.centerX[arrayPosSpec] / 320.0) - 1) / 1.5);
                driveSpec = ((locator.centerY[arrayPosSpec] / 240.0) - 1) / 3;
            }
        }
        if (strafeSpec <= 0.13 && strafeSpec > 0 || strafeSpec >= -0.13 && strafeSpec < 0) {
            strafeSpec = 0;
        }
        if (driveSpec <= 0.1 && driveSpec > 0 || driveSpec >= -0.1 && driveSpec < 0) {
            driveSpec = 0;
        }

        double leftSpec = -driveSpec + strafeSpec;
        double leftBackPowerSpec = -driveSpec - strafeSpec;
        double rightSpec = -driveSpec - strafeSpec;
        double rightBackPowerSpec = -driveSpec + strafeSpec;
        driveSpec = 0;
        strafeSpec = 0;
        double maxSpec = Math.max(Math.abs(leftSpec), Math.abs(rightSpec));
        if (maxSpec > 1) {
            leftSpec /= maxSpec;
            rightSpec /= maxSpec;
        }
        wheelCode.leftFrontDrive.setPower(leftSpec * wheelCode.speed);
        wheelCode.leftBackDrive.setPower(leftBackPowerSpec * wheelCode.speed);
        wheelCode.rightFrontDrive.setPower(rightSpec * wheelCode.speed);
        wheelCode.rightBackDrive.setPower(rightBackPowerSpec * wheelCode.speed);
    }
            //Take the arcos to find the angle
            /*double ydist = myBoxCorners[0].y - myBoxCorners[1].y;
            double xdist = myBoxCorners[0].x - myBoxCorners[1].x;
            double disthypot = Math.pow(xdist, 2) + Math.pow(ydist, 2);
            double angle = Math.acos((ydist) / (Math.sqrt(disthypot)));*/
            //whatever servo you're using .setPosition(angle + expirimental modifier)
}