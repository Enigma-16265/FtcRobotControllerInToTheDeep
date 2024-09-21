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
        loopToOnce(gamepad1.y);
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
            testFunction();
            intake();
            intakeIdle();
            telemetry.update();
            sleep(1);
        }
    }
}