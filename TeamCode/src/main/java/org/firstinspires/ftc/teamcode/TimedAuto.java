package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Autonomous(name="TimedAuto", group="Robot")
public class TimedAuto extends LinearOpMode {
    private final DcMotor rightFront;
    private final DcMotor leftFront;
    private final DcMotor rightBack;
    private final DcMotor leftBack;
    private final Gamepad gamePad;

    /** @noinspection FieldMayBeFinal, FieldCanBeLocal */ //not objects
    // constructor
    public TimedAuto (@NonNull HardwareMap hardwareMap, Gamepad gamePad) {
        //sets all the wheels to what they are in the hardware map
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");

        //says if each wheel should be backwards according to the hardware map or forwards
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        this.gamePad = gamePad;
    }

    private void left() {
        leftFront.setPower(-0.5);
        rightBack.setPower(-0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(0.5);
    }
    private void right() {
        leftFront.setPower(0.5);
        rightBack.setPower(0.5);
        leftBack.setPower(-0.5);
        rightFront.setPower(-0.5);
    }
    private void forward() {
        leftFront.setPower(0.5);
        rightBack.setPower(0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(0.5);
    }
    private void backward() {
        leftFront.setPower(-0.5);
        rightBack.setPower(-0.5);
        leftBack.setPower(-0.5);
        rightFront.setPower(-0.5);
    }
    private void rotateLeft() {
        leftFront.setPower(0.5);
        rightBack.setPower(-0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(-0.5);
    }
    private void rotateRight() {
        leftFront.setPower(-0.5);
        rightBack.setPower(0.5);
        leftBack.setPower(-0.5);
        rightFront.setPower(0.5);
    }
    private void brake() {
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
    }
    private void zzz(long howlong) {
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        sleep(howlong);
    }
    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();
        // while (opModeInInit()) {}

       // while (opModeIsActive()) {
            telemetry.addData("Status", "Running.");
            telemetry.update();
            sleep(1000);
            forward();
            sleep(150);
            left();
            sleep(1000);
            brake();
            sleep(1000);
            rotateRight();
            sleep(500);
            backward();
            sleep(100);
            brake();
            //do the thing
        //}

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }

}
