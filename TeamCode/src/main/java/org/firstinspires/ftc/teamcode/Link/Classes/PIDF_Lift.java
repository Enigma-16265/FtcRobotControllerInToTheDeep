package org.firstinspires.ftc.teamcode.Link.Classes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class PIDF_Lift extends LinearOpMode {
    private PIDController controller;

    public static double p = 0.05, i = 0, d = 0.1;
    public static double f = 0.1;

    public static int target = 0;

    private final double ticks_in_degree = 700 / 180.0; //TODO probably change this lmao

    private DcMotor leftLift;
    private DcMotor rightLift;
    private DcMotor armMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        //leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        //rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        //leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            controller.setPID(p, i, d);
            int liftPos = leftLift.getCurrentPosition();
            double pid = controller.calculate(liftPos, target);
            double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

            double power = pid + ff;

            leftLift.setPower(power);
            rightLift.setPower(power);

            telemetry.addData("pos", liftPos);
            telemetry.addData("target", target);
            telemetry.update();
        }
    }

}
