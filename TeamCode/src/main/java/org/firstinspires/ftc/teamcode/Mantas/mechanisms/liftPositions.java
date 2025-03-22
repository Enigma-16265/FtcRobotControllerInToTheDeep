//imports
package org.firstinspires.ftc.teamcode.Mantas.mechanisms;




import static android.os.SystemClock.sleep;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class liftPositions {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;
    private final Telemetry telemetry;
    public double setPoint;

    // constructor
    public liftPositions(HardwareMap hardwareMap, Gamepad gamePad, Telemetry telemetry) {
        rightLift = hardwareMap.get(DcMotor.class, "rightHang");
        leftLift = hardwareMap.get(DcMotor.class, "leftHang");

        this.gamePad = gamePad;
        this.telemetry = telemetry;

    }

    public void makeLiftsWork() {
        //code that might work

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
        if (setPoint - leftLift.getCurrentPosition() >= 30) {
            rightLift.setPower(1);
        } else if (setPoint - leftLift.getCurrentPosition() <= -30) {
            rightLift.setPower(-1);
        } else {
            rightLift.setPower(0);
        }
    }
}
