package org.firstinspires.ftc.teamcode.Link.Autos;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Link.SmartServo;
import org.firstinspires.ftc.teamcode.StuffWeMayUseSomeday.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

enum InitStep {
    START,
    DONE
}


@Config
@Autonomous(name = "Into the Auto", group = "16265-Auto")
public final class IntoTheAuto extends LinearOpMode {
    public class Arm {
        private Servo outtakeLeft;
        private Servo outtakeRight;
        private Servo wristRight;
        private Servo wristLeft;

        public Arm(HardwareMap hardwareMap) {
            outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
            outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
            wristRight = hardwareMap.get(Servo.class, "wristRight");
            wristLeft = hardwareMap.get(Servo.class, "wristLeft");
        }

        public class SpecimenIntakePos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 1);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 1);
                return false;
            }
        }
        public Action armSpecimenIntake() {
            return new SpecimenIntakePos();
        }

        public class SpecimenOuttakePos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.2);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.2);
                SmartServo.setSmartPos(hardwareMap, "wristRight", 0.75);
                SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.75);
                return false;
            }
        }
        public Action specimenOuttakePos() {
            return new SpecimenOuttakePos();
        }
    }


    public class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
            claw.setDirection(Servo.Direction.REVERSE);
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                SmartServo.setSmartPos(hardwareMap, "claw", 0.35);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                SmartServo.setSmartPos(hardwareMap, "claw", 0.75);
                telemetry.addData("BEANS", 0);
                telemetry.update();
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }
    }


    public class Lift {
        private DcMotor leftLift;
        private DcMotor rightLift;

        public Lift(HardwareMap hardwareMap) {
            leftLift = hardwareMap.get(DcMotor.class, "leftLift");
            rightLift = hardwareMap.get(DcMotor.class, "rightLift");
            leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        public class SetLiftIdle implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightLift.setPower(0);
                leftLift.setPower(0);
                return false;
            }
        }
        public Action liftIdle() {
            return new SetLiftIdle();
        }

        public class LiftUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightLift.setPower(1);
                leftLift.setPower(1);
                return false;
            }
        }
        public Action liftUp() {
            return new LiftUp();
        }

        public class LiftDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftLift.setPower(-0.32);
                rightLift.setPower(-0.32);
                return false;
            }
        }
        public Action liftDown() {
            return new LiftDown();
        }
    }






    private void function() {

    }
    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = new Lift(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        //DO NOT MOVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! RAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHH

        String autoStart = new String("null");
        String autoSide = new String("null");
        String autoType = new String("null");
        boolean canProceed = false;

        Pose2d beginPose = new Pose2d(12,-62, Math.toRadians(90));


        Pose2d leftBasket = new Pose2d(-60,-60, Math.toRadians(90));
        Pose2d specimenPose = new Pose2d(0, -31, Math.toRadians(90));
        Pose2d specimenPoseLeft = new Pose2d(-4, -31, Math.toRadians(90));
        Pose2d specimenPoseRight = new Pose2d(1.25, -29.75, Math.toRadians(90));
        Pose2d humanPlayerPose = new Pose2d(48, -50, Math.toRadians(90));

        Pose2d yoinkPose = new Pose2d(48, -52.75, Math.toRadians(90));
        Pose2d yoinkPose2 = new Pose2d(48, -53.5, Math.toRadians(90));

        Pose2d transitionPose = new Pose2d(48, -38, Math.toRadians(90));
        Pose2d coloredSample1 = new Pose2d(51, -8, Math.toRadians(90));
        //Pose2d coloredSample1 = new Pose2d(48, -8, Math.toRadians(90));
        Pose2d coloredSample2 = new Pose2d(54, -8, Math.toRadians(90));
        Pose2d coloredSample3 = new Pose2d(72, -8, Math.toRadians(90));

        Pose2d cycle2Actor = new Pose2d(48, -40, Math.toRadians(90));

        Servo slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        Servo slideRight = hardwareMap.get(Servo.class, "slideRight");
        Servo wristLeft = hardwareMap.get(Servo.class,"wristLeft");
        Servo wristRight = hardwareMap.get(Servo.class,"wristLeft");
        Servo outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        Servo outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        Servo clawServo = hardwareMap.get(Servo.class, "claw");

        slideLeft.setDirection(Servo.Direction.REVERSE);
        slideRight.setDirection(Servo.Direction.REVERSE);
        clawServo.setDirection(Servo.Direction.REVERSE);

        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {

            InitStep initState = InitStep.START;


            telemetry.addData("Initializing ENIGMA Autonomous: IntoTheAuto", "");
            telemetry.addData("---------------------------------------","");
            telemetry.addData("Select Mode using X and B:","");
            telemetry.addData("    Sample   ", "(X)");
            telemetry.addData("    Specimen ", "(B)");

            while(!isStopRequested()) {
                if (initState == InitStep.START) {
                    if (gamepad1.x == true) {
                        beginPose = new Pose2d(-35, -61.5, Math.PI / 2);
                        autoType = "sample";
                        canProceed = true;
                    }
                    if (gamepad1.b == true) {
                        beginPose = new Pose2d(14,-62, Math.toRadians(90));
                        autoType = "porting";
                        canProceed = true;
                        //Set Starting Pos
                    }
                }


                if (gamepad1.b == false && gamepad1.x == false && initState == InitStep.START && canProceed == true) {
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






            SmartServo.setSmartPos(hardwareMap, "slideRight", 0.0);
            SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
            SmartServo.setSmartPos(hardwareMap,"wristLeft", 0.52);
            SmartServo.setSmartPos(hardwareMap,"wristRight", 0.52);
            SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.1678);
            SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.1678);

            sleep(3500);

            clawServo.setPosition(0.35);

            waitForStart();

            //sample
            if (autoType.equals("sample")) {
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
            TrajectoryActionBuilder score1 = drive.actionBuilder(beginPose)
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));

            TrajectoryActionBuilder moveSamples = drive.actionBuilder(specimenPose)
                    .lineToY(-44)
                    .splineToLinearHeading(transitionPose, Math.toRadians(90))
                    /*
                    .splineToLinearHeading(coloredSample1, Math.toRadians(270))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))

                     */
                    .splineToLinearHeading(coloredSample2, Math.toRadians(270))
                    .splineToLinearHeading(cycle2Actor, Math.toRadians(90))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180));
                    /*
                    .splineToLinearHeading(coloredSample3, Math.toRadians(270))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    TODO: add if we somehow optimize to a 5 spec
             */



            TrajectoryActionBuilder specimenFromHumanPlayerAction = drive.actionBuilder(yoinkPose)
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));

            TrajectoryActionBuilder specimenFromHumanPlayerLeftAction = drive.actionBuilder(yoinkPose)
                    .splineToLinearHeading(specimenPoseLeft, Math.toRadians(90));

            TrajectoryActionBuilder specimenFromHumanPlayerRightAction = drive.actionBuilder(yoinkPose2)
                    .splineToLinearHeading(specimenPoseRight, Math.toRadians(90));

            TrajectoryActionBuilder humanPlayerFromSpecimenAction = drive.actionBuilder(specimenPose)
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(90));
                    //.splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    //.splineToLinearHeading(specimenPose, Math.toRadians(90));
            /*
            TrajectoryActionBuilder score4 = drive.actionBuilder(specimenPose)
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));
            TrajectoryActionBuilder score5 = drive.actionBuilder(specimenPose)
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));

             */
            TrajectoryActionBuilder yoink = drive.actionBuilder(humanPlayerPose).
                    splineToLinearHeading(yoinkPose, Math.toRadians(90));
            TrajectoryActionBuilder yoink3rd = drive.actionBuilder(humanPlayerPose).
                    splineToLinearHeading(yoinkPose2, Math.toRadians(90));

            SleepAction sleepAction = new SleepAction(400);

            Action score1built = score1.build();
            Action moveSamplesBuilt = moveSamples.build();

            Action specimenFromHumanPlayer = specimenFromHumanPlayerAction.build();
            Action humanPlayerFromSpecimen = humanPlayerFromSpecimenAction.build();
            Action specimenFromHumanPlayerLeft = specimenFromHumanPlayerLeftAction.build();
            Action specimenFromHumanPlayerRight = specimenFromHumanPlayerRightAction.build();
            Action yoink3rdBuilt = yoink3rd.build();

            /*
            Action score2built = score2.build();
            Action score3built = score3.build();
            Action score4built = score4.build();
            Action score5built = score5.build();

             */
            Action yoinkBuilt = yoink.build();

            if (autoType.equals("porting")) { //This is the one
                telemetry.addData("yeehaw", 0);
                telemetry.update();
                Actions.runBlocking(
                        new SequentialAction(
                                //TRACK lift up for a wee bit in the beginning to not clip bar, while driving
                                new ParallelAction(
                                        new SequentialAction(
                                            score1built,
                                            new SleepAction(1)
                                        ),
                                        new SequentialAction(
                                                lift.liftUp(),
                                                new SleepAction(.2),
                                                lift.liftIdle()
                                        )
                                ),

                                //TRACK Score preload
                                lift.liftUp(),
                                new SleepAction(0.7),
                                lift.liftIdle(),
                                claw.openClaw(),
                                new SleepAction(0.4),


                                //TRACK Go move samples to the human player zone while putting lifts down
                                new ParallelAction(
                                    moveSamplesBuilt,
                                    //Going to move samples
                                    new SequentialAction(
                                        lift.liftDown(),
                                        new SleepAction(2.75),
                                        lift.liftIdle()
                                    )
                                ),
                                new SleepAction(0.4),

                                //TRACK Picks up specimen #2
                                arm.armSpecimenIntake(),
                                new SleepAction(0.7),
                                yoinkBuilt,
                                new SleepAction(0.2),
                                claw.closeClaw(),
                                new SleepAction(1),
                                arm.specimenOuttakePos(),
                                new SleepAction(1.5),

                                //TRACK goes to specimen bar and scores 2
                                specimenFromHumanPlayerLeft,
                                new SleepAction(0.3),
                                lift.liftUp(),
                                new SleepAction(0.6),
                                lift.liftIdle(),
                                claw.openClaw(),
                                new SleepAction(0.2),

                                //TRACK drives to human player while putting lifts down
                                new ParallelAction(
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(3),
                                                lift.liftIdle()
                                        ),
                                        humanPlayerFromSpecimen
                                ),
                                new SleepAction(0.2),

                                //TRACK picks up specimen #3
                                arm.armSpecimenIntake(),
                                new SleepAction(0.7),
                                yoink3rdBuilt,
                                new SleepAction(0.4),
                                claw.closeClaw(),
                                new SleepAction(1),
                                arm.specimenOuttakePos(),
                                new SleepAction(1),
                                specimenFromHumanPlayerRight,
                                lift.liftUp(),
                                new SleepAction(0.7),
                                lift.liftIdle(),
                                claw.openClaw(),
                                new SleepAction(0.2),

                                //track puts lift down
                                lift.liftDown(),
                                new SleepAction(3),
                                lift.liftIdle()







                                /*


                                //TRACK goes and picks up specimen
                                yoinkBuilt,
                                new SleepAction(0.2),
                                claw.closeClaw(),
                                new SleepAction(1),
                                arm.specimenOuttakePos(),
                                new SleepAction(1.5),

                                //TRACK goes to specimen bar and scores
                                specimenFromHumanPlayer,
                                new SleepAction(0.3),
                                lift.liftUp(),
                                new SleepAction(0.7),
                                lift.liftIdle(),
                                claw.openClaw(),
                                new SleepAction(0.2),

                                //TRACK drives to human player while putting lifts down
                                new ParallelAction(
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(3),
                                                lift.liftIdle()
                                        ),
                                        humanPlayerFromSpecimen
                                ),
                                new SleepAction(0.2)//,

                                /*

                                //TRACK picks up specimen
                                arm.armSpecimenIntake(),
                                new SleepAction(0.2),
                                yoinkBuilt,
                                new SleepAction(0.2),
                                claw.closeClaw(),
                                new SleepAction(1),
                                arm.specimenOuttakePos(),
                                new SleepAction(1.5),


                                //TRACK goes to specimen pose and scores
                                specimenFromHumanPlayer,
                                lift.liftUp(),
                                new SleepAction(0.7),
                                lift.liftIdle(),
                                claw.openClaw(),

                                //TRACK lowers lift
                                new SleepAction(0.2),
                                new ParallelAction(
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(3.0),
                                                lift.liftIdle()
                                        )
                                )

                                 */

                        )
                );
            }

            if (autoType.equals("specimen1")) {
                telemetry.addData("can we change this to know where we're at", 0);
                telemetry.update();
                Actions.runBlocking(
                        drive.actionBuilder(beginPose)
                                .setTangent(90)
                                .splineToLinearHeading(specimenPose, Math.toRadians(90))
                                .build());
            }

                /*
                Actions.runBlocking(
                        drive.actionBuilder(beginPose)
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

                 */

            /*
            Actions.runBlocking(new SequentialAction(

                    drive.actionBuilder(beginPose).lineToX(1).build()

            ));

             */


        } else {
            throw new RuntimeException();
        }
    }
    }
