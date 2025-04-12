package org.firstinspires.ftc.teamcode.Link.Classes;

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

            constraintsForEachServo.put("slideLeft", new SingleServoConstraints(0.0, 0.28));
            constraintsForEachServo.put("slideRight", new SingleServoConstraints(0.0, 0.28));
            constraintsForEachServo.put("outtakeLeft", new SingleServoConstraints(0, 1)); //Arm btw
            constraintsForEachServo.put("outtakeRight", new SingleServoConstraints(0, 1)); //Init .51
            constraintsForEachServo.put("intake", new SingleServoConstraints(0.3, 0.075)); //.75
            constraintsForEachServo.put("wristLeft", new SingleServoConstraints(0, 1));
            constraintsForEachServo.put("claw", new SingleServoConstraints(0.6, 0.92));
            constraintsForEachServo.put("clawWrist", new SingleServoConstraints(0, 0.55));
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