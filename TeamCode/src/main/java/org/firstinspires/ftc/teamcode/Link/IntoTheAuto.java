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
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Link.Classes.IntakeClass;
import org.firstinspires.ftc.teamcode.Link.Classes.SmartServo;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
import org.opencv.core.Mat;

enum InitStep {
    START,
    DONE
}


@Config
@Autonomous(name = "Into the Auto", group = "16265-Auto")
public final class IntoTheAuto extends LinearOpMode {

    public IntakeClass.clawStates clawState = IntakeClass.clawStates.OPEN;
    private double extendoOffset = IntakeClass.extendoOffset;

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
                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.27);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.27);
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
                SmartServo.setSmartPos(hardwareMap, "claw", 0);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                SmartServo.setSmartPos(hardwareMap, "claw", 0.34);
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

        public class LiftDownFast implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftLift.setPower(-0.6);
                rightLift.setPower(-0.6);
                return false;
            }
        }
        public Action liftDownFast() {
            return new LiftDownFast();
        }
    }

    public class SampleActions {
        Servo outtakeLeft;
        Servo outtakeRight;
        Servo slideLeft;
        Servo slideRight;

        CRServo intakeLeft;
        CRServo intakeRight;
        Servo wristLeft;
        Servo wristRight;
        Servo claw;

        /*
         * mom, do we have skibidi slicers?
         */

        public SampleActions(HardwareMap hardwareMap) {
            outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
            outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
            slideLeft = hardwareMap.get(Servo.class, "slideLeft");
            slideRight = hardwareMap.get(Servo.class, "slideRight");

            intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
            intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
            wristLeft = hardwareMap.get(Servo.class,"wristLeft");
            wristRight = hardwareMap.get(Servo.class,"wristLeft");
            claw = hardwareMap.get(Servo.class, "claw");

            slideLeft.setDirection(Servo.Direction.REVERSE);
            slideRight.setDirection(Servo.Direction.REVERSE);
            claw.setDirection(Servo.Direction.REVERSE);

            intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        public class YoinkilySpadoinkily implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                slideLeft.setDirection(Servo.Direction.REVERSE);
                slideRight.setDirection(Servo.Direction.REVERSE);
                claw.setDirection(Servo.Direction.REVERSE);

                intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

                /*
                SmartServo.setSmartPos(hardwareMap,"wristLeft", 0);
                SmartServo.setSmartPos(hardwareMap,"wristRight", 0);


                intakeRight.setPower(-1);
                intakeLeft.setPower(-1);

                //wristLeft.setPosition(0.57);
                //wristRight.setPosition(0.57);

                sleep(500);


                 */

                SmartServo.setSmartPos(hardwareMap,"slideLeft", 0.23 + extendoOffset);
                SmartServo.setSmartPos(hardwareMap,"slideRight", 0.23);

                sleep(350);



                SmartServo.setSmartPos(hardwareMap,"wristLeft", 0.57);
                SmartServo.setSmartPos(hardwareMap,"wristRight", 0.57);

                sleep(150);

                SmartServo.setSmartPos(hardwareMap,"slideLeft", 0 + extendoOffset);
                SmartServo.setSmartPos(hardwareMap,"slideRight", 0);

                return false;
            }
        }
        public Action yoinkilySpadoinkily() {
            return new YoinkilySpadoinkily();
        }

        public class ScoreSample implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                slideLeft.setDirection(Servo.Direction.REVERSE);
                slideRight.setDirection(Servo.Direction.REVERSE);
                claw.setDirection(Servo.Direction.REVERSE);

                intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

                DcMotor leftLift = hardwareMap.get(DcMotor.class, "leftLift");
                DcMotor rightLift = hardwareMap.get(DcMotor.class, "rightLift");

                leftLift.setPower(1);
                rightLift.setPower(1);

                sleep(1000);

                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.8);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.8);
                leftLift.setPower(0.6);
                rightLift.setPower(0.6);

                sleep(650);

                openClaw();

                sleep(200);

                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.09);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.09);

                SmartServo.setSmartPos(hardwareMap,"slideLeft", 0.12 + extendoOffset);
                SmartServo.setSmartPos(hardwareMap,"slideRight", 0.12);

                SmartServo.setSmartPos(hardwareMap,"wristLeft", 0);
                SmartServo.setSmartPos(hardwareMap,"wristRight", 0);

                intakeRight.setPower(-1);
                intakeLeft.setPower(-1);

                return false;
            }
        }
        public Action scoreSample() {
            return new ScoreSample();
        }

        public class SampleTransfer implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                slideLeft.setDirection(Servo.Direction.REVERSE);
                slideRight.setDirection(Servo.Direction.REVERSE);
                claw.setDirection(Servo.Direction.REVERSE);

                intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

                openClaw();
                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.14);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.14);
                SmartServo.setSmartPos(hardwareMap,"wristLeft" , 0.53);
                SmartServo.setSmartPos(hardwareMap,"wristRight", 0.53);

                sleep(400);

                SmartServo.setSmartPos(hardwareMap,"slideLeft", 0 + extendoOffset);
                SmartServo.setSmartPos(hardwareMap,"slideRight", 0);

                sleep(333);

                closeClaw();

                sleep(100);

                /*
                sleep(350);

                intakeLeft.setPower(-1);
                intakeRight.setPower(-1);

                sleep(500);

                 */

                SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.47);
                SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.47);

                intakeLeft.setPower(0);
                intakeRight.setPower(0);

                return false;
            }
        }
        public Action sampleTransfer() {
            return new SampleTransfer();
        }
    }

    private void toggleClaw() {
        IntakeClass.clawStates currectClawState = clawState;
        if (currectClawState == IntakeClass.clawStates.OPEN) {
            closeClaw();
        }
        if (currectClawState == IntakeClass.clawStates.CLOSE) {
            openClaw();
        }
    }

    private void closeClaw() {
        SmartServo.setSmartPos(hardwareMap, "claw", 0);
        clawState = IntakeClass.clawStates.CLOSE;
    }
    private void openClaw() {
        SmartServo.setSmartPos(hardwareMap, "claw", 0.3);
        clawState = IntakeClass.clawStates.OPEN;
    }

    /*


    private void transferSample() {

        Servo outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        Servo outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        Servo slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        Servo slideRight = hardwareMap.get(Servo.class, "slideRight");

        CRServo intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        CRServo intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        Servo wristLeft = hardwareMap.get(Servo.class,"wristLeft");
        Servo wristRight = hardwareMap.get(Servo.class,"wristLeft");
        Servo claw = hardwareMap.get(Servo.class, "claw");

        slideLeft.setDirection(Servo.Direction.REVERSE);
        slideRight.setDirection(Servo.Direction.REVERSE);
        claw.setDirection(Servo.Direction.REVERSE);

        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);



        openClaw();
        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.09);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.09);
        SmartServo.setSmartPos(hardwareMap,"wristLeft" , 0.53);
        SmartServo.setSmartPos(hardwareMap,"wristRight", 0.53);

        sleep(600);

        SmartServo.setSmartPos(hardwareMap,"slideLeft", 0 + extendoOffset);
        SmartServo.setSmartPos(hardwareMap,"slideRight", 0);

        sleep(500);

        closeClaw();

        sleep(350);

        intakeLeft.setPower(-1);
        intakeRight.setPower(-1);

        sleep(500);

        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.45);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.45);

        sleep(200);

        intakeLeft.setPower(0);
        intakeRight.setPower(0);
    }
    //:
    private void scoreSample() {
        DcMotor leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        DcMotor rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        Servo outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");

        leftLift.setPower(1);
        rightLift.setPower(1);

        sleep(1000);

        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.7);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.7);
        leftLift.setPower(0.6);
        rightLift.setPower(0.6);

        sleep(350);


        openClaw();

        sleep(200);

        SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.09);
        SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.09);
    }

    private void yoinkilySpadoinkaly() {
        Servo slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        Servo slideRight = hardwareMap.get(Servo.class, "slideRight");

        CRServo intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        CRServo intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        Servo wristLeft = hardwareMap.get(Servo.class,"wristLeft");
        Servo wristRight = hardwareMap.get(Servo.class,"wristLeft");

        slideLeft.setDirection(Servo.Direction.REVERSE);
        slideRight.setDirection(Servo.Direction.REVERSE);

        // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

        SmartServo.setSmartPos(hardwareMap,"wristLeft", 0.57);
        SmartServo.setSmartPos(hardwareMap,"wristRight", 0.57);

        sleep(100);

        intakeRight.setPower(-1);
        intakeLeft.setPower(1);

        sleep(650);

        SmartServo.setSmartPos(hardwareMap,"wristLeft", 0);
        SmartServo.setSmartPos(hardwareMap,"wristRight", 0);

        sleep(100);

        SmartServo.setSmartPos(hardwareMap,"slideLeft", 0);
        SmartServo.setSmartPos(hardwareMap,"slideRight", 0);
    }

     */

    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = new Lift(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        SampleActions sampleActions = new SampleActions(hardwareMap);

        //DO NOT MOVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! RAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHH

        String autoStart = new String("null");
        String autoSide = new String("null");
        String autoType = new String("null");
        boolean canProceed = false;

        Pose2d beginPose = new Pose2d(12,-62, Math.toRadians(90));


        Pose2d leftBasket = new Pose2d(-55,-51, Math.toRadians(45));
        Pose2d specimenPose = new Pose2d(-3, -27, Math.toRadians(90));
        Pose2d specimenPoseLeft = new Pose2d(-6, -27, Math.toRadians(90));
        Pose2d specimenPoseRight = new Pose2d(0, -27, Math.toRadians(90));
        Pose2d humanPlayerPose = new Pose2d(48, -50, Math.toRadians(90));
        Pose2d returnFromGiftPose = new Pose2d(46, -48, Math.toRadians(90));
        //TODO: reduce accel from 2nd pickup

        Pose2d yoinkPose = new Pose2d(44, -53.25, Math.toRadians(90));
        Pose2d yoinkPose2 = new Pose2d(48, -52.75, Math.toRadians(90));

        Pose2d transitionPose = new Pose2d(44, -38, Math.toRadians(90));
        Pose2d coloredSample1 = new Pose2d(51, -8, Math.toRadians(90));
        //Pose2d coloredSample1 = new Pose2d(48, -8, Math.toRadians(90));
        Pose2d coloredSample2 = new Pose2d(56, -16, Math.toRadians(90));
        Pose2d coloredSample3 = new Pose2d(72, -8, Math.toRadians(90));

        Pose2d cycle2Actor = new Pose2d(56, -40, Math.toRadians(90));






        Pose2d intake2ndSample = new Pose2d(-49.5, -40, Math.toRadians(90));
        Pose2d intake3rdSample = new Pose2d(-60, -40, Math.toRadians(90));
        Pose2d intake4thSample = new Pose2d(-59, -35, Math.toRadians(135));

        Servo slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        Servo slideRight = hardwareMap.get(Servo.class, "slideRight");
        Servo wristLeft = hardwareMap.get(Servo.class,"wristLeft");
        Servo wristRight = hardwareMap.get(Servo.class,"wristLeft");
        Servo outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        Servo outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        Servo clawServo = hardwareMap.get(Servo.class, "claw");
        CRServo intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        CRServo intakeRight = hardwareMap.get(CRServo.class, "intakeRight");


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
                        beginPose = new Pose2d(-33,-62, Math.toRadians(90));
                        autoType = "sample";
                        canProceed = true;
                    }
                    if (gamepad1.b == true) {
                        beginPose = new Pose2d(14,-61.5, Math.toRadians(90));
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
            SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0 + extendoOffset);
            SmartServo.setSmartPos(hardwareMap,"wristLeft", 0.52);
            SmartServo.setSmartPos(hardwareMap,"wristRight", 0.52);
            SmartServo.setSmartPos(hardwareMap, "outtakeLeft", 0.1678);
            SmartServo.setSmartPos(hardwareMap, "outtakeRight", 0.1678);

            sleep(3500);

            clawServo.setPosition(0);

            waitForStart();




            TrajectoryActionBuilder dump1st = drive.actionBuilder(beginPose)
                    .splineToLinearHeading(leftBasket, Math.toRadians(180));

            TrajectoryActionBuilder intake2nd = drive.actionBuilder(leftBasket)
                    .setTangent(45)
                    .splineToLinearHeading(intake2ndSample, Math.toRadians(90));

            TrajectoryActionBuilder dump2nd = drive.actionBuilder(intake2ndSample)
                    .setTangent(270)
                    .splineToLinearHeading(leftBasket, Math.toRadians(180));

            TrajectoryActionBuilder intake3rd = drive.actionBuilder(leftBasket)
                    .setTangent(45)
                    .splineToLinearHeading(intake3rdSample, Math.toRadians(90));

            TrajectoryActionBuilder dump3rd = drive.actionBuilder(intake3rdSample)
                    .setTangent(270)
                    .splineToLinearHeading(leftBasket, Math.toRadians(180));

            TrajectoryActionBuilder intake4th = drive.actionBuilder(leftBasket)
                    .setTangent(45)
                    .splineToLinearHeading(intake4thSample, Math.toRadians(90));

            TrajectoryActionBuilder dump4th = drive.actionBuilder(intake4thSample)
                    .setTangent(270)
                    .splineToLinearHeading(leftBasket, Math.toRadians(180));

            Action dump1stBuilt = dump1st.build();
            Action intake2ndBuilt = intake2nd.build();
            Action dump2ndBuilt = dump2nd.build();
            Action intake3rdBuilt = intake3rd.build();
            Action dump3rdBuilt = dump3rd.build();
            Action intake4thBuilt = intake4th.build();
            Action dump4thBuilt = dump4th.build();







            //sample
            if (autoType.equals("sample")) {
                Actions.runBlocking(
                        new SequentialAction(
                                //TRACK score 1st
                                dump1stBuilt,
                                new SleepAction(0.1),
                                sampleActions.scoreSample(),
                                new SleepAction(1),
                                //TRACK For second
                                new ParallelAction(
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(2.0),
                                                lift.liftIdle()
                                        ),
                                        intake2ndBuilt
                                ),
                                sampleActions.yoinkilySpadoinkily(), //btw this is to intake the sample
                                new ParallelAction(
                                        sampleActions.sampleTransfer(),
                                        dump2ndBuilt
                                ),
                                new SleepAction(0.1),
                                sampleActions.scoreSample(),
                                new SleepAction(1),
                                //TRACK For third
                                new ParallelAction(
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(2.0),
                                                lift.liftIdle()
                                        ),
                                        intake3rdBuilt
                                ),
                                sampleActions.yoinkilySpadoinkily(), //btw this is to intake the sample
                                new ParallelAction(
                                        sampleActions.sampleTransfer(),
                                        dump3rdBuilt
                                ),
                                new SleepAction(0.1),
                                sampleActions.scoreSample(),
                                new SleepAction(1),
                                //TRACK For 4rd
                                new ParallelAction(
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(2.0),
                                                lift.liftIdle()
                                        ),
                                        intake4thBuilt
                                ),
                                sampleActions.yoinkilySpadoinkily(), //btw this is to intake the sample
                                new ParallelAction(
                                        sampleActions.sampleTransfer(),
                                        dump4thBuilt
                                ),
                                new SleepAction(0.1),
                                sampleActions.scoreSample(),
                                new SleepAction(1),

                                lift.liftDown(),
                                new SleepAction(2.0),
                                lift.liftIdle(),
                                new SleepAction(1000)
                        )
                );
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
                    .splineToLinearHeading(returnFromGiftPose, Math.toRadians(-180));
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
            TrajectoryActionBuilder yoink = drive.actionBuilder(returnFromGiftPose).
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
                                                arm.specimenOuttakePos()
                                                /*lift.liftUp(),
                                                new SleepAction(.2),
                                                lift.liftIdle()

                                                 */
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
                                        //Going to move samples elliot is stupid
                                        new SequentialAction(
                                                lift.liftDown(),
                                                new SleepAction(2.75),
                                                lift.liftIdle()
                                        )
                                ),
                                new SleepAction(1),

                                //TRACK Picks up specimen #2
                                arm.armSpecimenIntake(),
                                new SleepAction(0.6),
                                yoinkBuilt,
                                new SleepAction(0.2),
                                claw.closeClaw(),
                                new SleepAction(0.5),
                                arm.specimenOuttakePos(),
                                new SleepAction(0.7),

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
                                new SleepAction(0.5),
                                arm.specimenOuttakePos(),
                                new SleepAction(0.8),
                                specimenFromHumanPlayerRight,
                                lift.liftUp(),
                                new SleepAction(0.7),
                                lift.liftIdle(),
                                claw.openClaw(),
                                new SleepAction(0.2),

                                //track puts lift down
                                lift.liftDownFast(),
                                new SleepAction(1),
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



        } else {
            throw new RuntimeException();
        }
    }
}
