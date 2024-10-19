package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class WaterGames extends LinearOpMode {
    //TODO: Give servoh some friends.
    Servo servoh;
    Servo specimenServo;
    DcMotor intakeMotor;
    //TODO: Variables go here.
    ElapsedTime elaspedTime;
    double triggerThreshold = 0.4;
    boolean gamepad2_y_OAD = false;
    boolean gamepad2_y_LU = false;
    boolean gamepad2_y_release_OAD = false;

    boolean gamepad2_b_OAD = false;
    boolean gamepad2_b_LU = false;
    boolean gamepad2_b_release_OAD = false;


   //TODO: Make some functions

    private void driveMain() {
        //nyoooooooooooooom
    }
    private void outtakePosHandler() {
        //outtake
    }
    private void outtakeHandler() {
        if (gamepad2_b_OAD == true) {
            //release sample code
        }
    }

    //TODO: add the distance sensor detector lol
    private void intake() {
        if (gamepad1.right_trigger > triggerThreshold && gamepad1.left_trigger < triggerThreshold) {
            intakeMotor.setPower(1);
        }
    }
    private void intakeIdle() {
        if (gamepad1.right_trigger > triggerThreshold && gamepad1.left_trigger < triggerThreshold) {
            intakeMotor.setPower(0);
        }
        if (gamepad1.right_trigger < triggerThreshold && gamepad1.left_trigger < triggerThreshold) {
            intakeMotor.setPower(0);
        }
    }
    private void specimenIntake() {
        if (gamepad2_y_OAD == true) {
            specimenServo.setPosition(1);
        }
        if (gamepad2_y_release_OAD == true) {
            specimenServo.setPosition(0);
        }
    }
    private void oneAndDone() {
        //----------Y-------------
        //o n e a n d d o n e
        if (gamepad2_y_OAD == true) {
            gamepad2_y_OAD = false;
        }
        if (gamepad2_y_release_OAD == true) {
            gamepad2_y_release_OAD = false;
        }
        //Y release detection
        if (gamepad2.y == false && gamepad2_y_LU == true) {
            gamepad2_y_OAD = false;
            gamepad2_y_release_OAD = true;
        }
        //Y press detection
        if (gamepad2.y == true && gamepad2_y_LU == false) {
            gamepad2_y_OAD = true;
            gamepad2_y_release_OAD = false;
        }
        //------------/Y-----------

        //----------B-------------
        //o n e a n d d o n e
        if (gamepad2_b_OAD == true) {
            gamepad2_b_OAD = false;
        }
        if (gamepad2_b_release_OAD == true) {
            gamepad2_b_release_OAD = false;
        }
        //B release detection
        if (gamepad2.b == false && gamepad2_b_LU == true) {
            gamepad2_b_OAD = false;
            gamepad2_b_release_OAD = true;
        }
        //B press detection
        if (gamepad2.b == true && gamepad2_b_LU == false) {
            gamepad2_b_OAD = true;
            gamepad2_b_release_OAD = false;
        }
        //------------/B-----------




        oneAndDoneUpdate();
    }
    private void oneAndDoneUpdate() {
        //Last Instance Update
        gamepad2_y_LU = gamepad1.y;
    }
    private boolean loopToOnce(boolean buttonToEdit) {
        boolean returnVal = true;
        return returnVal;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        //TODO: Add hadware mappings, directions and servo init positions
        //servosample = hardwareMap.get(Servo.class, "servosample");

        //Servo init positions

        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            oneAndDone();
            intake();
            intakeIdle();
            telemetry.update();
            sleep(1);
        }
    }
}