package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import static android.os.SystemClock.sleep;

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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Link.IntoTheAuto;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

enum AutoTypes {
    SAMPLE,
    SPECIMEN
        }

@Autonomous(name = "Aquato [Pedro auto]")
public class Aquato extends OpMode {

    Servo claw;
    Servo outtakeLeft;
    Servo outtakeRight;

    AutoTypes autoType;

    //MUY IMPORTANTE
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

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
    private final Pose sampleStartPose = new Pose(33, 8.5, Math.toRadians(0));

    private final Pose scorePose = new Pose(18, /*57*/ 18, Math.toRadians(45));


    private final Pose pickup1Pose = new Pose(24, 28, Math.toRadians(90));

    private final Pose pickup2Pose = new Pose(13, 28, Math.toRadians(90));

    private final Pose pickup3Pose = new Pose(13, 28, Math.toRadians(135));

    private final Pose sampleParkPose = new Pose(46, 60, Math.toRadians(90));

    private final Pose sampleParkControlPose = new Pose(28, 52, Math.toRadians(90));

    /* These are our Paths and PathChains that we will define in buildPaths() */
    private Path scoreSamplePreload, samplePark;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    //SPECIMEN \/
    private final Pose specimenStartPose = new Pose(85, 8.5, Math.toRadians(270));
    private final Pose hangPose = new Pose(72,35, Math.toRadians(270));
    private final Pose behindSample1 = new Pose(111, 60, Math.toRadians(270));
    private final Pose behindSample1Control1 = new Pose(98, 0, Math.toRadians(270));
    private final Pose behindSample1Control2 = new Pose(108, 60, Math.toRadians(270));
    private final Pose humanPlayerPose = new Pose(111, 16, Math.toRadians(270));
    private final Pose behindSample2 = new Pose(120, 60, Math.toRadians(270));
    private final Pose behindSample2Control = new Pose(115, 66, Math.toRadians(270));
    private final Pose humanPlayerPose2 = new Pose(120, 16, Math.toRadians(270));
    private final Pose behindSample3 = new Pose(128, 60, Math.toRadians(270));
    private final Pose behindSample3Control = new Pose(113, 66, Math.toRadians(270));
    private final Pose humanPlayerPose3 = new Pose(129, 16, Math.toRadians(270));
    private final Pose grab2 = new Pose(118,11.243, Math.toRadians(270));
    private final Pose grab2Control = new Pose(120,20, Math.toRadians(270));
    private final Pose hangPose2 = new Pose(74,35, Math.toRadians(270));
    private final Pose hangControl = new Pose(72, 20, Math.toRadians(270));
    private final Pose grab3 = new Pose(118, 11.25, Math.toRadians(270));
    private Path scoreSpecimenPreload, specimenPark;
    private PathChain moveBehindSample1, giveSample1, moveBehindSample2, giveSample2, moveBehindSample3, giveSample3, grab2nd, grab3rd, hang2nd, hang3rd, grab4th, hang4th, grab5th, hang5th;
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

