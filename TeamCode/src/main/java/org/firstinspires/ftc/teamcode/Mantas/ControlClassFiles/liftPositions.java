//imports
package org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * turning the robot 180 degrees
 * and possibly other stuff later
 */

public class liftPositions {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;

    //not objects
    private double last_time_x_pressed;
    private double last_time_y_pressed;
    enum liftStages{
        isFullyUp,
        isFullyDown,
        inactive
    }
    private liftStages currently_lifting = liftStages.isFullyDown;

    // constructor
    public liftPositions(HardwareMap hardwareMap, Gamepad gamePad) {
        //sets all the wheels to what they are in the hardware map
        rightLift = hardwareMap.get(DcMotor.class, "rightHang");
        leftLift = hardwareMap.get(DcMotor.class, "leftHang");

        //says if each wheel should be backwards according to the hardware map or forwards
        rightLift.setDirection(DcMotor.Direction.FORWARD);
        leftLift.setDirection(DcMotor.Direction.FORWARD);

        this.gamePad = gamePad;

    }

    // the function for spinning the robot 180 degrees
    public void makeLiftsWork() {

        //if a was pressed do not let spinAroundFunction run for another second
        if (gamePad.x && last_time_x_pressed + 1000 < System.currentTimeMillis() && currently_lifting != liftStages.isFullyUp) {
            last_time_x_pressed = System.currentTimeMillis();

            rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //spin the robot 18 degrees
            rightLift.setPower(1);
            leftLift.setPower(1);

            try {
                sleep(2300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            rightLift.setPower(0);
            leftLift.setPower(0);

            //reset variables so previous code can be repeated
            currently_lifting = liftStages.isFullyUp;
        }

        if (gamePad.y && last_time_y_pressed + 1000 < System.currentTimeMillis() && currently_lifting != liftStages.isFullyDown) {
            last_time_y_pressed = System.currentTimeMillis();

            rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //spin the robot 18 degrees
            rightLift.setPower(-1);
            leftLift.setPower(-1);

            try {
                sleep(2300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            rightLift.setPower(0);
            leftLift.setPower(0);

            //reset variables so previous code can be repeated
            currently_lifting = liftStages.isFullyDown;
        }
    }
}
