package org.firstinspires.ftc.teamcode.Classes;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;


class ServoConstraints {

    class SingleServoConstraints {

        double low, high;
        SingleServoConstraints(double low, double high){
            this.low = low;
            this.high = high;
        }

    }

    static HashMap<String, SingleServoConstraints> constraintsForEachServo = new HashMap<String, SingleServoConstraints>();

    void init() {
        constraintsForEachServo.put("wrist", new SingleServoConstraints(.1, .9));
    }

    public void setWContraints(HardwareMap hardwareMap, String servoName, double setTo) {
        Servo servoToUse;
        servoToUse = hardwareMap.get(Servo.class, servoName);

        // strange option?
        if (setTo < ServoConstraints.constraintsForEachServo.get(servoName).low){
            servoToUse.setPosition(ServoConstraints.constraintsForEachServo.get(servoName).low);
        } else if (setTo > ServoConstraints.constraintsForEachServo.get(servoName).high){
            servoToUse.setPosition(ServoConstraints.constraintsForEachServo.get(servoName).high);
        } else {
            servoToUse.setPosition(setTo);
        }

    }

}