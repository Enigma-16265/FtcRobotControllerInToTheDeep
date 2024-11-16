package org.firstinspires.ftc.teamcode.FunStuffs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ControlClassFiles.RandomMovementControls;
import org.firstinspires.ftc.teamcode.ControlClassFiles.subtleServoMoveThread;
import org.firstinspires.ftc.teamcode.RosesLinearOpMode;

public class HappyDance {

    // create instance of RandomMovementControls
    private final HardwareMap hardwareMap;
    private final Gamepad gamePad;
    RandomMovementControls spinAroundFunction;
    SoundThing soundPlayer;
    RosesLinearOpMode opMode;

    // constructor
    public HappyDance (HardwareMap hardwareMap, Gamepad gamePad, RosesLinearOpMode opMode) {
        spinAroundFunction = new RandomMovementControls (hardwareMap, gamePad);
        soundPlayer = new SoundThing(hardwareMap);

        //hardware map and gamepad
        this.gamePad = gamePad;
        this.hardwareMap = hardwareMap;
        this.opMode = opMode;
    }


    private void moveLift() {
        subtleServoMoveThread upLift = new subtleServoMoveThread("lift", 0.7, 1.5, hardwareMap, opMode);
        upLift.start();
        subtleServoMoveThread downLift = new subtleServoMoveThread("lift", 0.06, 1.5, upLift, hardwareMap, opMode);
        downLift.start();
    }

    private void moveClaw() {
        subtleServoMoveThread open = new subtleServoMoveThread("claw", 1, 3, hardwareMap, opMode);
        open.start();
        subtleServoMoveThread close = new subtleServoMoveThread("claw", 0.4, 3, open, hardwareMap, opMode);
        close.start();
    }

    // happy dance function
    double lastXClicked = 0;
    public void doHappyDance() {

        if (gamePad.x && lastXClicked + 1000 < System.currentTimeMillis()) {
            spinAroundFunction.startSpin();
            moveLift();
            lastXClicked = System.currentTimeMillis();
            moveClaw();
            soundPlayer.runSounds();
        }
        spinAroundFunction.spinAroundFunction();
    }





}
