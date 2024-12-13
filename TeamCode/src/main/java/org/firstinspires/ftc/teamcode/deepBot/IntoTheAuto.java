package org.firstinspires.ftc.teamcode.deepBot;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

public final class IntoTheAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        /*
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {


            waitForStart();

            Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(30, 30), Math.PI / 2)
                        .splineTo(new Vector2d(0, 60), Math.PI)
                        .build());
        }

         */

        String autoSide = "left";

        String autoType = "sample";

        Pose2d beginPose = new Pose2d(-35,-62, Math.toRadians(90));

        Pose2d leftBasket = new Pose2d(-60,-60, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);



        if (autoSide == "left" && autoType == "sample") {
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
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
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
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
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
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
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
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


        else {
            throw new RuntimeException();
        }
    }
}
