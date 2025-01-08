package org.firstinspires.ftc.teamcode.Link;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Link.Classes.SmartServo;
import org.firstinspires.ftc.teamcode.MecanumDrive;
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
                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.95);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.95);
                return false;
            }
        }
        public Action armSpecimenIntake() {
            return new SpecimenIntakePos();
        }

        public class SpecimenOuttakePos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.1678);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.1678);
                SmartServo.setSmartPos(hardwareMap, "wristRight", 0.75);
                SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.75);
                return false;
            }
        }
        public Action specimenOuttakePos() {
            return new SpecimenOuttakePos();
        }
    }
    Arm arm = new Arm(hardwareMap);

    public class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
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
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }
    }
    Claw claw = new Claw(hardwareMap);

    public class Lift {
        private DcMotor leftLift;
        private DcMotor rightLift;

        public Lift(HardwareMap hardwareMap) {
            leftLift = hardwareMap.get(DcMotor.class, "leftLift");
            rightLift = hardwareMap.get(DcMotor.class, "rightLift");
            //TODO reverse :3
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
                leftLift.setPower(-0.2);
                rightLift.setPower(-0.2);
                return false;
            }
        }
        public Action liftDown() {
            return new LiftDown();
        }
    }
    Lift lift = new Lift(hardwareMap);





    private void function() {

    }
    @Override
    public void runOpMode() throws InterruptedException {

        int Auto = 1;
        String autoStart = new String("null");
        String autoSide = new String("null");
        String autoType = new String("null");
        boolean canProceed = false;

        Pose2d beginPose = new Pose2d(12,-62, Math.toRadians(90));


        Pose2d leftBasket = new Pose2d(-60,-60, Math.toRadians(90));
        Pose2d specimenPose = new Pose2d(0, -38, Math.toRadians(90));
        Pose2d humanPlayerPose = new Pose2d(48, -54, Math.toRadians(90));

        Pose2d coloredSample1 = new Pose2d(37, -18, Math.toRadians(90));
        //Pose2d coloredSample1 = new Pose2d(48, -8, Math.toRadians(90));
        Pose2d coloredSample2 = new Pose2d(58, -8, Math.toRadians(90));
        Pose2d coloredSample3 = new Pose2d(64, -8, Math.toRadians(90));

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
                        beginPose = new Pose2d(12,-62, Math.toRadians(90));
                        autoType = "specimen";
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

            sleep(500);

            claw.closeClaw();
            //SmartServo.setSmartPos(hardwareMap, "slideRight", 0.0);
            //SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0);
            SmartServo.setSmartPos(hardwareMap, "wristRight", 0.0);
            SmartServo.setSmartPos(hardwareMap, "wristLeft", 0.0);

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
                    .splineToLinearHeading(specimenPose, Math.toRadians(270));

            TrajectoryActionBuilder moveSamples = drive.actionBuilder(specimenPose)
                    .lineToY(-47)
                    .splineToLinearHeading(coloredSample1, Math.toRadians(123))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(coloredSample2, Math.toRadians(270))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(coloredSample3, Math.toRadians(270))
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180));

            TrajectoryActionBuilder score2 = drive.actionBuilder(humanPlayerPose)
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));

            TrajectoryActionBuilder score3 = drive.actionBuilder(specimenPose)
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));
            TrajectoryActionBuilder score4 = drive.actionBuilder(specimenPose)
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));
            TrajectoryActionBuilder score5 = drive.actionBuilder(specimenPose)
                    .lineToY(-40)
                    .splineToLinearHeading(humanPlayerPose, Math.toRadians(-180))
                    .splineToLinearHeading(specimenPose, Math.toRadians(90));

            SleepAction sleepAction = new SleepAction(400);



            //Specimen
            if (autoType.equals("specimen")) {
                Action score1built = score1.build();
                Action moveSamplesBuilt = moveSamples.build();
                Action score2built = score2.build();
                Action score3built = score3.build();
                Action score4built = score4.build();
                Action score5built = score5.build();

                Actions.runBlocking(
                        new SequentialAction(
                                score1built,
                                lift.liftUp(),
                                new SleepAction(1000),
                                new ParallelAction(
                                        moveSamplesBuilt,
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(500),
                                                lift.liftIdle()
                                        )
                                ),
                                score2built,
                                lift.liftUp(),
                                new SleepAction(1000),
                                new ParallelAction(
                                        score3built,
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(500),
                                                lift.liftIdle()
                                        )
                                ),
                                score3built,
                                lift.liftUp(),
                                new SleepAction(1000),
                                new ParallelAction(
                                        score4built,
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(500),
                                                lift.liftIdle()
                                        )
                                ),
                                score4built,
                                lift.liftUp(),
                                new SleepAction(1000),
                                new ParallelAction(
                                        score5built,
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(500),
                                                lift.liftIdle()
                                        )
                                ),
                                score2built,
                                lift.liftUp()
                        )
                );
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

            }
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
