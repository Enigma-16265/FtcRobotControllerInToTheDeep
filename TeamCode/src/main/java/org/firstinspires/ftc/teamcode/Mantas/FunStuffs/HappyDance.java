package org.firstinspires.ftc.teamcode.Mantas.FunStuffs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles.RandomMovementControls;
import org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles.subtleServoMoveThread;

public class HappyDance {

    // create instance of RandomMovementControls
    private final HardwareMap hardwareMap;
    private final Gamepad gamePad;
    RandomMovementControls spinAroundFunction;
    SoundThing soundPlayer;

    // constructor
    public HappyDance (HardwareMap hardwareMap, Gamepad gamePad) {
        spinAroundFunction = new RandomMovementControls (hardwareMap, gamePad);
        soundPlayer = new SoundThing(hardwareMap);

        //hardware map and gamepad
        this.gamePad = gamePad;
        this.hardwareMap = hardwareMap;
    }


    private void moveLift() {
        subtleServoMoveThread upLift = new subtleServoMoveThread("lift", 0.7, 1.5, hardwareMap);
        upLift.start();
        subtleServoMoveThread downLift = new subtleServoMoveThread("lift", 0.06, 1.5, upLift, hardwareMap);
        downLift.start();
    }

    private void moveClaw() {
        subtleServoMoveThread open = new subtleServoMoveThread("claw", 1, 3, hardwareMap);
        open.start();
        subtleServoMoveThread close = new subtleServoMoveThread("claw", 0.4, 3, open, hardwareMap);
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
