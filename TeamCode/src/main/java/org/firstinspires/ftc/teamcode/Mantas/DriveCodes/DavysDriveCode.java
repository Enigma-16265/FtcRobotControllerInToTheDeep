// Rose says this is a fancy import
package org.firstinspires.ftc.teamcode.Mantas.DriveCodes;

// Stuff that we will need
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

// My drivecode can take stuff from driveCodeAbstract
public class DavysDriveCode extends DriveCodeAbstract {

    //A constructor
    public DavysDriveCode(HardwareMap hardwareMap, Gamepad gamepad1){
        super(hardwareMap, gamepad1);
    }

    //Change the runWheels code in DriveCodeAbstract into this
    @Override
    public void runWheels(){

        // Variables I need
        double turn = gamepad1.right_stick_x;
        double forwardmove = gamepad1.left_stick_y;
        double strafemove = gamepad1.left_stick_x;
        //Movement in directions before turn
        double NetMoveLeftFront;
        double NetMoveLeftBack;
        double NetMoveRightFront;
        double NetMoveRightBack;
        //The larger of the two values for strafe and forward
        double move;

        // If strafing more than moving forward, strafe
        if (Math.abs(strafemove) > Math.abs(forwardmove)) {
            move = strafemove;
            NetMoveLeftFront = strafemove;
            NetMoveLeftBack = -strafemove;
            NetMoveRightFront = -strafemove;
            NetMoveRightBack = strafemove;

        //Otherwise, move forward
        } else{
            move = forwardmove;
            NetMoveLeftFront = -forwardmove;
            NetMoveRightFront = -forwardmove;
            NetMoveLeftBack = -forwardmove;
            NetMoveRightBack = -forwardmove;
        }

        //If any of the values get higher than 1, divide all values by that number
        double fakemaxmove = Math.max(Math.abs(-move - turn), Math.abs(move + turn));
        double realmaxmove = Math.max(Math.abs(fakemaxmove), Math.abs(-move + turn));
        if (realmaxmove > 1.0) {
            NetMoveLeftFront/=realmaxmove;
            NetMoveRightFront/=realmaxmove;
            NetMoveRightBack/=realmaxmove;
            NetMoveLeftBack/=realmaxmove;
        }

        //Move the robot
        leftFrontDrive.setPower(NetMoveLeftFront + turn);
        rightFrontDrive.setPower(NetMoveRightFront - turn);
        leftBackDrive.setPower(NetMoveLeftBack + turn);
        rightBackDrive.setPower(NetMoveRightBack + turn);

    }
}
