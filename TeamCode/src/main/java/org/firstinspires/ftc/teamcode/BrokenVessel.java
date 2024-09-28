package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class BrokenVessel extends LinearOpMode {
    //Basic Servos
    Servo wrist;
    Servo shoulder;
    Servo elbow;
    Servo claw;
    //Slides
    DcMotor rightSlideSetOne;
    DcMotor leftSlideSetOne;
    Servo rightSlideSetTwo;
    Servo leftSlideSetTwo;

    //Servo doubles
    double armIntHeight = .5;
    double clawIntClamp = .5;
    double wristIntRotation = .5;
    double elbowIntPos = 0.5;
    //Motor doubles
    double leftSlideIntSpeed = 0.0;
    double rightSlideIntSpeed = 0.0;
    double servoRightSlideIntSpeed = 0.0;
    double servoLeftSlideIntSpeed = 0.0;

    /*
    double WristPostion;
    double LeftHangSpeed;
    double RightHangSpeed;
    double ClawPostion;
    double ArmPostion;
     */

    //Int of servos and motors
public void ServoPos() {
    //Servo hardwareMap
    wrist = hardwareMap.get(Servo.class, "wrist");
    elbow = hardwareMap.get(Servo.class, "elbow");
    shoulder = hardwareMap.get(Servo.class, "shoulder");
    claw = hardwareMap.get(Servo.class, "claw");
    rightSlideSetTwo = hardwareMap.get(Servo.class, "rightServoSlide");
    leftSlideSetTwo = hardwareMap.get(Servo.class, "leftServoSlide");
    //Motor hardwareMap
    rightSlideSetOne = hardwareMap.get(DcMotor.class, "rightMotorSlide");
    leftSlideSetOne = hardwareMap.get(DcMotor.class, "leftMotorSlide");

    //Servo Pos
    wrist.setPosition(wristIntRotation);
    shoulder.setPosition(armIntHeight);
    elbow.setPosition(elbowIntPos);
    claw.setPosition(clawIntClamp);
    rightSlideSetTwo.setPosition(servoRightSlideIntSpeed);
    leftSlideSetTwo.setPosition(servoLeftSlideIntSpeed);
    //Motor Power
    rightSlideSetOne.setPower(rightSlideIntSpeed);
    leftSlideSetOne.setPower(leftSlideIntSpeed);


    }
    @Override
    public void runOpMode() throws InterruptedException {
        /*
        WristPostion = WristIntRotation;
        LeftHangSpeed = LeftHangIntSpeed;
        RightHangSpeed = RightHangIntSpeed;
        ClawPostion = ClawIntClamp;
        ArmPostion = ArmIntHeight;
*/
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


