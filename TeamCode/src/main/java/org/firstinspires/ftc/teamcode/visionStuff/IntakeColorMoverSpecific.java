/*package org.firstinspires.ftc.teamcode.clawTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.LorelaisDriveCode;
public class IntakeColorMoverSpecific extends ColorLocator_LoraleiButEditedToWorkForIntakeColorMoverSpecific{
    double strafe = 0;
    double drive = 0;
    HardwareMap hardwareMap;
    Gamepad gamepad1;

    @Override
    public void colorMoveSpecifi() {
        super.Setup();
        double runTime = System.currentTimeMillis();
        LorelaisDriveCode wheelCode = new LorelaisDriveCode(hardwareMap, gamepad1);
        // Precise Mover: For the camera mounted on the arm
        telemetry.addLine(String.valueOf(super.centerX));
        drive = 0;
        strafe = 0;
        if (super.centerX != 16265 || super.centerY != 16265) {
            strafe = ((super.centerX / 320.0) - 1) / 1.5;
            drive = ((super.centerY / 240.0) - 1) / 3;
        }
        if (strafe <= 0.13 && strafe > 0 || strafe >= -0.13 && strafe < 0) {
            strafe = 0;
        }
        if (drive <= 0.1 && drive > 0 || drive >= -0.1 && drive < 0) {
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
*/