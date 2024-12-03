package org.firstinspires.ftc.teamcode.deepBot.Classes;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class IntakeClass {

    enum transferingStates {
        IDLE,
        MOVING_LIFT,
        RETRACTING_WRIST,
        OPENING_LID,
        MOVING_EXTENDO,
        TRANSFERING,
        CLOSING_LID
    }

    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final HardwareMap hardwareMap;



    Servo intakePivot;
    Servo slideLeft;
    Servo slideRight;
    Servo outtakeLeft;
    Servo outtakeRight;
    Servo lid;
    CRServo intake;
    DcMotor dcmotor;

    CRServo contanstly_rotating_fellow;

    Servo specimenServo;

    //TODO: Variables go here.

    ElapsedTime Runtime = new ElapsedTime();


    double triggerThreshold = 0.4;
    boolean intakeToggle = false;
    boolean intakeWasPressed = false;
    boolean bWasPressed = false;
    boolean spitToggle = false;
    boolean spitWasPressed = false;
    boolean gamepad2_y_OAD = false;
    boolean gamepad2_y_LU = false;
    boolean gamepad2_y_release_OAD = false;

    boolean gamepad2_b_OAD = false;
    boolean gamepad2_b_LU = false;
    boolean gamepad2_b_release_OAD = false;

    public boolean transferRequested = false;
    public boolean transferInProgress = false;

    double transferTime = 0;

    double wrist_rotation_speed = 0.05;

    public static double extendoOffset = 0.05;


    transferingStates transferState = transferingStates.IDLE;



    public IntakeClass(@NonNull com.qualcomm.robotcore.hardware.HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        //HardwareMapping
        slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        slideRight = hardwareMap.get(Servo.class, "slideRight");
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        lid = hardwareMap.get(Servo.class, "lid");
        intake = hardwareMap.get(CRServo.class, "intake");
        intakePivot = hardwareMap.get(Servo.class, "intakePivot");
        /*
        outtakeRight.setDirection(Servo.Direction.REVERSE);
        slideLeft.setDirection(Servo.Direction.REVERSE);
        They did this in hardware
         */

        this.gamepad1 = gamepad1;
        //dcmotor.setTargetPosition(1);
        this.hardwareMap = hardwareMap;
        this.gamepad2 = gamepad2;
        //TODO: import stuff
    }



    private void intake() {
        if (gamepad2.right_trigger > triggerThreshold && gamepad2.left_trigger < triggerThreshold) {
            intake.setPower(1);
        }
    }
    private void intakeIdle() {
        if (gamepad2.right_trigger > triggerThreshold && gamepad2.left_trigger > triggerThreshold) {
            intake.setPower(0);
        }
        if (gamepad2.right_trigger < triggerThreshold && gamepad2.left_trigger < triggerThreshold) {
            intake.setPower(0);
        }
    }
    private void outtake() {
        if (gamepad2.left_trigger > triggerThreshold && gamepad2.right_trigger < triggerThreshold) {
            intake.setPower(-1);
        }
    }
    private void intakeHandling() {
        //Toggle intake on/off
        if (gamepad2.right_trigger > triggerThreshold && gamepad2.left_trigger < triggerThreshold && intakeWasPressed == false) {
            intakeToggle = true;
        }
        if (gamepad2.right_trigger < triggerThreshold && intakeWasPressed == false) {
            intakeToggle = false;
        }
        //Toggle spit on/off
        if (gamepad2.left_trigger > triggerThreshold && gamepad2.right_trigger < triggerThreshold && spitWasPressed == false) {
            spitToggle = true;
        }
        if (gamepad2.left_trigger < triggerThreshold && spitWasPressed == false) {
            spitToggle = false;
        }
        //Other Conditions
        if (gamepad2.left_trigger > triggerThreshold && gamepad2.right_trigger > triggerThreshold) {
            spitToggle = false;
            intakeToggle = false;
        }
        if (gamepad2.left_trigger < triggerThreshold && gamepad2.right_trigger < triggerThreshold) {
            spitToggle = false;
            intakeToggle = false;
        }


        //Set Power based on logic
        if (intakeToggle == true && spitToggle == false) {
            intake.setPower(1);
        }
        if (intakeToggle == false && spitToggle == true) {
            intake.setPower(-1);
        }
        if (intakeToggle == false && spitToggle == false) {
            intake.setPower(0);
        }

        //Safety
        if (spitToggle == true && intakeToggle == true) {
            intake.setPower(1);
        }


        //Update current state
        if (gamepad2.right_trigger > triggerThreshold && gamepad2.left_trigger < triggerThreshold) {
            intakeWasPressed = true;
        }
        if (gamepad2.right_trigger < triggerThreshold) {
            intakeWasPressed = false;
        }
        if (gamepad2.left_trigger > triggerThreshold && gamepad2.right_trigger < triggerThreshold) {
            spitWasPressed = true;
        }
        if (gamepad2.left_trigger < triggerThreshold) {
            spitWasPressed = false;
        }
    }
    /*
    private void specimenIntake() {
        if (gamepad2_y_OAD == true) {
            specimenServo.setPosition(1);
        }
        if (gamepad2_y_release_OAD == true) {
            specimenServo.setPosition(0);
        }
    }

     */





    private void wristRotation() {
        if (gamepad2.right_bumper == true && gamepad2.left_bumper == false) {
            //intakePivot.setPosition(intakePivot.getPosition() + wrist_rotation_speed);
            SmartServo.setSmartPos(hardwareMap,"intakePivot", intakePivot.getPosition()+wrist_rotation_speed);
        }
        if (gamepad2.left_bumper == true && gamepad2.right_bumper == false) {
            //intakePivot.setPosition(intakePivot.getPosition() - wrist_rotation_speed);
            SmartServo.setSmartPos(hardwareMap,"intakePivot", intakePivot.getPosition()-wrist_rotation_speed);
        }
            //TODO: Contrainsts
    }


    private void oneAndDone() {
        //----------Y-------------
        //o n e a n d d o n e
        if (gamepad2_y_OAD == true) {
            gamepad2_y_OAD = false;
        }
        if (gamepad2_y_release_OAD == true) {
            gamepad2_y_release_OAD = false;
        }
        //Y release detection
        if (gamepad2.y == false && gamepad2_y_LU == true) {
            gamepad2_y_OAD = false;
            gamepad2_y_release_OAD = true;
        }
        //Y press detection
        if (gamepad2.y == true && gamepad2_y_LU == false) {
            gamepad2_y_OAD = true;
            gamepad2_y_release_OAD = false;
        }
        //------------/Y-----------

        /*
        //----------B-------------
        //o n e a n d d o n e
        if (gamepad2_b_OAD == true) {
            gamepad2_b_OAD = false;
        }
        if (gamepad2_b_release_OAD == true) {
            gamepad2_b_release_OAD = false;
        }
        //B release detection
        if (gamepad2.b == false && gamepad2_b_LU == true) {
            gamepad2_b_OAD = false;
            gamepad2_b_release_OAD = true;
        }
        //B press detection
        if (gamepad2.b == true && gamepad2_b_LU == false) {
            gamepad2_b_OAD = true;
            gamepad2_b_release_OAD = false;
            transferRequested = true;

        }

         */
        //------------/B-----------




        oneAndDoneUpdate();
    }

    private void oneAndDoneNew(String servoString) {
        Servo servoToUse;

        servoToUse = hardwareMap.get(Servo.class, servoString);
    }

    private void transferSequence() {
        if (transferRequested == true || transferInProgress == true) {
            transferRequested = false;

            if (transferState == transferingStates.IDLE) {
                transferInProgress = true;
                transferState = transferingStates.MOVING_LIFT;
            }
            if (transferState == transferingStates.MOVING_LIFT) {
                transferTime = Runtime.seconds();
                //TODO: Add

                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferingStates.RETRACTING_WRIST;
                }

            }
            if (transferState == transferingStates.RETRACTING_WRIST) {
                transferTime = Runtime.seconds();

                SmartServo.setSmartPos(hardwareMap,"intakePivot",0.5);

                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferingStates.OPENING_LID;
                }

            }
            if (transferState == transferingStates.OPENING_LID) {
                SmartServo.setSmartPos(hardwareMap, "lid", 0.6);

                transferTime = Runtime.seconds();

                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferingStates.MOVING_EXTENDO;
                }

            }
            if (transferState == transferingStates.MOVING_EXTENDO) {
                transferTime = Runtime.seconds();

                SmartServo.setSmartPos(hardwareMap, "slideRight", 0.0);
                SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0 + extendoOffset);

                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferingStates.TRANSFERING;
                }

            }
            if (transferState == transferingStates.TRANSFERING) {
                transferTime = Runtime.seconds();
                //Set CRSERVO Power to 1
                if (Runtime.seconds() - transferTime >= 3) {
                    //Set CRServo Power to 0
                    transferState = transferingStates.CLOSING_LID;
                }


            }
            if (transferState == transferingStates.CLOSING_LID) {
                transferTime = Runtime.seconds();

                if (Runtime.seconds() - transferTime >= 0.5) {
                    transferState = transferingStates.TRANSFERING;
                }
                transferInProgress = false;
                transferState = transferingStates.IDLE;
            }

        }
    }


    private void returnToDefaultPos() {
        if (gamepad2.b == true && bWasPressed == false) {
            hardwareMap.get(DcMotor.class, "rightLift").setPower(0);
            hardwareMap.get(DcMotor.class, "leftLift").setPower(0);

            SmartServo.setSmartPos(hardwareMap,"slideLeft", 0.0 + extendoOffset);
            SmartServo.setSmartPos(hardwareMap,"slideRight", 0.0);
            SmartServo.setSmartPos(hardwareMap,"intakePivot", 0.44);
            SmartServo.setSmartPos(hardwareMap,"outtakeRight", 0.18);
            SmartServo.setSmartPos(hardwareMap,"outtakeLeft", 0.18);
            SmartServo.setSmartPos(hardwareMap,"lid", 0.6);
        }
        bWasPressed = gamepad2.b;
    }




    /*
    private void setSmartPos(String servoName, double setTo) {
        Servo servoToUse;
        servoToUse = hardwareMap.get(Servo.class, servoName);

        // strange option?
            if (setTo < ServoConstraints.constraintsForEachServo.get("wrist").low){
                servoToUse.setPosition(ServoConstraints.constraintsForEachServo.get("wrist").low);
            }
            if (setTo > ServoConstraints.constraintsForEachServo.get("wrist").high){
                servoToUse.setPosition(ServoConstraints.constraintsForEachServo.get("wrist").high);
            }

    }

     */

    private void extendoHandler() {
        //slideLeft.setPosition(gamepad2.right_stick_y/10 + slideLeft.getPosition() + extendoOffset);
        SmartServo.setSmartPos(hardwareMap,"slideLeft",(-1*gamepad2.right_stick_y/40 + slideLeft.getPosition()));
        //slideRight.setPosition(gamepad2.right_stick_y/10 + slideRight.getPosition());
        SmartServo.setSmartPos(hardwareMap,"slideRight",-1*gamepad2.right_stick_y/40 + slideRight.getPosition());
    }

    public void runIntake() {
        returnToDefaultPos();
        extendoHandler();
        transferSequence();
        oneAndDone();
        wristRotation();
        intakeHandling();
    }
    public boolean isWristDown() {
        if (intakePivot.getPosition() <= 0.6) return true;
        else{
            return false;
        }
    }





    private void oneAndDoneUpdate() {
        //Last Instance Update
        gamepad2_y_LU = gamepad1.y;
        gamepad2_b_LU = gamepad2.b;
    }
}
