package com.example.meepdeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepDeep {

    // Left, Left Specimen, Right, Right Specimen



    public static void main(String[] args) {

        int Auto = 4;

        Pose2d leftBasket = new Pose2d(-54, -54, 315);

        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        //Left
        if (Auto == 1) {
            myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -61.5, Math.toRadians(90)))
                    .setTangent(135)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
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
        // Left Specimen
        if (Auto == 2) {
            myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-36, -58, Math.toRadians(90)))
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
        // Right
        if (Auto == 3) {
            myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-36, -58, Math.toRadians(90)))
                    .setTangent(135)
                    .splineToLinearHeading(leftBasket, Math.toRadians(235))
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
        // Right Specimen
        if (Auto == 4) {
            myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(36, -58, Math.toRadians(90)))
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