package org.firstinspires.ftc.teamcode.Link.Classes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mantas.DriveCodes.DriveCodeAbstract;

/*
 * This class runs the wheel control code
 *
 */

public class DeepDriveCode extends DriveCodeAbstract {

    //not objects
    private double last_time_a_pressed;
    private boolean currently_spinning = false;
    private int cycle_number = 13;

    // constructor initializes the wheels and does some stuff with direction of wheels
    public DeepDriveCode(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1) {
        super(hardwareMap, gamepad1);
    }

    public void runWheels() {
        // Run wheels in POV mode (note: The joystick goes negative when pushed forward, so negate it)
        // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
        // This way it's also easy to just drive straight, or just turn.\
        double boostMultiplier = 1.2;

        double drive = -gamepad1.left_stick_y * boostMultiplier;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x * boostMultiplier;


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
        // he he they're now NOT safe >:) -E
        leftFrontDrive.setPower(leftFront * speed);
        leftBackDrive.setPower(leftBack * speed);
        rightFrontDrive.setPower(rightFront * speed);
        rightBackDrive.setPower(rightBack * speed);
    }

    /*public boolean areWheelsMoving(){
        if (leftFrontDrive.getPower() == 0 && rightFrontDrive.getPower() == 0){
            return false;
        }else {
            return true;
        }
    }*/
    // the function for spinning the robot 180 degrees
    public void spinAroundFunction () {

        //if a was pressed do not let spinAroundFunction run for another second
        if (gamepad1.a && last_time_a_pressed + 1000 < System.currentTimeMillis()) {
            last_time_a_pressed = System.currentTimeMillis();
            currently_spinning = true;
        }


        if (currently_spinning) {

            leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //minus 1 to cycle_number
            cycle_number--;

            //spin the robot 18 degrees
            leftFrontDrive.setPower(speed);
            leftBackDrive.setPower(speed);
            rightFrontDrive.setPower(-speed);
            rightBackDrive.setPower(-speed);

            //stop spinning if the robot has spun 180 degrees
            if (cycle_number == 0) {

                //reset variables so previous code can be repeated
                currently_spinning = false;
                cycle_number = 13;
            }
        }
    }
}
