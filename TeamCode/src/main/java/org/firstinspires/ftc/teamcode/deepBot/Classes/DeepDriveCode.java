package org.firstinspires.ftc.teamcode.deepBot.Classes;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.DriveCodeAbstract;

/*
 * This class runs the wheel control code
 *
 */

public class DeepDriveCode extends DriveCodeAbstract {

    // constructor initializes the wheels and does some stuff with direction of wheels
    public DeepDriveCode(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {
        super(hardwareMap, gamepad1);
    }

    public void runWheels() {
        // Run wheels in POV mode (note: The joystick goes negative when pushed forward, so negate it)
        // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
        // This way it's also easy to just drive straight, or just turn.

        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;


        // Combine drive and turn for blended motion.
        // variables
        double leftFront = drive + turn + strafe;
        double leftBack = drive + turn - strafe;
        double rightFront = drive - turn - strafe;
        double rightBack = drive - turn + strafe;

        // Normalize the values so neither exceed +/- 1.0
        double max = Math.max(Math.abs(leftFront), Math.abs(rightFront));
        if (max > 1.0)
        {
            leftFront /= max;
            rightFront /= max;
        }

        // Output the safe vales to the motor drives.
        leftFrontDrive.setPower(leftFront * speed);
        leftBackDrive.setPower(leftBack * speed);
        rightFrontDrive.setPower(rightFront * speed);
        rightBackDrive.setPower(rightBack * speed);

    }

    public boolean areWheelsMoving(){
        if (leftFrontDrive.getPower() == 0 && rightFrontDrive.getPower() == 0){
            return false;
        }else {
            return true;
        }
    }
}
