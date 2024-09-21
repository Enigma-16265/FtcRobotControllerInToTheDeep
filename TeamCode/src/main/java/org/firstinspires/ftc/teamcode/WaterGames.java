package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class WaterGames extends LinearOpMode {
    //TODO: Give servoh some friends.
    Servo servoh;
    DcMotor intakeMotor;
    //TODO: Variables go here.
    int Integer = 1;
    double triggerThreshold = 0.4;
    boolean gamepad1_y_OAD = false;
    boolean gamepad1_y_LU = false;



   //TODO: Make some functions
    private void testFunction() {
        servoh.setPosition(0.5);
    }
    private void driveMain() {
        //nyoooooooooooooom
    }
    private void intake() {
        if (gamepad1.right_trigger > triggerThreshold && gamepad1.left_trigger < triggerThreshold) {
            intakeMotor.setPower(1);
        }
    }
    private void intakeIdle() {
        if (gamepad1.right_trigger > triggerThreshold && gamepad1.left_trigger < triggerThreshold) {
            intakeMotor.setPower(0);
        }
        if (gamepad1.right_trigger < triggerThreshold && triggerThreshold < triggerThreshold) {
            intakeMotor.setPower(0);
        }
    }
    private void specimenIntake() {

    }
    private void oneAndDone() {
        //----------Y-------------
        //o n e a n d d o n e
        if (gamepad1_y_OAD == true) {
            gamepad1_y_OAD = false;
        }
        //Y release detection
        if (gamepad1.y == false && gamepad1_y_LU == true) {
            gamepad1_y_OAD = false;
        }
        //Y press detection
        if (gamepad1.y == true && gamepad1_y_LU == false) {
            gamepad1_y_OAD = true;
        }
        //------------/Y-----------




        oneAndDoneUpdate();
    }
    private void oneAndDoneUpdate() {
        //Last Instance Update
        gamepad1_y_LU = gamepad1.y;
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
            testFunction();
            intake();
            intakeIdle();
            telemetry.update();
            sleep(1);
        }
    }
}