        moveBehindSample1 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(hangPose), new Point(behindSample1Control1), new Point(behindSample1Control2), new Point(behindSample1)))
                .setLinearHeadingInterpolation(hangPose.getHeading(), behindSample1.getHeading())
                .build();

        giveSample1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(behindSample1), new Point(humanPlayerPose)))
                .setLinearHeadingInterpolation(behindSample1.getHeading(), humanPlayerPose.getHeading())
                .build();

        moveBehindSample2 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(humanPlayerPose), new Point(behindSample2Control), new Point(behindSample2)))
                .setLinearHeadingInterpolation(humanPlayerPose.getHeading(), behindSample2.getHeading())
                .build();

        giveSample2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(behindSample2), new Point(humanPlayerPose2)))
                .setLinearHeadingInterpolation(behindSample2.getHeading(), humanPlayerPose2.getHeading())
                .build();

        moveBehindSample3 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(humanPlayerPose2), new Point(behindSample3Control), new Point(behindSample3)))
                .setLinearHeadingInterpolation(humanPlayerPose2.getHeading(), behindSample3.getHeading())
                .build();

        giveSample3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(behindSample3), new Point(humanPlayerPose3)))
                .setLinearHeadingInterpolation(behindSample3.getHeading(), humanPlayerPose3.getHeading())
                .build();

        grab2nd = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(humanPlayerPose3), new Point(grab2Control), new Point(grab2)))
                .setLinearHeadingInterpolation(humanPlayerPose3.getHeading(), grab2.getHeading())
                .build();

        hang2nd = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(grab2), new Point(hangControl), new Point(hangPose2)))
                .setLinearHeadingInterpolation(grab2.getHeading(), hangPose2.getHeading())
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
                        follower.followPath(grabPickup1, true);
                        setPathState(2);
                    }
                    break;
                case 2:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                    if (!follower.isBusy()) {
                        /* Grab Sample */


                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                        follower.followPath(scorePickup1, true);
                        setPathState(3);
                    }
                    break;
                case 3:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Score Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
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
                        setPathState(5);
                    }
                    break;
                case 5:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Score Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                        follower.followPath(grabPickup3, true);
                        setPathState(6);
                    }
                    break;
                case 6:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                    if (!follower.isBusy()) {
                        /* Grab Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                        follower.followPath(scorePickup3, true);
                        setPathState(7);
                    }
                    break;
                case 7:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Score Sample */

                        /* Since this is a pathChain, we can have Pedro hold the end point while we are parked */
                        follower.followPath(samplePark, true);
                        setPathState(8);
                    }
                    break;
                case 8:
                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                    if (!follower.isBusy()) {
                        /* Level 1 Ascent */

                        /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                        setPathState(-1);
                    }
                    break;
            }
        }
        if (autoType == AutoTypes.SAMPLE || 1==1) {
            switch (pathState2) {
                case 0:
                    follower.followPath(scoreSpecimenPreload);

                    setPathState2(1);
                    break;
                case 1:
                    if (!follower.isBusy()) {
                        sleep(500);
                        scoreSpecimenPos();
                        sleep(200);
                        openClaw();
                        sleep(50);
                        follower.followPath(moveBehindSample1, true);
                        setPathState2(2);
                    }
                    break;
                case 2:
                    if (!follower.isBusy()) {
                        follower.followPath(giveSample1, true);
                        intakeSpecimenPos();
                        setPathState2(3);
                    }
                    break;
                case 3:
                    if (!follower.isBusy()) {
                        follower.followPath(moveBehindSample2, true);
                        setPathState2(4);
                    }
                    break;
                case 4:
                    if (!follower.isBusy()) {
                        follower.followPath(giveSample2, true);
                        setPathState2(5);
                    }
                    break;
                case 5:
                    if (!follower.isBusy()) {
                        follower.followPath(moveBehindSample3, true);
                        setPathState2(6);
                    }
                    break;
                case 6:
                    if (!follower.isBusy()) {
                        follower.followPath(giveSample3, true);
                        setPathState2(7);
                    }
                    break;
                case 7:
                    if (!follower.isBusy()) {
                        follower.followPath(grab2nd, true);
                        intakeSpecimenPos();
                        setPathState2(8);
                    }
                    break;
                case 8:
                    if (!follower.isBusy()) {
                        intakeSpecimenPos();
                        sleep(1000);
                        closeClaw();
                        sleep(100);
                        outtakeSpecimenPos();
                        sleep(200);
                        follower.followPath(hang2nd, true);
                        setPathState2(9);
                    }
                    break;
                case 9:
                    if (!follower.isBusy()) {
                        sleep(1000);
                        scoreSpecimenPos();
                        sleep(200);
                        openClaw();
                        sleep(50);
                        setPathState2(10);
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
    private void initHardware() {
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        claw = hardwareMap.get(Servo.class, "claw");

        claw.setDirection(Servo.Direction.REVERSE);
    }


    private void intakeSpecimenPos() {
        outtakeRight.setPosition(0.06);
        outtakeLeft.setPosition(0.06);
    }
    private void outtakeSpecimenPos() {
        outtakeRight.setPosition(0.73);
        outtakeLeft.setPosition(0.73);
    }

    private void scoreSpecimenPos() {
        outtakeRight.setPosition(0.5);
        outtakeLeft.setPosition(0.5);
    }
    private void closeClaw() {
        claw.setPosition(0);
    }
    private void openClaw() {
        claw.setPosition(0.3);
    }




    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

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
        autoType = AutoTypes.SPECIMEN;
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(specimenStartPose);
        initHardware();
        buildPaths();

        closeClaw();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        outtakeSpecimenPos();
        setPathState(0);
        setPathState2(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}

