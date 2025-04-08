package org.firstinspires.ftc.teamcode.Link.Classes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
@TeleOp
public class PIDF_Lift extends OpMode {
    private PIDController controller;

    public static double p = 0.09, i = 0.001, d = 0.0008;
    public static double f = 0.14;

    public static int target = 0;

    private final double ticks_in_degree = 700  / 180.0;

    private DcMotor lift_left;
    private DcMotor lift_right;



    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        lift_left = hardwareMap.get(DcMotor.class, "leftLift");
        lift_right = hardwareMap.get(DcMotor.class, "rightLift");
    }


    public void liftMain() {
        controller.setPID(p, i, d);
        int liftPos = lift_left.getCurrentPosition();
        double pid = controller.calculate(liftPos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = pid + ff;


        if (power < -0.25) {
            power = -0.25;
        }



        lift_left.setPower(power);
        lift_right.setPower(power);

        telemetry.addData("pos ", liftPos);
        telemetry.addData("target ", target);
        telemetry.addData("power ", power);
        telemetry.addData("pid ", pid);
        telemetry.update();
    }

    @Override
    public void loop() {
        liftMain();
    }
}
