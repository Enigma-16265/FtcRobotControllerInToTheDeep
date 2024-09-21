package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class BrokenVessel extends LinearOpMode {
    //TODO: Step 1, Replace all "wrist","hopper", etc with your servos
    Servo wrist;
    Servo elbow;
    Servo leftFinger;
    Servo rightFinger;
    Servo shoulder;
    Servo leftLift;
    Servo rightLift;

    double ArmIntHeight = .5;
    double ClawIntClamp = .5;
    double HangIntHeight = .5;
    double WristIntRotation = .5;

    double WristPostion;
    double HangPosition;
    double ClawPostion;
    double ArmPostion;
    //The code below is not DONE
public void ServoPos() {
    wrist.setPosition(WristPostion);
    leftLift.setPosition(HangPosition);
    wrist.setPosition(WristPostion);
    wrist.setPosition(WristPostion);

    }
    @Override
    public void runOpMode() throws InterruptedException {
        WristPostion = WristIntRotation;
        HangPosition = HangIntHeight;
        ClawPostion = ClawIntClamp;
        ArmPostion = ArmIntHeight;

        ServoPos();
    }




}


