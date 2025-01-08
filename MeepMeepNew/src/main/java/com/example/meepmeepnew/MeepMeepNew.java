package com.example.meepmeepnew;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.NotNull;



public class MeepMeepNew {

    public static class GoDunk implements Action {
        @Override
        public boolean run(@NotNull TelemetryPacket telemetryPacket) {
            return false;
        }
    }

    public class Drive {

        public Action driveToDunkPos() {
            return new GoDunk();
        }
    }


    public static void main(String[] args) {

        String autoType = "specimen";

        Pose2d beginPose = new Pose2d(12,-62, Math.toRadians(90));

        Pose2d leftBasket = new Pose2d(-60,-60, Math.toRadians(90));
        Pose2d specimenPose = new Pose2d(0, -38, Math.toRadians(90));
        Pose2d humanPlayerPose = new Pose2d(48, -54, Math.toRadians(90));

        Pose2d coloredSample1 = new Pose2d(37, -28, Math.toRadians(90));
        //Pose2d coloredSample1 = new Pose2d(48, -8, Math.toRadians(90));
        Pose2d coloredSample2 = new Pose2d(58, -8, Math.toRadians(90));
        Pose2d coloredSample3 = new Pose2d(64, -8, Math.toRadians(90));



        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        if (autoType == "sample") {
            myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                    .setTangent(135)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    //.addDisplacementMarker(() -> {
                    //  // This marker runs after the first splineTo()

                    // Run your action in here!
                    //})
                    .setTangent(90)
                    .splineToLinearHeading(new Pose2d(-37, -24, Math.toRadians(180)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(-44)
                    .lineToX(-42)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(-42, -24, Math.toRadians(180)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(-52)
                    .lineToX(-50)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(-46, -24, Math.toRadians(180)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(-60)
                    .lineToX(-58)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .build());
        }
        if (autoType == "specimen") {
            myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                    .splineToLinearHeading(specimenPose, Math.toRadians(270))
                    //Scored 1st
                    .lineToY(-47)
                    .splineToLinearHeading(coloredSample1, Math.toRadians(123))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(coloredSample2, Math.toRadians(270))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(coloredSample3, Math.toRadians(270))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90))
                    //SCored 2nd
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90))
                    //Scored 3rd
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90))
                    //Scored 4th
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90))
                    //Scored5th
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .build());
        }
        if (autoType == "test") {
            //Drive drive = new Drive();

            myBot.runAction(
                    new SequentialAction(
                        new SleepAction(100),
                            new GoDunk()
                    )
            );
        }



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}