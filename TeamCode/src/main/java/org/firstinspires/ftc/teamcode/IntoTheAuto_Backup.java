package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Link.Classes.SmartServo;

@Autonomous(name = "IntoTheAuto")
public final class IntoTheAuto_Backup extends LinearOpMode {

    //private DcMotor leftDrive   = null;
    //private DcMotor         rightDrive  = null;
    private DcMotor rightLift;
    private DcMotor leftLift;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;
    private Servo lid;

    private ElapsedTime runtime = new ElapsedTime();

    private void dump() {


        rightLift.setPower(1);
        leftLift.setPower(1);
        sleep(4000);

        rightLift.setPower(0.2);
        leftLift.setPower(0.2);

        // close lid, move to next step
        SmartServo.setSmartPos(hardwareMap,"lid", 0);
        sleep(2000);

        // turn wrist, move to next step
        SmartServo.setSmartPos(hardwareMap,"outtakeRight",1);
        SmartServo.setSmartPos(hardwareMap,"outtakeLeft",1);
        sleep(2300);

        // open lid, move to next step
        SmartServo.setSmartPos(hardwareMap,"lid",0.6);
        sleep(3000);



        // return to transfer position
        SmartServo.setSmartPos(hardwareMap,"outtakeRight", 0.2);
        SmartServo.setSmartPos(hardwareMap,"outtakeLeft", 0.2);
        SmartServo.setSmartPos(hardwareMap,"lid", 0);
        sleep(2000);

        rightLift.setPower(-0.1);
        leftLift.setPower(-0.1);
        sleep(1000);

        rightLift.setPower(0);
        leftLift.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        lid = hardwareMap.get(Servo.class, "lid");


        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");

        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        lid.setDirection(Servo.Direction.REVERSE);

        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();


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

        // Wait for the game to start (driver presses START)
        waitForStart();

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
