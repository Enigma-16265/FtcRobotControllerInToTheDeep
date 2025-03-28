package org.firstinspires.ftc.teamcode.Link.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Link.SmartServo;

@TeleOp
public class McFishFillet extends LinearOpMode {
    //TODO: Step 1, Replace all "wrist","hopper", etc with your servos
    Servo slideLeft;
    Servo slideRight;
    Servo outtakeLeft;
    Servo outtakeRight;
    Servo claw;
    CRServo intakeLeft;
    CRServo intakeRight;
    Servo wristLeft;
    Servo wristRight;
    DcMotor rightLift;
    DcMotor leftLift;

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
        LIFT
    }
    ServoTypes which;
    //TODO: Step 4, replace all names of Servos with yours, and replace all capitals with what you set them to from step 3

    private void masterTuner() {
        if (gamepad1.left_bumper) {
            if (which == ServoTypes.LID) {
                claw.setPosition(claw.getPosition() - speedAmount);
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
                wristLeft.setPosition(wristLeft.getPosition() - speedAmount);
                wristRight.setPosition(wristRight.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.INTAKE) {
                //intake.setPosition(intake.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.LIFT) {
                rightLift.setPower(0.1);
                leftLift.setPower(0.1);
            }

        }
        else if (gamepad1.right_bumper) {
            if (which == ServoTypes.LID) {
                claw.setPosition(claw.getPosition() + speedAmount);
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
                wristLeft.setPosition(wristLeft.getPosition() + speedAmount);
                wristRight.setPosition(wristRight.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.INTAKE) {
                //intake.setPosition(intake.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.LIFT) {
                rightLift.setPower(-0.1);
                leftLift.setPower(-0.1);
            }
        }
        else {
            rightLift.setPower(0);
            leftLift.setPower(0);
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
        else if (gamepad1.a) {
            which = ServoTypes.LIFT;
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
    private void scuffedIntake() {
        if (gamepad2.left_stick_button) {
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
        }
        if (gamepad2.right_stick_button) {
            intakeRight.setPower(-1);
            intakeLeft.setPower(-1);
        }
        if (!gamepad2.right_stick_button && !gamepad2.left_stick_button || gamepad2.right_stick_button && gamepad2.left_stick_button) {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }
    }

    //TODO: Step 7, your done! This was written by Goober on 11/5/23 slouching in a chair at 10:35 in the morning.
    // And as I'm writing this I wonder if anybody will actually use this. Problably not,
    // but idk what to do while I wait for the robot to be ready for calibration. It's now 10:36 am.
    // I wonder if future me will use this and or remember doing this. Goober out. Edit: I have to recode :( 11/28/23

    //TODO: Step 6 replace all of the xyz.getPosition()0; with your servos and replace "xyz" with what that servo is
    private void whatServoAt() {
        telemetry.addData("claw (Y) = ", claw.getPosition());
        telemetry.addData("slideRight (X) = ", slideRight.getPosition());
        telemetry.addData("outtakeLeft (B) = ", outtakeLeft.getPosition());
        telemetry.addData("outtakeRight (B) = ", outtakeRight.getPosition());
        telemetry.addData("slideLeft (X) = ", slideLeft.getPosition());
        telemetry.addData("wrist (Dpad R)= ", wristLeft.getPosition());
        telemetry.addData("alvin and the chipmuks", 0);
        //telemetry.addData("intake (Dpad L)= ", intake.getPosition());
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
        claw = hardwareMap.get(Servo.class, "claw");
        //intake = hardwareMap.get(CRServo.class, "intake");
        wristLeft = hardwareMap.get(Servo.class, "wristLeft");
        wristRight = hardwareMap.get(Servo.class, "wristRight");
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");

        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");

        //sets direction to reverse
        slideLeft.setDirection(Servo.Direction.REVERSE);
        slideRight.setDirection(Servo.Direction.REVERSE);
        claw.setDirection(Servo.Direction.REVERSE);
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //
        //
        // intake.setDirection(DcMotorSimple.Direction.REVERSE);

        SmartServo.setSmartPos(hardwareMap,"slideLeft", 0.0);
        SmartServo.setSmartPos(hardwareMap,"slideRight", 0.0);
        SmartServo.setSmartPos(hardwareMap,"wristLeft", 0.44);
        SmartServo.setSmartPos(hardwareMap,"wristRight", 0.44);
        SmartServo.setSmartPos(hardwareMap,"outtakeRight", 0.18);
        SmartServo.setSmartPos(hardwareMap,"outtakeLeft", 0.18);
        SmartServo.setSmartPos(hardwareMap,"claw", 0.0);


        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            setServo();
            masterTuner();
            setExtra();
            whatServoAt();
            //telemetry.addData("Selected", which.toString());
            //telemetry.addLine("Y = Shoulder - X = Hopper - B = Wrist A = Lift");
            scuffedIntake();
            telemetry.update();
            sleep(100);
            idle();
        }
    }
}