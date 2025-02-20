package org.firstinspires.ftc.teamcode.Mantas.DriveCodes;

import com.qualcomm.robotcore.hardware.Gamepad;

/*
 * This class runs the wheel control code
 *
 */

public class LorelaisDriveCode extends DriveCodeAbstract {

    // constructor initializes the wheels and does some stuff with direction of wheels
    public LorelaisDriveCode(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {
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

        // Output the safe vales to the motor drives.
        leftFrontDrive.setPower(left * speed);
        leftBackDrive.setPower(leftBackPower * speed);
        rightFrontDrive.setPower(right * speed);
        rightBackDrive.setPower(rightBackPower * speed);

    }
}
