package org.firstinspires.ftc.teamcode.clawTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mantas.ControlClassFiles.RosesMcMuffinClass;
import org.firstinspires.ftc.teamcode.hypotheticals.ColorLocator_Loralei;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Concept: Test", group = "Teleop")
public class IntakeClaw extends ColorLocator_LoraleiButEditedToWorkForIntakeClaw {
    RosesMcMuffinClass movement = new RosesMcMuffinClass(hardwareMap, gamepad1);

    @Override
    public void runOpMode() {
        super.Setup();
        //Take the arcos to find the angle
        double ydist = myBoxCorners[0].y - myBoxCorners[1].y;
        double xdist = myBoxCorners[0].x - myBoxCorners[1].x;
        double disthypot = Math.pow(xdist,2) + Math.pow(ydist,2);
        double angle = Math.acos((ydist)/(Math.sqrt(disthypot)));
        //whatever servo you're using .setPosition(angle + expirimental modifier)
    }
}
