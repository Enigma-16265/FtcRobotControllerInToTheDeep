package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTesting {


    Vector2d testVector = new Vector2d(2,2);
    public static void main(String[] args) {
        //Close = 1
        //Far = 2
        //Close Specimen = 3
        //Far Specimen = 4
        int startPos = 4;


        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot;

        Pose2d basketPos = new Pose2d(-60, -60, Math.toRadians(225));
        Pose2d specimenBar = new Pose2d(-6,-32, Math.toRadians(90));

        //Close
        if(startPos == 1) {
            myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))
                                    .forward(20)
                                    .lineToSplineHeading(basketPos)
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-35, -25, Math.toRadians(180)))
                                    .forward(10)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(basketPos)
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-45, -25, Math.toRadians(180)))
                                    .forward(10)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(basketPos)
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-55, -25, Math.toRadians(180)))
                                    .forward(10)
                                    .back(15)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(basketPos)
                                    .build()
                    );
        }
        //Far
        else if(startPos == 2) {
            myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                                    .forward(20)
                                    .lineToSplineHeading(basketPos)
                                    .lineToSplineHeading(new Pose2d(36, -25, Math.toRadians(0)))
                                    .forward(10)
                                    .lineToSplineHeading(basketPos)
                                    .lineToSplineHeading(new Pose2d(46, -25, Math.toRadians(0)))
                                    .forward(10)
                                    .lineToSplineHeading(basketPos)
                                    .lineToSplineHeading(new Pose2d(56, -25, Math.toRadians(0)))
                                    .forward(10)
                                    .lineToSplineHeading(basketPos)
                                    .build()
                    );
        }
        //Close Specimen
        else if(startPos == 3) {
            myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))
                                    .lineToSplineHeading(specimenBar)
                                    .back(15)
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-35, -25, Math.toRadians(180)))
                                    .forward(10)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(basketPos)
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-45, -25, Math.toRadians(180)))
                                    .forward(10)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(basketPos)
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-55, -25, Math.toRadians(180)))
                                    .forward(10)
                                    .back(15)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(basketPos)
                                    .build()
                    );
        }
        //Far Specimen
        else if(startPos == 4) {
            myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                                    .lineToSplineHeading(specimenBar)
                                    .lineToSplineHeading(new Pose2d(36, -25, Math.toRadians(0)))
                                    .forward(10)
                                    .lineToSplineHeading(basketPos)
                                    .lineToSplineHeading(new Pose2d(46, -25, Math.toRadians(0)))
                                    .forward(10)
                                    .lineToSplineHeading(basketPos)
                                    .lineToSplineHeading(new Pose2d(56, -25, Math.toRadians(0)))
                                    .forward(10)
                                    .lineToSplineHeading(basketPos)
                                    .build()
                    );
        }








        else {
            myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))
                                    .forward(20)
                                    .lineToSplineHeading(new Pose2d(-60, -60, Math.toRadians(225)))
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-35, -25, Math.toRadians(180)))
                                    .forward(10)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(new Pose2d(-60, -60, Math.toRadians(225)))
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-45, -25, Math.toRadians(180)))
                                    .forward(10)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(new Pose2d(-60, -60, Math.toRadians(225)))
                                    //*epic depositing happens*
                                    .lineToSplineHeading(new Pose2d(-55, -25, Math.toRadians(180)))
                                    .forward(10)
                                    .back(15)
                                    //*epic picking up happens*
                                    .lineToSplineHeading(new Pose2d(-60, -60, Math.toRadians(225)))
                                    .build()
                    );
        }


        Image img = null;
        try { img = ImageIO.read(new File("C:\\Users\\Manza\\Downloads\\field.png")); }
        catch (IOException e) {}

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();



        /*
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

         */

    }
}