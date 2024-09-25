package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class BrokenVessel extends LinearOpMode {

    Servo wrist;
    Servo shoulder;
    Servo elbow;

    Servo leftLift;
    Servo rightLift;

    double ArmIntHeight = .5;
    double ClawIntClamp = .5;
    double LeftHangIntSpeed = 0.2;
    double RightHangIntSpeed = 0.2;
    double WristIntRotation = .5;

    double WristPostion;
    double LeftHangSpeed;
    double RightHangSpeed;
    double ClawPostion;
    double ArmPostion;
    //goob
public void ServoPos() {
    wrist = hardwareMap.get(Servo.class, "wrist");
    elbow = hardwareMap.get(Servo.class, "elbow");
    shoulder = hardwareMap.get(Servo.class, "shoulder");

    wrist.setPosition(WristPostion);
    //leftLift.setPosition(LeftHangSpeed);
    //rightLift.setPosition(RightHangSpeed);
    shoulder.setPosition(ArmPostion);
    elbow.setPosition(ClawPostion);

    }
    @Override
    public void runOpMode() throws InterruptedException {
        WristPostion = WristIntRotation;
        LeftHangSpeed = LeftHangIntSpeed;
        RightHangSpeed = RightHangIntSpeed;
        ClawPostion = ClawIntClamp;
        ArmPostion = ArmIntHeight;

        ServoPos();
        waitForStart();


        while(opModeIsActive()) {
            //telemetry.addData("Selected", which.toString());
            //telemetry.addLine("Y = Shoulder - X = Hopper - B = Wrist A = Lift");
            telemetry.update();
            sleep(100);
        }
    }




}


