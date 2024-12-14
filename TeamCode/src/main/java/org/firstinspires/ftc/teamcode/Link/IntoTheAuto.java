package org.firstinspires.ftc.teamcode.Link;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

enum InitStep {
    START,
    SIDE,
    TYPE,
    DONE
}


@Config
@Autonomous(name = "Into the Auto", group = "16265-Auto")
public final class IntoTheAuto extends LinearOpMode {
    private void function() {

    }
    @Override
    public void runOpMode() throws InterruptedException {

        int Auto = 1;
        String autoStart = new String("null");
        String autoSide = new String("null");
        String autoType = new String("null");
        boolean canProceed = false;


        Pose2d leftBasket = new Pose2d(-52, -52, 315);

        Pose2d beginPose = new Pose2d(0, 0, Math.PI / 2);

        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {

            InitStep initState = InitStep.START;


            telemetry.addData("Initializing ENIGMA Autonomous: IntoTheAuto", "");
            telemetry.addData("---------------------------------------","");
            telemetry.addData("Select Staring Side using X and B:","");
            telemetry.addData("    Left   ", "(X)");
            telemetry.addData("    Right ", "(B)");

            while(!isStopRequested()) {
                if (initState == InitStep.START) {
                    if (gamepad1.x == true) {
                        autoStart = "left";
                        beginPose = new Pose2d(-35, -61.5, Math.PI / 2);
                        canProceed = true;
                    }
                    if (gamepad1.b == true) {
                        autoStart = "right";
                        beginPose = new Pose2d(35, -61.5, Math.toRadians(90));
                        canProceed = true;
                        //Set Starting Pos
                    }
                }

                if (gamepad1.x == false && gamepad1.b == false && initState == InitStep.START && canProceed == true) {
                    canProceed = false;
                    initState = InitStep.SIDE;
                    telemetry.clearAll();
                    telemetry.addData("Initializing ENIGMA Autonomous: IntoTheAuto", "");
                    telemetry.addData("---------------------------------------","");
                    telemetry.addData("Starting side: ", autoStart.toString(), "\n");
                    telemetry.addData("Select Grabbing Side using X and B:","");
                    telemetry.addData("    Left   ", "(X)");
                    telemetry.addData("    Right ", "(B)");
                }

                if (initState == InitStep.SIDE) {
                    if (gamepad1.x == true) {
                        canProceed = true;
                        autoSide = "left";
                    }
                    if (gamepad1.b == true) {
                        canProceed = true;
                        autoSide = "right";
                    }
                }

                if (gamepad1.x == false && gamepad1.b == false && initState == InitStep.SIDE && canProceed == true) {
                    canProceed = false;
                    initState = InitStep.TYPE;
                    telemetry.clearAll();
                    telemetry.addData("Initializing ENIGMA Autonomous: IntoTheAuto", "");
                    telemetry.addData("---------------------------------------","");
                    telemetry.addData("Select Initialized Type using Y and A:","");
                    telemetry.addData("    Specimen   ", "(Y)");
                    telemetry.addData("    Sample ", "(A)");
                }

                if (initState == InitStep.TYPE) {
                    if (gamepad1.y == true) {
                        autoType = "specimen";
                        canProceed = true;
                    }
                    if (gamepad1.a == true) {
                        autoType = "sample";
                        canProceed = true;

                    }
                }

                if (gamepad1.y == false && gamepad1.a == false && initState == InitStep.TYPE && canProceed == true) {
                    telemetry.clearAll();
                    telemetry.addData("Initialized ENIGMA Autonomous: IntoTheAuto", "");
                    telemetry.addData("---------------------------------------","");
                    telemetry.addData("Good luck and have fun. GP!","");
                    telemetry.addData("                 .77:                   \n" +
                            "             .^JPP?75PJ~.               \n" +
                            "          :75PY~.     ~7:  .            \n" +
                            "      .~YP57:   :?PPJ^    ^5G5!.        \n" +
                            "     .BG^   .!YPY!..!YP57:   ^GB^       \n" +
                            "     :BP  !PP?:        :7PG7  JB^       \n" +
                            "     .PY  5B:            .BG  JB^       \n" +
                            "          YB:       .!Y555G5  JB^       \n" +
                            "          YB:                 JB^       \n" +
                            "          YB7.          .^.   YB^       \n" +
                            "          .^JPP7:    :75PY^   5B~       \n" +
                            "              :75PYYG57:  .~YPY!.       \n" +
                            "                 .~^   :?5PJ^.          \n" +
                            "                   .!YPY!.              \n" +
                            "                 7PP7:                  \n" +
                            "                 YB.                    \n" +
                            "                  .                     \n" +
                            "                 YJ                     \n" +
                            "                 .~                     ", "");
                    initState = InitStep.DONE;
                }

                telemetry.update();

                if (initState == InitStep.DONE) {
                    break;
                }
                sleep(1);
            }

            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
            waitForStart();

            //Left
            if (autoSide == "left" && autoType == "sample") {
                telemetry.addData("BBEANSBEANSBEANSBENASEBANEBANSBEANSBEANSBEANSBEANS","");
                telemetry.update();
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
            //Left Specimen
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

            //Grab Right
            if (autoSide == "right" && autoType == "sample") {
                Actions.runBlocking(
                        drive.actionBuilder(beginPose)
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

            //Grab Right Specimen
            if (autoSide == "right" && autoType == "specimen") {
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
            Actions.runBlocking(new SequentialAction(
                    drive.actionBuilder(beginPose).lineToX(1).build()

            ));






        } else {
            throw new RuntimeException();
        }
    }
}
