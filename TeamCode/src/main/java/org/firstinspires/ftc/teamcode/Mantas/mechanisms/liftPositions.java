//imports
package org.firstinspires.ftc.teamcode.Mantas.mechanisms;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class liftPositions {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;
    private final Telemetry telemetry;

    // constructor
    public liftPositions(HardwareMap hardwareMap, Gamepad gamePad, Telemetry telemetry) {
        rightLift = hardwareMap.get(DcMotor.class, "rightHang");
        leftLift = hardwareMap.get(DcMotor.class, "leftHang");

        this.gamePad = gamePad;

        this.telemetry = telemetry;

    }

    public void makeLiftsWork() {

        if (gamePad.dpad_up) {

            //make the right lift go to the max height
            rightLift.setPower(1);
            rightLift.setTargetPosition(6188);

            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // code that might work
            /*while (rightLift.isBusy()) {
                leftLift.setPower(1);
                telemetry.update();

            }*/
        }

        if (gamePad.dpad_down) {

            //make the right lift go to the min height
            rightLift.setPower(1);
            rightLift.setTargetPosition(0);
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //code that might work
            /*while (rightLift.isBusy()) {
                leftLift.setPower(-1);
                telemetry.update();
            }*/
        }
    }
}