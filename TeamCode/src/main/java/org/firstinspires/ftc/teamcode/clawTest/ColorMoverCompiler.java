package org.firstinspires.ftc.teamcode.clawTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Link.Classes.IntakeClass;

import java.util.jar.Attributes;

@Autonomous(name = "Color Compiler", group = "Test")
public class ColorMoverCompiler extends LinearOpMode {
    private Servo wristLeft;
    private Servo wristRight;
    private Servo elbow;
    public boolean servoSet = false;

    @Override
    public void runOpMode(){
        wristLeft = hardwareMap.get(Servo.class,"wristLeft");
        wristRight = hardwareMap.get(Servo.class, "wristRight");
        elbow = hardwareMap.get(Servo.class, "elbow");
        waitForStart();
        telemetry.addData("started", toString());
        telemetry.update();
        elbow.setPosition(0.5);
        sleep (10000);
        wristLeft.setPosition(0);
        wristRight.setPosition(0);
        wristRight.setPosition(0.5);
        wristLeft.setPosition(0.5);
        intakeColorMover farMover = new intakeColorMover(hardwareMap, gamepad1);
        wristRight.setDirection(Servo.Direction.REVERSE);
        double waitTimer = 0;
        double drivePast = 0;
        double strafePast = 0;
        telemetry.addData("Drive = " + farMover.drive, toString());
        telemetry.addData("Change in drive over 500 ms is " + drivePast, toString());
        telemetry.update();
        //farMover.colorMove();
        while (opModeIsActive()) {
            telemetry.addData("driveSpec = " + farMover.driveSpec, toString());
            telemetry.update();
            if ((farMover.strafe == 0 && farMover.drive == 0) || (System.currentTimeMillis() - waitTimer >= 500 && farMover.drive == drivePast && farMover.strafe == strafePast)) {
                if (!servoSet) {
                    elbow.setPosition(0.5);
                    wristLeft.setPosition(-1);
                    wristRight.setPosition(-1);
                    servoSet = true;
                }
                telemetry.addData(String.valueOf(farMover.problem), toString());
                telemetry.update();
               farMover.colorMoveSpecific();
            }else {
                farMover.colorMove();
            }
            if (System.currentTimeMillis() - waitTimer >= 500){
                waitTimer = System.currentTimeMillis();
                drivePast = farMover.drive;
                strafePast = farMover.strafe;
            }
        }
    }
}
