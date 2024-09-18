package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class WaterGames extends LinearOpMode {
    //TODO: Give servoh some friends.
    Servo servoh;
    //TODO: Variables go here.
    int Integer = 1;


   //TODO: Make some functions
    private void testFunction() {
        servoh.setPosition(0.5);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        //TODO: Add hadware mappings and servo init positions
        //servosample = hardwareMap.get(Servo.class, "servosample");

        //Servo init positions


        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            testFunction();
            telemetry.update();
            sleep(1);
        }
    }
}