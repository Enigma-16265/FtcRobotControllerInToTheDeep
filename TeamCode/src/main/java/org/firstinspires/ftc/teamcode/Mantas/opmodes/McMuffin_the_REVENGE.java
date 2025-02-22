package org.firstinspires.ftc.teamcode.Mantas.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class McMuffin_the_REVENGE extends LinearOpMode {
    //TODO: Step 1, Replace all "wrist","hopper", etc with your servos
    Servo wrist;
    Servo elbow;
    Servo shoulder;
    Servo leftLift;
    Servo rightLift;
    Servo wristLeft;
    Servo wristRight;

    //DcMotor frontIntake;
    //DcMotor rearIntake;

    double speedAmount;
    //TODO: Add offset if needed w/ eg double lift
    //double LiftLeftOffset = -.05;

    //TODO: Step 3, set all of your servo names below
    enum ServoTypes{
        SHOULDER,
        ELBOW,
        WRIST,
        CLAW,
        LIFT,
        WRIST_ROTATION,


    }
    ServoTypes which;
    //TODO: Step 4, replace all names of Servos with yours, and replace all capitals with what you set them to from step 3

    private void masterTuner() {
        if (gamepad1.left_bumper) {
            if (which == ServoTypes.SHOULDER) {
                shoulder.setPosition(shoulder.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.ELBOW) {
                elbow.setPosition(elbow.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.WRIST) {
                wristLeft.setPosition(wristLeft.getPosition() - speedAmount);
                wristRight.setPosition(wristRight.getPosition() - speedAmount);
            }
            else if (which == ServoTypes.WRIST_ROTATION) {
                wristLeft.setPosition(wristLeft.getPosition() - speedAmount);
                wristRight.setPosition(wristRight.getPosition() + speedAmount);
            }
        }
        else if (gamepad1.right_bumper) {
            if (which == ServoTypes.SHOULDER) {
                shoulder.setPosition(shoulder.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.ELBOW) {
                elbow.setPosition(elbow.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.WRIST) {
                wristLeft.setPosition(wristLeft.getPosition() + speedAmount);
                wristRight.setPosition(wristRight.getPosition() + speedAmount);
            }
            else if (which == ServoTypes.WRIST_ROTATION) {
                wristLeft.setPosition(wristLeft.getPosition() + speedAmount);
                wristRight.setPosition(wristRight.getPosition() - speedAmount);
            }
        }
    }
    //TODO: Step 5, replace all of your Servo functions below
    private void setServo() {
        if (gamepad1.y) {
            which = ServoTypes.SHOULDER;
        }
        else if (gamepad1.x) {
            which = ServoTypes.ELBOW;
        }
        else if (gamepad1.b) {
            which = ServoTypes.WRIST;
        }
        else if (gamepad1.a) {
            which = ServoTypes.WRIST_ROTATION;
        }
        telemetry.addData("Selected Servo = ", which.toString());
    }

    //TODO: Step 7, your done! This was written by Goober on 11/5/23 slouching in a chair at 10:35 in the morning.
    // And as I'm writing this I wonder if anybody will actually use this. Problably not,
    // but idk what to do while I wait for the robot to be ready for calibration. It's now 10:36 am.
    // I wonder if future me will use this and or remember doing this. Goober out. Edit: I have to recode :( 11/28/23

    //TODO: Step 6 replace all of the xyz.getPosition()0; with your servos and replace "xyz" with what that servo is
    private void whatServoAt() {
        telemetry.addData("Shoulder = ", shoulder.getPosition());
        telemetry.addData("Elbow = ", elbow.getPosition());
        telemetry.addData("Wrist = ", wrist.getPosition());
        telemetry.addData("Lift Left = ",leftLift.getPosition());
        telemetry.addData("Lift Right = ",rightLift.getPosition());
    }

    @Override
    public void runOpMode() {
        which = ServoTypes.SHOULDER;
        speedAmount = 0.01;
        //this is a coment to mAKE git update

        //TODO: Step 2, Replace the device names with your 4 (or more if you use two servos for one task) Into the deep me here, just replace var names and device names
        elbow = hardwareMap.get(Servo.class, "elbow");
        shoulder = hardwareMap.get(Servo.class, "shoulder");
        rightLift = hardwareMap.get(Servo.class, "rightLift");
        leftLift = hardwareMap.get(Servo.class, "leftLift");
        wristLeft = hardwareMap.get(Servo.class, "wristLeft");
        wristRight = hardwareMap.get(Servo.class, "wristRight");

        shoulder.setDirection(Servo.Direction.REVERSE);
        wristRight.setDirection(Servo.Direction.REVERSE);


        wrist.setPosition(0.575);
        shoulder.setPosition(0.425);
        elbow.setPosition(0.5);
        leftLift.setPosition(0.06);
        rightLift.setPosition(0.06);


        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            setServo();
            masterTuner();
            whatServoAt();
            //telemetry.addData("Selected", which.toString());
            //telemetry.addLine("Y = Shoulder - X = Hopper - B = Wrist A = Lift");
            telemetry.update();
            sleep(100);
        }
    }
}