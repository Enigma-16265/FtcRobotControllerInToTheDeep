//imports
package org.firstinspires.ftc.teamcode.Mantas.mechanisms;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class seconedLiftCode {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;
    private final Telemetry telemetry;

    // constructor
    public seconedLiftCode(HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {
        rightLift = hardwareMap.get(DcMotor.class, "rightHang");
        leftLift = hardwareMap.get(DcMotor.class, "leftHang");

        this.gamePad = gamepad1;

        this.telemetry = telemetry;

    }

    public void makeLiftsWork() {
        if (gamePad.dpad_up) {
            rightLift.setPower(-1);
            leftLift.setPower(-1);
        }
    }
}
