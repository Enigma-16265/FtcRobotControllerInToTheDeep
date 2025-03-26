//imports
package org.firstinspires.ftc.teamcode.Mantas.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class liftPositions {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;
    public double setPoint;

    // constructor
    public liftPositions(HardwareMap hardwareMap, Gamepad gamePad) {
        rightLift = hardwareMap.get(DcMotor.class, "rightHang");
        leftLift = hardwareMap.get(DcMotor.class, "leftHang");

        this.gamePad = gamePad;

    }

    public void makeLiftsWork() {

        if (gamePad.dpad_up) {

            //make the right lift go to the max height
            leftLift.setPower(1);
            leftLift.setTargetPosition(3000); //6188
            setPoint = 3000;
            leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        if (gamePad.dpad_down) {

            //make the right lift go to the min height
            leftLift.setPower(1);
            leftLift.setTargetPosition(0);
            setPoint = 0;
            leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }
        //if the left lift is not close to its destination, the right lift moves
        if (setPoint - leftLift.getCurrentPosition() >= 30) {
            rightLift.setPower(1);
        } else if (setPoint - leftLift.getCurrentPosition() <= -30) {
            rightLift.setPower(-1);
        } else {
            rightLift.setPower(0);
        }
    }
}
