package org.firstinspires.ftc.teamcode.clawTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hypotheticals.ColorLocator_Loralei;
@Autonomous(name = "Concept: Test", group = "Concept")
public class IntakeClaw extends ColorLocator_Loralei {

    @Override
    public void runOpMode() {
        super.Setup();
        while (opModeIsActive() || opModeInInit()){
            super.colorFinder();
            if (super.angle >= 1) {
            telemetry.addLine("Angle >= 0");
            }
        }
    }
}
