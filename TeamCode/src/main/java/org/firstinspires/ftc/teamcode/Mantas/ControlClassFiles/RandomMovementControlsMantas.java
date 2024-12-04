//imports
package org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * turning the robot 180 degrees
 * and possibly other stuff later
 */

public class RandomMovementControlsMantas {

    //objects
    private final DcMotor rightFrontWheel;
    private final DcMotor leftFrontWheel;
    private final DcMotor rightBackWheel;
    private final DcMotor leftBackWheel;
    private final Gamepad gamePad;

    /** @noinspection FieldMayBeFinal, FieldCanBeLocal */ //not objects
    private double speed = 1;
    private double last_time_a_pressed;
    private double last_time_b_pressed;
    private boolean currently_spinning = false;
    private boolean currently_doing_a_lap = false;
    private int cycle_number_spin = 10;
    private int cycle_number_lap = 10;

    // constructor
    public RandomMovementControlsMantas (HardwareMap hardwareMap, Gamepad gamePad) {
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

    public void startSpin () {
        currently_spinning = true;
        cycle_number_spin += 20;
    }

    // the function for spinning the robot 180 degrees
    public void spinAroundFunction () {

        //if a was pressed do not let spinAroundFunction run for another second
        if (gamePad.a && last_time_a_pressed + 1000 < System.currentTimeMillis()) {
            last_time_a_pressed = System.currentTimeMillis();
            currently_spinning = true;
        }


        if (currently_spinning) {

            //minus 1 to cycle_number
            cycle_number_spin--;

            //spin the robot 18 degrees
            leftFrontWheel.setPower(speed);
            leftBackWheel.setPower(speed);
            rightFrontWheel.setPower(-speed);
            rightBackWheel.setPower(-speed);

            //stop spinning if the robot has spun 180 degrees
            if (cycle_number_spin == 0) {

                //reset variables so previous code can be repeated
                currently_spinning = false;
                cycle_number_spin = 10;
            }
        }
    }
    public void makeTheRobotDoALap() {

        //if a was pressed do not let spinAroundFunction run for another second
        if (gamePad.b && last_time_b_pressed + 1000 < System.currentTimeMillis()) {
            last_time_b_pressed = System.currentTimeMillis();
            currently_doing_a_lap = true;
        }


        if (currently_doing_a_lap) {

            cycle_number_lap--;

            //move the robot forward
            leftFrontWheel.setPower(speed);
            leftBackWheel.setPower(speed);
            rightFrontWheel.setPower(speed);
            rightBackWheel.setPower(speed);

            if (cycle_number_lap == 0) {

                //reset variables so previous code can be repeated
                currently_doing_a_lap = false;
                cycle_number_lap = 10;
            }
        }
    }
}
    //27