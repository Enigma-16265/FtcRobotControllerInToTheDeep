//imports
package org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class liftPositions2 {

    //objects
    private final DcMotor rightLift;
    private final DcMotor leftLift;
    private final Gamepad gamePad;

    // constructor
    public liftPositions2(HardwareMap hardwareMap, Gamepad gamePad) {
        //sets all the wheels to what they are in the hardware map
        rightLift = hardwareMap.get(DcMotor.class, "rightHang");
        leftLift = hardwareMap.get(DcMotor.class, "leftHang");

        this.gamePad = gamePad;

    }

    public void makeLiftsWork() {

        if (gamePad.dpad_up) {

            rightLift.setPower(1);
            rightLift.setTargetPosition(6188);
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if (gamePad.dpad_down) {

            rightLift.setPower(1);
            rightLift.setTargetPosition(0);
            rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
}