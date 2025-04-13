package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Link.Classes.IntakeClass;
import org.firstinspires.ftc.teamcode.Link.Classes.PIDF_Lift;
import org.firstinspires.ftc.teamcode.Link.Classes.SmartServo;
import org.firstinspires.ftc.teamcode.Link.IntoTheAuto;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.opencv.core.Mat;

enum AutoTypes {
    SAMPLE,
    SPECIMEN
        }
enum transferringStates {
    IDLE,
    OPENING_AND_MOVING_SERVOS,
    RETRACTING_EXTENDO,
    CLOSING_CLAW,
    MAIN_TRANSFER,
    FINISH
}

@Autonomous(name = "Aquato [Pedro auto]")
public class Aquato extends OpMode {
    int waitTimer1 = -1;

    Servo claw;
    Servo outtakeLeft;
    Servo outtakeRight;

    Servo clawWrist;
    Servo slideLeft;
    Servo slideRight;

    CRServo intakeLeft;
    CRServo intakeRight;

    AutoTypes autoType;

    transferringStates transferState = transferringStates.IDLE;

    //MUY IMPORTANTE
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private PIDController controller;

    public static double p = PIDF_Lift.p, i = PIDF_Lift.i, d = PIDF_Lift.d;
    public static double f = PIDF_Lift.f;

    public static int target = 0;

    private final double ticks_in_degree = 700  / 180.0;

    private DcMotor liftLeft;
    private DcMotor liftRight;


    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;


    /* Create and Define Poses + Paths
     * Poses are built with three constructors: x, y, and heading (in Radians).
     * Pedro uses 0 - 144 for x and y, with 0, 0 being on the bottom left.
     * (For Into the Deep, this would be Blue Observation Zone (0,0) to Red Observation Zone (144,144).)
     * Even though Pedro uses a different coordinate system than RR, you can convert any roadrunner pose by adding +72 both the x and y.
     * This visualizer is very easy to use to find and create paths/pathchains/poses: <https://pedro-path-generator.vercel.app/>
     * Lets assume our robot is 18 by 18 inches
     * Lets assume the Robot is facing the human player and we want to score in the bucket */

    //SAMPLE \/

    /** Start Pose of our robot for robot*/
    private final Pose sampleStartPose = new Pose(32, 8.5, Math.toRadians(90)); //Try the ones that I don't have commented out. They might work. Otherwise, try the ones that are commented out, they get you 1 sample auto

    private final Pose scorePose = new Pose(12,/*57*/ 5, Math.toRadians(45));

    private final Pose pickup1Pose = new Pose(22, 28, Math.toRadians(90));

    private final Pose pickup2Pose = new Pose(16, 28, Math.toRadians(90));

    private final Pose pickup3Pose = new Pose(16, 28, Math.toRadians(135));

    private final Pose sampleParkPose = new Pose(36, 60, Math.toRadians(90));

    private final Pose sampleParkControlPose = new Pose(18, 50, Math.toRadians(90));

