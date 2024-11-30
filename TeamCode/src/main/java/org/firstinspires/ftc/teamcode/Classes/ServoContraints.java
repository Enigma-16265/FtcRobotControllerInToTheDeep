package org.firstinspires.ftc.teamcode.Classes;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
import java.util.Objects;


class ServoConstraints {

    class SingleServoConstraints {

        double low, high;
        SingleServoConstraints(double low, double high){
            this.low = low;
            this.high = high;
        }

    }

    static HashMap<String, SingleServoConstraints> constraintsForEachServo = new HashMap<String, SingleServoConstraints>();

    public void init() {
        constraintsForEachServo.put("wrist", new SingleServoConstraints(.1, .9));
    }

    public void setSmartPos(HardwareMap hardwareMap, String servoName, double setTo) {
        Servo servoToUse;
        servoToUse = hardwareMap.get(Servo.class, servoName);
        SingleServoConstraints thisServosConstraints = Objects.requireNonNull(ServoConstraints.constraintsForEachServo.get(servoName));

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