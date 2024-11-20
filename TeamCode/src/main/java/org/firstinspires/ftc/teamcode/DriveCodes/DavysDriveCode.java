package org.firstinspires.ftc.teamcode.DriveCodes;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DavysDriveCode extends DriveCodeAbstract {
    public DavysDriveCode(HardwareMap hardwareMap, Gamepad gamepad1){
        super(hardwareMap, gamepad1);
    }
    @Override
    //gamepad1.(left or right)_stick_(x or y)
    public void runWheels(){
        double left = gamepad1.right_stick_x;
        double right = gamepad1.right_stick_y;
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0)
        {
            left /= max;
            right /= max;
        }
        leftFrontDrive.setPower(left);
        rightFrontDrive.setPower(right);
        leftBackDrive.setPower(left);
        rightBackDrive.setPower(right);
        //Negative x strafe left, positive x strafe right
        double forwardmove = gamepad1.left_stick_y;
        double strafemove = gamepad1.left_stick_x;
        double NetMoveLeftFront;
        double NetMoveLeftBack;
        double NetMoveRightFront;
        double NetMoveRightBack;
        if (strafemove > 0) {
            NetMoveLeftFront = - strafemove;
            NetMoveLeftBack = strafemove;
            NetMoveRightFront = 0;
            NetMoveRightBack = 0;
        } else {
            NetMoveLeftFront = 0;
            NetMoveLeftBack = 0;
            NetMoveRightFront = strafemove;
            NetMoveRightBack = - strafemove;
        }
        double maxmove = Math.max(Math.abs(forwardmove - strafemove), Math.abs(forwardmove + strafemove));
        if (maxmove > 1.0)
        {
            NetMoveLeftFront/=maxmove;
            NetMoveLeftBack/=maxmove;
            NetMoveRightBack/=maxmove;
            NetMoveRightFront/=maxmove;
        }
        leftFrontDrive.setPower(NetMoveLeftFront);
        rightFrontDrive.setPower(NetMoveLeftBack);
        leftBackDrive.setPower(NetMoveRightFront);
        rightBackDrive.setPower(NetMoveRightBack);

    }
}
