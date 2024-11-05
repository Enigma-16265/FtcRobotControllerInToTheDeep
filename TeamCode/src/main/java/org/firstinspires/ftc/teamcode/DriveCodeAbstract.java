package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/** @noinspection ALL*/
public abstract class DriveCodeAbstract {
    /** @noinspection FieldCanBeLocal, unused */
    public final Gamepad gamepad1;

    // declare wheels
    public final DcMotor leftFrontDrive;
    public final DcMotor leftBackDrive;
    public final DcMotor rightFrontDrive;
    public final DcMotor rightBackDrive;

    public final double speed = 0.75;

    public DriveCodeAbstract(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {

        // Define and Initialize Motors
    leftFrontDrive  = hardwareMap.get(DcMotor.class, "leftFront");
    leftBackDrive  = hardwareMap.get(DcMotor.class, "leftBack");
    rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
    rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");

    // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
    // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
    // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        this.gamepad1 = gamepad1;
}

    public abstract void runWheels();
}
