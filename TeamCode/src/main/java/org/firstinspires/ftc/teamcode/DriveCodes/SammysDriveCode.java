package org.firstinspires.ftc.teamcode.DriveCodes;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** @noinspection unused*/
public class SammysDriveCode extends DriveCodeAbstract {

    private boolean wasStrafing = false;
    private double whenStrafeStarted;

    public SammysDriveCode(HardwareMap hardwareMap, Gamepad gamepad1) {
        super(hardwareMap, gamepad1);
    }

    public void runWheels() {
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;

        strafe = Math.round(strafe);
        drive = Math.round(drive);

        double left = drive + turn + strafe;
        double leftBackPower = drive + turn - strafe;
        double right = drive - turn - strafe;
        double rightBackPower = drive + turn + strafe;

        // Normalize the values so neither exceed +/- 1.0
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0)
        {
            left /= max;
            right /= max;
        }

        if (!wasStrafing && strafe != 0) {
            whenStrafeStarted = System.currentTimeMillis();
        }

        // Output the safe vales to the motor drives.
        leftFrontDrive.setPower(left * speed);
        leftBackDrive.setPower(leftBackPower * speed);
        rightFrontDrive.setPower(right * speed);
        rightBackDrive.setPower(rightBackPower * speed);
    }
}
