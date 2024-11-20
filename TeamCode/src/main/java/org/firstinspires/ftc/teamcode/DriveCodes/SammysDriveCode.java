package org.firstinspires.ftc.teamcode.DriveCodes;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SammysDriveCode extends DriveCodeAbstract {

    //constructor
    public SammysDriveCode(HardwareMap hardwareMap, Gamepad gamepad1) {
        super(hardwareMap, gamepad1);
    }

    @Override
    public void runWheels() {

        //moving variables
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;

        //round all outputs that do not go in cardinal directions to make it so that they do
        //only do this for strafe and drive
        strafe = Math.round(strafe);
        drive = Math.round(drive);

        //combine moving variables
        double left =           speed * (drive + turn + strafe);
        double leftBackPower =  speed * (drive + turn - strafe);
        double right =          speed * (drive - turn - strafe);
        double rightBackPower = speed * (drive + turn + strafe);

        // Normalize the values so neither exceed +/- 1.0
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0)
        {
            left /= max;
            right /= max;
        }

            // Output the safe vales to the motor drives.
            leftFrontDrive.setPower(left);
            leftBackDrive.setPower(leftBackPower);
            rightFrontDrive.setPower(right);
            rightBackDrive.setPower(rightBackPower);
    }
}