    /* These are our Paths and PathChains that we will define in buildPaths() */
    private Path scoreSamplePreload, samplePark;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    //SPECIMEN \/
    private final Pose specimenStartPose = new Pose(85, 8.5, Math.toRadians(270));
    private final Pose hangPose = new Pose(65,41.5, Math.toRadians(270));
    private final Pose behindSample1 = new Pose(113, 60, Math.toRadians(270));
    private final Pose behindSample1Control1 = new Pose(99, 0, Math.toRadians(270));
    private final Pose behindSample1Control2 = new Pose(108, 60, Math.toRadians(270));
    private final Pose humanPlayerPose = new Pose(111, 28, Math.toRadians(270));
    private final Pose behindSample2 = new Pose(123, 58, Math.toRadians(270));
    private final Pose behindSample2Control = new Pose(120, 58, Math.toRadians(270));
    private final Pose humanPlayerPose2 = new Pose(122, 21, Math.toRadians(270));
    private final Pose behindSample3 = new Pose(131, 58, Math.toRadians(270));
    private final Pose behindSample3Control = new Pose(128, 58, Math.toRadians(270));
    private final Pose humanPlayerPose3 = new Pose(131, 24, Math.toRadians(270));
    private final Pose grab2 = new Pose(118,13.5, Math.toRadians(270));
    private final Pose grab2Control = new Pose(120,18, Math.toRadians(270));
    private final Pose hangPose2 = new Pose(67,39, Math.toRadians(270));
    private final Pose hangControl = new Pose(70, 20, Math.toRadians(270));
    private final Pose grab3 = new Pose(118, 11.75, Math.toRadians(270));
    private final Pose cycleControl = new Pose(88, 20, Math.toRadians(270));
    private final Pose hangPose3 = new Pose(70,39, Math.toRadians(270));
    private final Pose grab4 = new Pose(118, 11.75, Math.toRadians(270));
    private final Pose hangControl2 = new Pose(78, 20, Math.toRadians(270));
    private final Pose hangPose4 = new Pose(72,40, Math.toRadians(270));
    private Path scoreSpecimenPreload, specimenPark;
    private PathChain moveSamples, moveBehindSample1, giveSample1, moveBehindSample2, giveSample2, moveBehindSample3, giveSample3, grab2nd, grab2ndAlt, grab3rd, hang2nd, hang3rd, grab4th, hang4th, grab5th, hang5th;
    private int pathState2;

    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
     * It is necessary to do this so that all the paths are built before the auto starts. **/
    public void buildPaths() {

        /* There are two major types of paths components: BezierCurves and BezierLines.
         *    * BezierCurves are curved, and require >= 3 points. There are the start and end points, and the control points.
         *    - Control points manipulate the curve between the start and end points.
         *    - A good visualizer for this is [this](https://pedro-path-generator.vercel.app/).
         *    * BezierLines are straight, and require 2 points. There are the start and end points.
         * Paths have can have heading interpolation: Constant, Linear, or Tangential
         *    * Linear heading interpolation:
         *    - Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path.
         *    * Constant Heading Interpolation:
         *    - Pedro will maintain one heading throughout the entire path.
         *    * Tangential Heading Interpolation:
         *    - Pedro will follows the angle of the path such that the robot is always driving forward when it follows the path.
         * PathChains hold Path(s) within it and are able to hold their end point, meaning that they will holdPoint until another path is followed.
         * Here is a explanation of the difference between Paths and PathChains <https://pedropathing.com/commonissues/pathtopathchain.html> */

        //SAMPLE \/

        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scoreSamplePreload = new Path(new BezierLine(new Point(sampleStartPose), new Point(scorePose)));
        scoreSamplePreload.setLinearHeadingInterpolation(sampleStartPose.getHeading(), scorePose.getHeading());

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        /* This is our scorePickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our park path. We are using a BezierCurve with 3 points, which is a curved line that is curved based off of the control point */
        samplePark = new Path(new BezierCurve(new Point(scorePose), /* Control Point */ new Point(sampleParkControlPose), new Point(sampleParkPose)));
        samplePark.setLinearHeadingInterpolation(scorePose.getHeading(), sampleParkPose.getHeading());

        //SPECIMEN \/
        scoreSpecimenPreload = new Path(new BezierLine(new Point(specimenStartPose), new Point(hangPose)));
        scoreSpecimenPreload.setLinearHeadingInterpolation(specimenStartPose.getHeading(), hangPose.getHeading());

        moveSamples = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(hangPose), new Point(behindSample1Control1), new Point(behindSample1Control2), new Point(behindSample1)))
                .setConstantHeadingInterpolation(behindSample1.getHeading())
                .addPath(new BezierLine(new Point(behindSample1), new Point(humanPlayerPose)))
                .setConstantHeadingInterpolation(behindSample1.getHeading())
                //.addPath(new BezierCurve(new Point(humanPlayerPose), new Point(behindSample2Control), new Point(behindSample2)))
                //.setConstantHeadingInterpolation(humanPlayerPose.getHeading())
                .addPath(new BezierLine(new Point(humanPlayerPose), new Point(behindSample2Control)))
                .setConstantHeadingInterpolation(behindSample2Control.getHeading())
                .addPath(new BezierLine(new Point(behindSample2Control), new Point(behindSample2)))
                .setConstantHeadingInterpolation(behindSample2.getHeading())
                .addPath(new BezierLine(new Point(behindSample2), new Point(humanPlayerPose2)))
                .setConstantHeadingInterpolation(humanPlayerPose2.getHeading())


                /*
                .addPath(new BezierLine(new Point(humanPlayerPose2), new Point(behindSample3Control)))
                .setConstantHeadingInterpolation(behindSample3Control.getHeading())
                .addPath(new BezierLine(new Point(behindSample3Control), new Point(behindSample3)))
                .setConstantHeadingInterpolation(behindSample3.getHeading())
                .addPath(new BezierLine(new Point(behindSample3), new Point(humanPlayerPose3)))
                .setConstantHeadingInterpolation(humanPlayerPose3.getHeading())

                 */



                //.addPath(new BezierLine(new Point(behindSample2Control), new Point(humanPlayerPose2))) specimen old
                //.addPath(new BezierLine(new Point(behindSample2), new Point(humanPlayerPose2))) specimen old
                //.setConstantHeadingInterpolation(behindSample2.getHeading()) specimen old
                //.addPath(new BezierCurve(new Point(humanPlayerPose2), new Point(behindSample3Control), new Point(behindSample3))) specimen old
                //.setConstantHeadingInterpolation(humanPlayerPose2.getHeading()) specimen old
                //.addPath(new BezierLine(new Point(behindSample3), new Point(humanPlayerPose3)))
                //.setConstantHeadingInterpolation(behindSample3.getHeading())
                //.addPath(new BezierCurve(new Point(humanPlayerPose3), new Point(grab2Control), new Point(grab2)))
                //.setLinearHeadingInterpolation(humanPlayerPose3.getHeading(), grab2.getHeading())
                .build();


        moveBehindSample1 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(hangPose), new Point(behindSample1Control1), new Point(behindSample1Control2), new Point(behindSample1)))
                .setConstantHeadingInterpolation(behindSample1.getHeading())
                .build();

        giveSample1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(behindSample1), new Point(humanPlayerPose)))
                .setConstantHeadingInterpolation(behindSample1.getHeading())
                .build();

        moveBehindSample2 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(humanPlayerPose), new Point(behindSample2Control), new Point(behindSample2)))
                .setConstantHeadingInterpolation(humanPlayerPose.getHeading())
                .build();

        giveSample2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(behindSample2), new Point(humanPlayerPose2)))
                .setConstantHeadingInterpolation(behindSample2.getHeading())
                .build();

        moveBehindSample3 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(humanPlayerPose2), new Point(behindSample3Control), new Point(behindSample3)))
                .setConstantHeadingInterpolation(humanPlayerPose2.getHeading())
                .build();

        giveSample3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(behindSample3), new Point(humanPlayerPose3)))
                .setConstantHeadingInterpolation(behindSample3.getHeading())
                .build();

        grab2nd = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(humanPlayerPose3), new Point(grab2Control), new Point(grab2)))
                .setLinearHeadingInterpolation(humanPlayerPose3.getHeading(), grab2.getHeading())
                .build();

        grab2ndAlt = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(humanPlayerPose2), new Point(grab2Control), new Point(grab2)))
                .setLinearHeadingInterpolation(humanPlayerPose2.getHeading(), grab2.getHeading())
                .build();

        hang2nd = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(grab2), new Point(hangControl), new Point(hangPose2)))
                .setLinearHeadingInterpolation(grab2.getHeading(), hangPose2.getHeading())
                .build();

        grab3rd = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(hangPose2), new Point(cycleControl), new Point(grab3)))
                .setLinearHeadingInterpolation(hangPose2.getHeading(), grab3.getHeading())
                .build();

        hang3rd = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(grab3), new Point(hangControl), new Point(hangPose3)))
                .setLinearHeadingInterpolation(grab3.getHeading(), hangPose3.getHeading())
                .build();

        grab4th = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(hangPose3), new Point(cycleControl), new Point(grab4)))
                .setLinearHeadingInterpolation(hangPose3.getHeading(), grab4.getHeading())
                .build();

        hang4th = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(grab4), new Point(hangControl2), new Point(hangPose4)))
                .setLinearHeadingInterpolation(grab4.getHeading(), hangPose4.getHeading())
                .build();
    }

    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)
     * The followPath() function sets the follower to run the specific path, but does NOT wait for it to finish before moving on. */
    public void autonomousPathUpdate() {
        //if (autoType != AutoTypes.SAMPLE || autoType != AutoTypes.SPECIMEN)
        if (autoType == AutoTypes.SAMPLE) {
            switch (pathState) {
                case 0:
                    follower.followPath(scoreSamplePreload);
                    liftsUp();
                    sampleDrivePos();

                    setPathState(1);
                    break;
                case 1:

                    /* You could check for
                    - Follower State: "if(!follower.isBusy() {}"
                    - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
                    - Robot Position: "if(follower.getPose().getX() > 36) {}"
                    */

                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Score Preload */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                        sleep(1500);
                        scoreSamplePos();
                        sleep(500);
                        openClaw();
                        sleep(300);
                        preTransferPos();
                        sleep(500);
                        intakeSample();


                        follower.followPath(grabPickup1, true);
                        setPathState(2);
                    }
                    break;
                case 2:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                    if (!follower.isBusy()) {
                        /* Grab Sample */


                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */

                        sampleIntakePos();
                        sleep(300);
                        extendSlides();
                        sleep(500);
                        sampleDrivePos();
                        sleep(2000);
                        retractSlides();
                        sleep(5000);
                        follower.followPath(scorePickup1, true);
                        setPathState(3);
                    }
                    break;
                case 3:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Score Sample */
                        transfer();
                        sleep(300);
                        scoreSamplePos();

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                        sleep(500);
                        follower.followPath(grabPickup2, true);
                        setPathState(4);
                    }
                    break;
                case 4:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup2Pose's position */
                    if (!follower.isBusy()) {
                        /* Grab Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                        follower.followPath(scorePickup2, true);
                        sleep(500);
                        setPathState(5);
                    }
                    break;
                case 5:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Score Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                        follower.followPath(grabPickup3, true);
                        sleep(500);
                        setPathState(6);
                    }
                    break;
                case 6:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                    if (!follower.isBusy()) {
                        /* Grab Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                        follower.followPath(scorePickup3, true);
                        sleep(500);
                        setPathState(7);
                    }
                    break;
                case 7:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Score Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are parked */

                        retractSlides();
                        follower.followPath(samplePark, true);
                        sleep(500);
                        setPathState(8);
                    }
                    break;
                case 8:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Level 1 Ascent */
                        sleep(500);

                        /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                        setPathState(-1);
                    }
                    break;
            }
        }
        if (autoType == AutoTypes.SPECIMEN) {
            switch (pathState2) {
                case 0:
                    scoreSpecimenPos();
                    follower.followPath(scoreSpecimenPreload);

                    setPathState2(1);
                    break;
                case 1:
                    if (!follower.isBusy()) {
                        sleep(300);
                        openClaw();
                        sleep(100);
                        follower.followPath(moveSamples, true); //behind sample
                        sleep(400);
                        drivePos();
                        waitTimer1 = 0;
                        setPathState2(2);
                    }
                    break;
                case 2:
                    if (!follower.isBusy()) {
                        follower.followPath(grab2ndAlt, true);
                        setPathState2(3);
                    }
                    break;
                case 3:
                    if (!follower.isBusy()) {
                        intakeSpecimenPos();
                        sleep(600);
                        closeClaw();
                        sleep(200);
                        scoreSpecimenPos();
                        sleep(200);
                        follower.followPath(hang2nd, true);
                        setPathState2(4);
                    }
                    break;
                case 4:
                    if (!follower.isBusy()) {
                        sleep(300);
                        openClaw();
                        sleep(200);
                        drivePos();
                        sleep(200);
                        //follower.followPath(moveBehindSample1, true);
                        waitTimer1 = 0;
                        setPathState2(5);
                    }
                case 5:
                    if (!follower.isBusy()) {
                        follower.followPath(grab3rd, true);
                        setPathState2(6);
                    }
                case 6:
                    if (!follower.isBusy()) {
                        intakeSpecimenPos();
                        sleep(600);
                        closeClaw();
                        sleep(200);
                        scoreSpecimenPos();
                        sleep(200);
                        follower.followPath(hang3rd, true);
                        setPathState2(7);
                    }
                case 7:
                    if (!follower.isBusy()) {
                        sleep(300);
                        openClaw();
                        sleep(600);
                        drivePos();
                        follower.followPath(grab4th, true); //behind sample
                        setPathState2(8);
                    }
                case 8:
                    if (!follower.isBusy()) {
                        intakeSpecimenPos();
                        sleep(500);
                        closeClaw();
                        sleep(200);
                        scoreSpecimenPos();
                        sleep(300);
                        follower.followPath(hang4th, true);
                        setPathState2(9);
                    }
            }
        }
    }

    /** These change the states of the paths and actions
     * It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void setPathState2(int pState) {
        pathState2 = pState;
        pathTimer.resetTimer();
    }

    public void setAutoType(String autoTypeToSetTo) {
        if (autoTypeToSetTo.equals("specimen") || autoTypeToSetTo.equals("Specimen") || autoTypeToSetTo.equals("SPECIMEN")) {
            autoType = AutoTypes.SPECIMEN;
        }
        if (autoTypeToSetTo.equals("sample") || autoTypeToSetTo.equals("Sample") || autoTypeToSetTo.equals("SAMPLE")) {
            autoType = AutoTypes.SAMPLE;
        }
    }
    private void chooseAutoType() {
        String chosenAuto = "";

        if (gamepad1.x) {
            //setAutoType("sample");
            chosenAuto = "sample";
            autoType = AutoTypes.SAMPLE;
        }
        if (gamepad1.b) {
            //setAutoType("specimen");
            chosenAuto = "specimen";
            autoType = AutoTypes.SPECIMEN;
        }
        telemetry.addData("SAMPLE = [X]", "");
        telemetry.addData("SPECIMEN = [B]", "");
        telemetry.addData("Chosen auto = ",chosenAuto);
        telemetry.update();
    }

    private void initHardware() {
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        claw = hardwareMap.get(Servo.class, "claw");
        clawWrist = hardwareMap.get(Servo.class, "clawWrist");
        slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        slideRight = hardwareMap.get(Servo.class, "slideRight");
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeRight.setDirection(DcMotorSimple.Direction.FORWARD);

        slideLeft.setDirection(Servo.Direction.REVERSE);
        slideRight.setDirection(Servo.Direction.REVERSE);

        claw.setDirection(Servo.Direction.REVERSE);
    }


    private void intakeSpecimenPos() {
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.0);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.0);
        SmartServo.setSmartPos(hardwareMap, "clawWrist", 0.35);
        SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.03);
        //SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        //SmartServo.setSmartPos(hardwareMap, "slideRight", 0.0);
        target = 3;
    }

    private void drivePos() {
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.01); //used to be 0.3
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.01);
        SmartServo.setSmartPos(hardwareMap, "clawWrist", 0.34);
        SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.03);
        //SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        //SmartServo.setSmartPos(hardwareMap, "slideRight", 0.0);
        target = 3;
    }

    private void scoreSpecimenPos() {
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.85);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.85);
        SmartServo.setSmartPos(hardwareMap, "clawWrist", 0.55);
        //SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        //SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        target = 450;
    }
    private void preTransferPos() {
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.08);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.08);
        SmartServo.setSmartPos(hardwareMap, "clawWrist", 0.3);
        target = 3;
    }

    private void sampleIntakePos() {
        SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.89);
        intakeLeft.setPower(-1);
        intakeRight.setPower(-1);
    }

    private void extendSlides() {
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.15);
        SmartServo.setSmartPos(hardwareMap, "slideRight", 0.15);
    }

    private void retractSlides() {
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0);
        SmartServo.setSmartPos(hardwareMap, "slideRight", 0);
    }
    private void closeClaw() {
        claw.setPosition(0.6);
    }
    private void openClaw() {
        claw.setPosition(0.92);
    }

    private void scoreSamplePos() {
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.7);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.7);
        SmartServo.setSmartPos(hardwareMap, "clawWrist", 0.55);
    }

    private void liftsUp() {
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        target = 870;
    }

    private void transfer() {

        openClaw();
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.09);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.09);
        SmartServo.setSmartPos(hardwareMap,"wristLeft", 0.35);
        SmartServo.setSmartPos(hardwareMap, "clawWrist", 0.3);
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.05);
        SmartServo.setSmartPos(hardwareMap, "slideRight", 0.05);

        sleep(300);


        SmartServo.setSmartPos(hardwareMap,"slideLeft", 0);
        SmartServo.setSmartPos(hardwareMap,"slideRight", 0);

        sleep(250);

        closeClaw();

        sleep(350);

        intakeLeft.setPower(1);
        intakeRight.setPower(1);
        sleep(500);
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.7);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.7);

        sleep(200);

        intakeLeft.setPower(0);
        intakeRight.setPower(0);


    }

    private void sampleDrivePos() {
        SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.57);
    }
    private void intakeSample() {
        intakeLeft.setPower(1);
        intakeRight.setPower(1);
    }
    private void stopIntake() {
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
    }




    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        controller.setPID(p, i, d);
        int liftPos = liftLeft.getCurrentPosition();
        double pid = controller.calculate(liftPos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = pid + ff;



        if (power < -0.25) {
            power = -0.25;
        }



        liftLeft.setPower(power);
        liftRight.setPower(power);

        if (waitTimer1 >= 0) {
            waitTimer1 = waitTimer1 + 1;
            if (waitTimer1 >= 1000) {
                //intakeSpecimenPos();
                waitTimer1 = -1;
            }
        }









        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {


        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        liftLeft = hardwareMap.get(DcMotor.class, "leftLift");
        liftRight = hardwareMap.get(DcMotor.class, "rightLift");

        target = 5;

        //autoType = AutoTypes.SPECIMEN;
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        initHardware();
        buildPaths();

        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.03); //used to be 0.3
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.03);
        SmartServo.setSmartPos(hardwareMap, "clawWrist", 0.54);
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);

        sleep(300);

        closeClaw();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {
        chooseAutoType();
    }

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        //scoreSpecimenPos();
        setPathState(0);
        setPathState2(0);
        SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
        SmartServo.setSmartPos(hardwareMap, "slideRight", 0.0);
        SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.3);

        if (autoType == AutoTypes.SAMPLE) {
            follower.setStartingPose(sampleStartPose);
        }
        if (autoType == AutoTypes.SPECIMEN) {
            follower.setStartingPose(specimenStartPose);
        }
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}

