//imports
package org.firstinspires.ftc.teamcode.Link.mechanisms;




import static android.os.SystemClock.sleep;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DeepOuttake2 {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;
    public double setPoint;

    // constructor
    public DeepOuttake2(HardwareMap hardwareMap, Gamepad gamePad) {
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");

        this.gamePad = gamePad;

    }

    public void makeLiftsWork() {
        //code that might work

        if (gamePad.dpad_up) {

            //make the right lift go to the max height
            rightLift.setPower(1);
            rightLift.setTargetPosition(3000);
            setPoint = 3000;
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        if (gamePad.dpad_down) {

            //make the right lift go to the min height
            rightLift.setPower(1);
            rightLift.setTargetPosition(0);
            setPoint = 0;
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }
        //if the right lift is not close to its destination, the left lift moves
        if (setPoint - rightLift.getCurrentPosition() >= 30) {
            leftLift.setPower(1);
        } else if (setPoint - rightLift.getCurrentPosition() <= -30) {
            leftLift.setPower(-1);
        } else {
            leftLift.setPower(0);
        }
    }
}