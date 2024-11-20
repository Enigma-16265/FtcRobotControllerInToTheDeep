package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class McCrispy extends LinearOpMode {
    //TODO: Step 1, Replace all "wrist","hopper", etc with your servos
    Servo slideLeft;
    Servo slideRight;
    Servo outtakeLeft;
    Servo outtakeRight;
    Servo lid;
    Servo intakePivot;
    Servo intake;

    //DcMotor frontIntake;
    //DcMotor rearIntake;

    double speedAmount;
    //TODO: Add offset if needed w/ eg double lift
    //double LiftLeftOffset = -.05;

    //TODO: Step 3, set all of your servo names below
    enum ServoTypes{
        LID,
        SLIDE,
        OUTTAKE,
        INTAKEPIVOT,
        INTAKE,
    }
    ServoTypes which;
    //TODO: Step 4, replace all names of Servos with yours, and replace all capitals with what you set them to from step 3

    private void masterTuner() {
        if (gamepad1.left_bumper) {
            if (which == ServoTypes.LID) {
                lid.setPosition(lid.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.SLIDE) {
                slideRight.setPosition(slideRight.getPosition() - speedAmount);
                slideLeft.setPosition(slideLeft.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.OUTTAKE) {
                outtakeLeft.setPosition(outtakeLeft.getPosition() - speedAmount);
                outtakeRight.setPosition(outtakeRight.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.INTAKEPIVOT) {
                intakePivot.setPosition(intakePivot.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.INTAKE) {
                intake.setPosition(intake.getPosition() - speedAmount);
            }

        }
        else if (gamepad1.right_bumper) {
            if (which == ServoTypes.LID) {
                lid.setPosition(lid.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.SLIDE) {
                slideRight.setPosition(slideRight.getPosition() + speedAmount);
                slideLeft.setPosition(slideLeft.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.OUTTAKE) {
                outtakeLeft.setPosition(outtakeLeft.getPosition() + speedAmount);
                outtakeRight.setPosition(outtakeRight.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.INTAKEPIVOT) {
                intakePivot.setPosition(intakePivot.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.INTAKE) {
                intake.setPosition(intake.getPosition() + speedAmount);
            }
        }
    }
    //TODO: Step 5, replace all of your Servo functions below
    private void setServo() {
        if (gamepad1.y) {
            which = ServoTypes.LID;
        }
        else if (gamepad1.x) {
            which = ServoTypes.SLIDE;
        }
        else if (gamepad1.b) {
            which = ServoTypes.OUTTAKE;
        }

        telemetry.addData("Selected Servo = ", which.toString());
    }
    private void setExtra() {
        if (gamepad1.dpad_left) {
            which = ServoTypes.INTAKE;
        }
        else if (gamepad1.dpad_right) {
            which = ServoTypes.INTAKEPIVOT;
        }
    }

    //TODO: Step 7, your done! This was written by Goober on 11/5/23 slouching in a chair at 10:35 in the morning.
    // And as I'm writing this I wonder if anybody will actually use this. Problably not,
    // but idk what to do while I wait for the robot to be ready for calibration. It's now 10:36 am.
    // I wonder if future me will use this and or remember doing this. Goober out. Edit: I have to recode :( 11/28/23

    //TODO: Step 6 replace all of the xyz.getPosition()0; with your servos and replace "xyz" with what that servo is
    private void whatServoAt() {
        telemetry.addData("lid (Y) = ", lid.getPosition());
        telemetry.addData("slideRight (X) = ", slideRight.getPosition());
        telemetry.addData("outtakeLeft (B) = ", outtakeLeft.getPosition());
        telemetry.addData("outtakeRight (B) = ", outtakeRight.getPosition());
        telemetry.addData("slideLeft (X) = ", slideLeft.getPosition());
        telemetry.addData("intakePivot (Dpad R)= ", intakePivot.getPosition());
        telemetry.addData("intake (Dpad L)= ", intake.getPosition());
    }

    @Override
    public void runOpMode() {
        which = ServoTypes.LID;
        speedAmount = 0.01;
        //this is a coment to mAKE git update

        //TODO: Step 2, Replace the device names with your 4 (or more if you use two servos for one task) Into the deep me here, just replace var names and device names
        slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        slideRight = hardwareMap.get(Servo.class, "slideRight");
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        lid = hardwareMap.get(Servo.class, "lid");
        intake = hardwareMap.get(Servo.class, "intake");
        intakePivot = hardwareMap.get(Servo.class, "intakePivot");

        outtakeRight.setDirection(Servo.Direction.REVERSE);
        slideLeft.setDirection(Servo.Direction.REVERSE);


        slideLeft.setPosition(0.5);
        lid.setPosition(0.5);
        slideRight.setPosition(0.5);
        outtakeRight.setPosition(0.5);
        outtakeLeft.setPosition(0.5);
        intakePivot.setPosition(0.5);
        intake.setPosition(0.5);


        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            setServo();
            masterTuner();
            setExtra();
            whatServoAt();
            //telemetry.addData("Selected", which.toString());
            //telemetry.addLine("Y = Shoulder - X = Hopper - B = Wrist A = Lift");
            telemetry.update();
            sleep(100);
        }
    }
}