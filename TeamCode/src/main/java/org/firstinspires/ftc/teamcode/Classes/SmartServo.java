package org.firstinspires.ftc.teamcode.Classes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
import java.util.Objects;


public class SmartServo {

    static class SingleServoConstraints {

        double low, high;
        SingleServoConstraints(double low, double high){
            this.low = low;
            this.high = high;
        }

    }


    static HashMap<String, SingleServoConstraints> constraintsForEachServo = new HashMap<String, SingleServoConstraints>();
    static boolean isInitialized = false;
    static  void init() {
        if(!isInitialized) {
            isInitialized = true;

            constraintsForEachServo.put("slideLeft", new SingleServoConstraints(0.0, 0.5));
            constraintsForEachServo.put("slideRight", new SingleServoConstraints(0.0, 0.5));
            constraintsForEachServo.put("outtakeLeft", new SingleServoConstraints(0.0, 1.0));
            constraintsForEachServo.put("outtakeRight", new SingleServoConstraints(0.0, 1.0));
            constraintsForEachServo.put("lid", new SingleServoConstraints(0.32, 0.63));
            constraintsForEachServo.put("intake", new SingleServoConstraints(0.1, 0.6));
            constraintsForEachServo.put("intakePivot", new SingleServoConstraints(0.31, 0.8));
        }
    }

    static public void setSmartPos(HardwareMap hardwareMap, String servoName, double setTo) {

        init();

        Servo servoToUse;
        servoToUse = hardwareMap.get(Servo.class, servoName);
        SingleServoConstraints thisServosConstraints = Objects.requireNonNull(SmartServo.constraintsForEachServo.get(servoName));


        // strange option?
        if (setTo < thisServosConstraints.low){
            servoToUse.setPosition(thisServosConstraints.low);
        } else if (setTo > thisServosConstraints.high){
            servoToUse.setPosition(thisServosConstraints.high);
        } else {
            servoToUse.setPosition(setTo);
        }

    }

}