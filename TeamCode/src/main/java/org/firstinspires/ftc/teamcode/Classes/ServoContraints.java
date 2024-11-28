package org.firstinspires.ftc.teamcode.Classes;

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

}