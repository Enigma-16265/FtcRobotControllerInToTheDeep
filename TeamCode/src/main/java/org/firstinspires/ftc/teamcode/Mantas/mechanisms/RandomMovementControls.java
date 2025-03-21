//imports
package org.firstinspires.ftc.teamcode.Mantas.mechanisms;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * turning the robot 180 degrees
 * and possibly other stuff later
 */

public class RandomMovementControls {

    //objects
    private final DcMotor rightFrontWheel;
    private final DcMotor leftFrontWheel;
    private final DcMotor rightBackWheel;
    private final DcMotor leftBackWheel;
    private final Gamepad gamePad;

    //not objects
    private double speed = 1;
    private double last_time_a_pressed;
    private boolean currently_spinning = false;
    private int cycle_number_spin = 13;

    // constructor
    public RandomMovementControls(HardwareMap hardwareMap, Gamepad gamePad) {
        //sets all the wheels to what they are in the hardware map
        rightFrontWheel = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontWheel = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackWheel = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackWheel = hardwareMap.get(DcMotor.class, "leftBack");

        //says if each wheel should be backwards according to the hardware map or forwards
        leftFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        leftBackWheel.setDirection(DcMotor.Direction.REVERSE);
        rightFrontWheel.setDirection(DcMotor.Direction.FORWARD);
        rightBackWheel.setDirection(DcMotor.Direction.FORWARD);

        this.gamePad = gamePad;

    }

    public void startSpin() {
        currently_spinning = true;
        cycle_number_spin += 20;
    }

    // the function for spinning the robot 180 degrees
    public void spinAroundFunction() {
        // I could have just put a for loop here but I did not know I was making one at the time

        //if a was pressed do not let spinAroundFunction run for another second
        if (gamePad.a && ((last_time_a_pressed + 1000) < System.currentTimeMillis())) {
            last_time_a_pressed = System.currentTimeMillis();
            currently_spinning = true;
        }


        if (currently_spinning) {

            leftFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //minus 1 to cycle_number
            cycle_number_spin--;

            leftFrontWheel.setPower(speed);
            leftBackWheel.setPower(speed);
            rightFrontWheel.setPower(-speed);
            rightBackWheel.setPower(-speed);

            //stop spinning if the robot has spun 180 degrees
            if (cycle_number_spin == 0) {

                //reset variables so previous code can be repeated
                currently_spinning = false;
                cycle_number_spin = 13;
            }
        }
    }
}