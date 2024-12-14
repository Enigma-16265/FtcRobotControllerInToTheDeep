package org.firstinspires.ftc.teamcode.Link.Classes;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/** @noinspection PointlessBooleanExpression*/
public class IntakeClass {

    // variables
    enum transferringStates {
        IDLE,
        MOVING_LIFT,
        RETRACTING_WRIST,
        OPENING_LID,
        MOVING_EXTENDO,
        TRANSFERRING,
        CLOSING_LID
    }

    private final Gamepad gamepad2;
    private final HardwareMap hardwareMap;


    // hardware variables
    Servo intakePivot;
    Servo slideLeft;
    Servo slideRight;
    Servo outtakeLeft;
    Servo outtakeRight;
    Servo lid;
    CRServo intake;
    DcMotor dcmotor;
    Servo specimenServo;

    ElapsedTime Runtime = new ElapsedTime();

    // lots of usage variables
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


    transferringStates transferState = transferringStates.IDLE;


    // constructor
    public IntakeClass(@NonNull HardwareMap hardwareMap, Gamepad gamepad2) {
        // instantiate variables
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

        //dcmotor.setTargetPosition(1);
        this.hardwareMap = hardwareMap;
        this.gamepad2 = gamepad2;
    }

    // handle intake functions
    private void intakeHandling() {
        //Toggle intake on/off
        if (intakeWasPressed == false) {
            if (gamepad2.right_trigger > triggerThreshold && gamepad2.left_trigger < triggerThreshold) {
                intakeToggle = true;
            }
            if (gamepad2.right_trigger < triggerThreshold) {
                intakeToggle = false;
            }
        }

        //Toggle spit on/off
        if (spitWasPressed == false) {
            if (gamepad2.left_trigger > triggerThreshold && gamepad2.right_trigger < triggerThreshold) {
                spitToggle = true;
            }
            if (gamepad2.left_trigger < triggerThreshold) {
                spitToggle = false;
            }
        }

        // if triggers are both on or both off, do nothing.
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
            intake.setPower(-0.8);
        }
        if (intakeToggle == false && spitToggle == false) {
            intake.setPower(0);
        }

        //Safety
        if (spitToggle == true && intakeToggle == true) {
            intake.setPower(1);
        }


        //Update current wasPressed state
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
    } // end of method

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




    // control rotation of the wrist
    private void wristRotation() {
        if (gamepad2.right_bumper && gamepad2.left_bumper == false) {
            SmartServo.setSmartPos(hardwareMap,"intakePivot", intakePivot.getPosition()+wrist_rotation_speed);
        }
        else if (gamepad2.left_bumper && gamepad2.right_bumper == false) {
            SmartServo.setSmartPos(hardwareMap,"intakePivot", intakePivot.getPosition()-wrist_rotation_speed);
        }
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
        if (gamepad2.y && gamepad2_y_LU == false) {
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

    // control the sequence for transferring from intake to outtake
    private void transferSequence() {
        // check to see if currently transferring
        if (transferRequested || transferInProgress) {
            transferRequested = false;

            // if transfer is idle (what its set to at the start)
            if (transferState == transferringStates.IDLE) {
                transferInProgress = true;
                transferState = transferringStates.MOVING_LIFT;
            }
            // if transfer is moving lift
            if (transferState == transferringStates.MOVING_LIFT) {
                transferTime = Runtime.seconds();
                //TODO: Add lift control

                // wait until lift is down
                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferringStates.RETRACTING_WRIST;
                }
            }
            // if transfer is retracting wrist
            if (transferState == transferringStates.RETRACTING_WRIST) {
                transferTime = Runtime.seconds();

                SmartServo.setSmartPos(hardwareMap,"intakePivot",0.5);

                // wait
                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferringStates.OPENING_LID;
                }
            }
            // if transfer is opening lid
            if (transferState == transferringStates.OPENING_LID) {
                SmartServo.setSmartPos(hardwareMap, "lid", 0.6);

                transferTime = Runtime.seconds();

                // wait
                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferringStates.MOVING_EXTENDO;
                }
            }
            // if transfer is moving extendo in
            if (transferState == transferringStates.MOVING_EXTENDO) {
                transferTime = Runtime.seconds();

                SmartServo.setSmartPos(hardwareMap, "slideRight", 0.0);
                SmartServo.setSmartPos(hardwareMap, "slideLeft", 0.0 + extendoOffset);
                // wait
                if (Runtime.seconds() - transferTime >= 1) {
                    transferState = transferringStates.TRANSFERRING;
                }
            }
            // if transfer is moving sample to outtake
            if (transferState == transferringStates.TRANSFERRING) {
                transferTime = Runtime.seconds();
                //Set CRSERVO Power to 1
                if (Runtime.seconds() - transferTime >= 3) {
                    //Set CRServo Power to 0
                    transferState = transferringStates.CLOSING_LID;
                }
            }
            // if transfer is closing lid
            if (transferState == transferringStates.CLOSING_LID) {
                transferTime = Runtime.seconds();

                if (Runtime.seconds() - transferTime >= 0.5) {
                    transferState = transferringStates.TRANSFERRING;
                }
                // set back to idle
                transferInProgress = false;
                transferState = transferringStates.IDLE;
            }
        } // end of if stmt asking if currently transferring

    } // end of method


    // sets all servos and motors to default pos
    // should button press be asked in runIntake so its more accessible? doesn't really matter much
    private void returnToDefaultPos() {
        if (gamepad2.b && bWasPressed == false) {
            hardwareMap.get(DcMotor.class, "rightLift").setPower(0);
            hardwareMap.get(DcMotor.class, "leftLift").setPower(0);

            SmartServo.setSmartPos(hardwareMap,"slideLeft", 0.0 + extendoOffset);
            SmartServo.setSmartPos(hardwareMap,"slideRight", 0.0);
            SmartServo.setSmartPos(hardwareMap,"intakePivot", 0.3278);
            SmartServo.setSmartPos(hardwareMap,"outtakeRight", 0.18);
            SmartServo.setSmartPos(hardwareMap,"outtakeLeft", 0.18);
            SmartServo.setSmartPos(hardwareMap,"lid", 0.6);
        }
        bWasPressed = gamepad2.b;
    }



    // control extendo
    private void extendoHandler() {
        double leftPos  = -1*gamepad2.right_stick_y/40 + slideLeft.getPosition();
        double rightPos = -1*gamepad2.right_stick_y/40 + slideRight.getPosition();
        SmartServo.setSmartPos(hardwareMap,"slideLeft",leftPos);
        SmartServo.setSmartPos(hardwareMap,"slideRight",rightPos);
    }

    // should this be called in runIntake? is anything else using x?
    private void closeLid() {
        if (gamepad2.x) {
            SmartServo.setSmartPos(hardwareMap, "lid", 0.1);
        }
    }

    // run all necessary functions for intake. This should be called in opMode
    public void runIntake() {
        returnToDefaultPos();
        extendoHandler();
        transferSequence();
        oneAndDone();
        wristRotation();
        intakeHandling();
    }

    // returns whether or not the wrist is down obv
    public boolean isWristDown() {
        return intakePivot.getPosition() >= 0.4;
    }



    private void oneAndDoneUpdate() {
        //Last Instance Update
        gamepad2_y_LU = gamepad2.y;
        gamepad2_b_LU = gamepad2.b;
    }
}
