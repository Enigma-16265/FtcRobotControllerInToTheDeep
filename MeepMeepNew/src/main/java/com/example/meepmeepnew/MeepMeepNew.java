package com.example.meepmeepnew;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepNew {


    public static void main(String[] args) {

        String autoSide = "left";

        String autoType = "sample";

        Pose2d beginPose = new Pose2d(-30,-60, Math.toRadians(90));

        Pose2d leftBasket = new Pose2d(-60,-60, Math.toRadians(90));

        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        if (autoSide == "left" && autoType == "sample") {
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
        if (autoSide == "left" && autoType == "specimen") {
            myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                    .setTangent(Math.toRadians(90))
                    .splineToConstantHeading(new Vector2d(0, -36), Math.PI/2)
                    .lineToY(-46)
                    .setTangent(0)
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
        if (autoSide == "right" && autoType == "sample") {
            myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                    .setTangent(135)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(39, -24, Math.toRadians(0)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(44)
                    .lineToX(42)
                    .setTangent(90)
                    .lineToY(-30)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(44, -24, Math.toRadians(0)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(52)
                    .lineToX(50)
                    .setTangent(90)
                    .lineToY(-30)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(48, -24, Math.toRadians(0)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(60)
                    .lineToX(58)
                    .setTangent(90)
                    .lineToY(-30)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .build());
        }
        if (autoSide == "right" && autoType == "sample") {
            myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                    .splineToConstantHeading(new Vector2d(0, -36), Math.toRadians(90))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(37, -24, Math.toRadians(0)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(44)
                    .lineToX(42)
                    .setTangent(90)
                    .lineToY(-30)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(42, -24, Math.toRadians(0)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(52)
                    .lineToX(50)
                    .setTangent(90)
                    .lineToY(-30)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .setTangent(0)
                    .splineToLinearHeading(new Pose2d(46, -24, Math.toRadians(0)), Math.PI / 2)
                    .setTangent(Math.PI / 1)
                    .lineToX(60)
                    .lineToX(58)
                    .setTangent(90)
                    .lineToY(-30)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
                    .build());
        }

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}