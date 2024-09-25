package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class BrokenVessel extends LinearOpMode {

    Servo wrist;
    Servo Arm;
    Servo Claw;

    DcMotor leftLift;
    DcMotor rightLift;

    double ArmIntHeight = .5;
    double ClawIntClamp = .5;
    double LeftHangIntSpeed = 0.0;
    double RightHangIntSpeed = 0.0;
    double WristIntRotation = .5;

    double WristPostion;
    double LeftHangSpeed;
    double RightHangSpeed;
    double ClawPostion;
    double ArmPostion;
    //The code below is a little more done but still barely complete
public void ServoPos() {
    wrist.setPosition(WristPostion);
    leftLift.setPower(LeftHangSpeed);
    rightLift.setPower(RightHangSpeed);
    Arm.setPosition(ArmPostion);
    Claw.setPosition(ClawPostion);

    }
    @Override
    public void runOpMode() throws InterruptedException {
        WristPostion = WristIntRotation;
        LeftHangSpeed = LeftHangIntSpeed;
        RightHangSpeed = RightHangIntSpeed;
        ClawPostion = ClawIntClamp;
        ArmPostion = ArmIntHeight;

        ServoPos();
    }




}


