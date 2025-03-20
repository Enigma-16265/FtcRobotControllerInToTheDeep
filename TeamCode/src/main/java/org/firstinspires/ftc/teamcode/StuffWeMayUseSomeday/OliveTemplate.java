package org.firstinspires.ftc.teamcode.StuffWeMayUseSomeday;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Mantas.opmodes.OliversDriveCode;

abstract public class OliveTemplate extends LinearOpMode {

    private boolean buttonPressed[] = new boolean[14];

    private enum Buttons {
        aButton,
        bButton,
        xButton,
        yButton,
        leftBumper,
        leftTrigger,
        rightBumper,
        rightTrigger,
        leftStick,
        rightStick,
        dpadUp,
        dpadDown,
        dpadLeft,
        dpadRight,
    }

    public void test() {
        if(gamepad1.a) {
            telemetry.addData("a got pressed","AAAAA");
        }
    }

    /*
       REDO THIS FUNCTION!
       return object with booleans for each button
       only have to run function once every loop with this method
     */
    /*private boolean checkButton(int crtlNumber, Buttons button, boolean held) {
        /*
            CRAPPY CODE AHEAD!!! BEWARE!! ⚠️⚠️⚠️🚨‼️‼️
            I dont know how I would improve it...

            If held not referenced; defaults to false
            If crtlNumber not referenced; defaults to 1
            If held = true; returns true when button is held
            If held = false; returns true only when user begins to press the button

            if(checkButton([CONTROLLER], Buttons.[BUTTON], [BOOL])) [CODE];
        //

        switch (button) {
            case aButton:
                if (gamepad1.a) {
                    if (!buttonPressed[0]) {
                        buttonPressed[0] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[0] = false;
                }
                break;

            case bButton:
                if (gamepad1.b) {
                    if (!buttonPressed[1]) {
                        buttonPressed[1] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[1] = false;
                }
                break;

            case xButton:
                if (gamepad1.x) {
                    if (!buttonPressed[2]) {
                        buttonPressed[2] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[2] = false;
                }
                break;

            case yButton:
                if (gamepad1.y) {
                    if (!buttonPressed[3]) {
                        buttonPressed[3] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[3] = false;
                }
                break;

            case leftBumper:
                if (gamepad1.left_bumper) {
                    if (!buttonPressed[4]) {
                        buttonPressed[4] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[4] = false;
                }
                break;

            case leftTrigger:
                if (gamepad1.left_trigger > 0.5) {
                    if (!buttonPressed[5]) {
                        buttonPressed[5] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[5] = false;
                }
                break;

            case rightBumper:
                if (gamepad1.right_bumper) {
                    if (!buttonPressed[6]) {
                        buttonPressed[6] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[6] = false;
                }
                break;

            case rightTrigger:
                if (gamepad1.right_trigger > 0.5) {
                    if (!buttonPressed[7]) {
                        buttonPressed[7] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[7] = false;
                }
                break;

            case leftStick:
                if (gamepad1.left_stick_button) {
                    if (!buttonPressed[8]) {
                        buttonPressed[8] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[8] = false;
                }
                break;

            case rightStick:
                if (gamepad1.right_stick_button) {
                    if (!buttonPressed[9]) {
                        buttonPressed[9] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[9] = false;
                }
                break;

            case dpadUp:
                if (gamepad1.dpad_up) {
                    if (!buttonPressed[10]) {
                        buttonPressed[10] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[10] = false;
                }
                break;

            case dpadDown:
                if (gamepad1.dpad_down) {
                    if (!buttonPressed[11]) {
                        buttonPressed[11] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[11] = false;
                }
                break;

            case dpadLeft:
                if (gamepad1.dpad_left) {
                    if (!buttonPressed[12]) {
                        buttonPressed[12] = true;
                    } else if (held) return true;
                } else {
                    buttonPressed[12] = false;
                }
                break;

            case dpadRight:
                if (gamepad1.dpad_right) {
                    if (!buttonPressed[13]) {
                        buttonPressed[13] = true;
                        return true;
                    } else if (held) return true;
                } else {
                    buttonPressed[13] = false;
                }
                break;
        }

        return false;
    }

    private boolean checkButton(Buttons button) {
        return checkButton(button, false);
    }*/
}
