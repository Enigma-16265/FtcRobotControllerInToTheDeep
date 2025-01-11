package org.firstinspires.ftc.teamcode.clawTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.LorelaisDriveCode;

@Autonomous(name = "Thingy test", group = "Teleop")
public class intakeColorMover extends ColorLocator_LoraleiButEditedToWorkForIntakeClaw{
    double strafe = 0;
    double drive = 0;
    @Override
    public void runOpMode() {
        waitForStart();
        super.Setup();
        LorelaisDriveCode wheelCode = new LorelaisDriveCode(hardwareMap, gamepad1);
        while (opModeIsActive() || opModeInInit()) {
            super.colorFinder();
            //Take the arcos to find the angle
            /*double ydist = myBoxCorners[0].y - myBoxCorners[1].y;
            double xdist = myBoxCorners[0].x - myBoxCorners[1].x;
            double disthypot = Math.pow(xdist, 2) + Math.pow(ydist, 2);
            double angle = Math.acos((ydist) / (Math.sqrt(disthypot)));*/
            //whatever servo you're using .setPosition(angle + expirimental modifier)
            telemetry.addLine(String.valueOf(super.centerX));
            drive = 0;
            strafe = 0;
            if (super.centerX != 16265 || super.centerY != 16265) {
                strafe = ((super.centerX / 320.0) - 1)/1.5;
                drive = ((super.centerY / 240.0) - 1)/3;
            }
            if (strafe <= 0.13 && strafe > 0 || strafe >= -0.13 && strafe < 0){
                strafe = 0;
            }
            if (drive <= 0.1 && drive > 0 || drive >=-0.1 && drive < 0){
                drive = 0;
            }
            double debugNum = drive * 3;
            double debugNum0 = strafe * 2;
            telemetry.addLine(String.valueOf(debugNum));
            telemetry.addLine(String.valueOf(debugNum0));
            telemetry.addLine(String.valueOf(centerX));
            telemetry.addLine(String.valueOf(centerY));
            telemetry.addLine(String.valueOf(super.seeBlueBlobs));
            double left = -drive + strafe;
            double leftBackPower = -drive - strafe;
            double right = -drive - strafe;
            double rightBackPower = -drive + strafe;
            double max = Math.max(Math.abs(left),Math.abs(right));
            if (max > 1){
                left/=max;
                right/=max;
            }
            wheelCode.leftFrontDrive.setPower(left * wheelCode.speed);
            wheelCode.leftBackDrive.setPower(leftBackPower * wheelCode.speed);
            wheelCode.rightFrontDrive.setPower(right * wheelCode.speed);
            wheelCode.rightBackDrive.setPower(rightBackPower * wheelCode.speed);
        }
    }
}