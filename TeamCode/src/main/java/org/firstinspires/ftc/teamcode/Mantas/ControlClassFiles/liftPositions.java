//imports
package org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class liftPositions {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;

    //not objects
    private double last_time_x_pressed;
    private double last_time_y_pressed;
    private double last_time_dPadRight_pressed;
    private double pauseTimeMillis;

    enum liftStages {
        isFullyUp,
        isInMiddle,
        isFullyDown,
        inactive
    }

    private liftStages currently_lifting = liftStages.isFullyDown;
    // constructor
    public liftPositions(HardwareMap hardwareMap, Gamepad gamePad) {
        //sets all the wheels to what they are in the hardware map
        rightLift = hardwareMap.get(DcMotor.class, "rightHang");
        leftLift = hardwareMap.get(DcMotor.class, "leftHang");

        this.gamePad = gamePad;

    }

    public void makeLiftsWork() {

        if (gamePad.x && last_time_x_pressed + 1000 < System.currentTimeMillis()) {
            last_time_x_pressed = System.currentTimeMillis();
            rightLift.setDirection(DcMotor.Direction.FORWARD);
            leftLift.setDirection(DcMotor.Direction.FORWARD);
            pauseTimeMillis = System.currentTimeMillis();

            if (currently_lifting == liftStages.isFullyDown) {

                rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                while (pauseTimeMillis + 2700 > System.currentTimeMillis()) {
                    rightLift.setPower(1);
                    leftLift.setPower(1);
                }

                rightLift.setPower(0);
                leftLift.setPower(0);

                currently_lifting = liftStages.isFullyUp;
            }

            if (currently_lifting == liftStages.isInMiddle) {

                rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                while (pauseTimeMillis + 2700 > System.currentTimeMillis()) {
                    rightLift.setPower(1);
                    leftLift.setPower(1);
                }

                rightLift.setPower(0);
                leftLift.setPower(0);

                currently_lifting = liftStages.isFullyUp;
            }
        }

        if (gamePad.dpad_right && last_time_dPadRight_pressed + 1000 < System.currentTimeMillis()) {
            last_time_dPadRight_pressed = System.currentTimeMillis();
            rightLift.setDirection(DcMotor.Direction.FORWARD);
            leftLift.setDirection(DcMotor.Direction.FORWARD);
            pauseTimeMillis = System.currentTimeMillis();

            if (currently_lifting == liftStages.isFullyDown) {

                rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                while (pauseTimeMillis + 1350 > System.currentTimeMillis()) {
                    rightLift.setPower(1);
                    leftLift.setPower(1);
                }

                rightLift.setPower(0);
                leftLift.setPower(0);

                currently_lifting = liftStages.isInMiddle;
            }

            if (currently_lifting == liftStages.isFullyUp) {

                rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                while (pauseTimeMillis + 1350 > System.currentTimeMillis()) {
                    rightLift.setPower(-1);
                    leftLift.setPower(-1);
                }

                rightLift.setPower(0);
                leftLift.setPower(0);

                currently_lifting = liftStages.isInMiddle;
            }
        }

            if (gamePad.y && last_time_y_pressed + 1000 < System.currentTimeMillis()) {
                last_time_y_pressed = System.currentTimeMillis();
                rightLift.setDirection(DcMotor.Direction.REVERSE);
                leftLift.setDirection(DcMotor.Direction.REVERSE);
                pauseTimeMillis = System.currentTimeMillis();

                if (currently_lifting == liftStages.isFullyUp) {

                    rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                    while (pauseTimeMillis + 2700 > System.currentTimeMillis()) {
                        rightLift.setPower(1);
                        leftLift.setPower(1);
                    }

                    rightLift.setPower(0);
                    leftLift.setPower(0);

                    currently_lifting = liftStages.isFullyDown;
                }

                if (currently_lifting == liftStages.isInMiddle) {

                    rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

                    while (pauseTimeMillis + 2700 > System.currentTimeMillis()) {
                        rightLift.setPower(1);
                        leftLift.setPower(1);
                    }

                    rightLift.setPower(0);
                    leftLift.setPower(0);

                    currently_lifting = liftStages.isFullyDown;
                }
            }
        }
    }
