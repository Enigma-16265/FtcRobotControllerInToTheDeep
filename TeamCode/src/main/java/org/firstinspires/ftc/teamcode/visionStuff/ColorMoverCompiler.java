package org.firstinspires.ftc.teamcode.visionStuff;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Color Compiler", group = "Test")
public class ColorMoverCompiler extends LinearOpMode {
    private Servo wristLeft;
    private Servo wristRight;
    private Servo elbow;
    public boolean servoSet = false;
    double waitTimer = 0;
    double drivePast = 0;
    double strafePast = 0;
    private DcMotor leftBack;

    @Override
    public void runOpMode(){
        wristLeft = hardwareMap.get(Servo.class, "wristLeft");
        wristRight = hardwareMap.get(Servo.class, "wristRight");
        elbow = hardwareMap.get(Servo.class, "elbow");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        intakeColorMover farMover = new intakeColorMover(hardwareMap, gamepad1);
            waitForStart();
            elbow.setPosition(0.5);
            sleep(3000);
        wristRight.setPosition(0);
        wristLeft.setPosition(0);
        sleep(3000);
            wristRight.setPosition(-0.6);
            wristLeft.setPosition(0.6);
            wristRight.setDirection(Servo.Direction.REVERSE);
            telemetry.update();
            waitTimer = System.currentTimeMillis();
        while (opModeIsActive()) {
            telemetry.update();
            if ((farMover.strafe == 0 && farMover.drive == 0 || (farMover.drive == drivePast && farMover.strafe == strafePast)) && System.currentTimeMillis() - waitTimer >= 50000) {
                if (!servoSet) {
                    elbow.setPosition(0.5);
                    wristLeft.setPosition(0);
                    wristRight.setPosition(0);
                    servoSet = true;
                }
                telemetry.update();
               //farMover.colorMoveSpecific();
               telemetry.addData("here", + 0);
            }else {
                farMover.colorMove();
                telemetry.addData("drive", farMover.drive);
                telemetry.addData("strafe", farMover.strafe);
                telemetry.addData("leftBack power", leftBack.getPower());
                telemetry.addData("ArrayPos ", farMover.arrayPos);
                telemetry.addData("lowestCenter ", farMover.lowestCenter);
            }
            if (System.currentTimeMillis() - waitTimer >= 500){
                waitTimer = System.currentTimeMillis();
                drivePast = farMover.drive;
                strafePast = farMover.strafe;
            }
        }
    }
}
