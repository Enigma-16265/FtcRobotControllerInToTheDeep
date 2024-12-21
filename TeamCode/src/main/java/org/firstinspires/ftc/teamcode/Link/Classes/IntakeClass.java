package org.firstinspires.ftc.teamcode.Link.Classes;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


/** @noinspection PointlessBooleanExpression*/
public class IntakeClass {

    // variables
    enum transferringStates {
        IDLE,
        OPENING_AND_MOVING_SERVOS,
        RETRACTING_EXTENDO,
        CLOSING_CLAW,
        MAIN_TRANSFER,
        FINISH
    }
    enum colorSensorReturns {
        NOT_IN_INTAKE,
        RED,
        BLUE,
        YELLOW,
        OTHER
    }

    private final Gamepad gamepad2;
    private final HardwareMap hardwareMap;


    ElapsedTime Runtime = new ElapsedTime();

    Servo outtakeLeft;
    Servo outtakeRight;
    Servo slideLeft;
    Servo slideRight;
    DcMotor leftLift;
    DcMotor rightLift;
    //CRServo intake = hardwareMap.get(CRServo.class, "intake");

    CRServo intakeLeft;
    CRServo intakeRight;
    Servo wristLeft;
    Servo wristRight;
    Servo claw;

    RevColorSensorV3 colorSensor;

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

    private double redDetection = 0.0;
    private double greenDetection = 0.0;
    private double blueDetection = 0.0;

    double transferTime = 0;

    double wrist_rotation_speed = 0.05;

    public static double extendoOffset = 0.05;
    private double intakeWristThreshold = 0.4;

    String team = "red";


    transferringStates transferState = transferringStates.IDLE;
    colorSensorReturns CSReturn = colorSensorReturns.NOT_IN_INTAKE;


    // constructor
    public IntakeClass(@NonNull HardwareMap hardwareMap, Gamepad gamepad2) {
        // instantiate variables
        outtakeLeft = hardwareMap.get(Servo.class, "outtakeLeft");
        outtakeRight = hardwareMap.get(Servo.class, "outtakeRight");
        slideLeft = hardwareMap.get(Servo.class, "slideLeft");
        slideRight = hardwareMap.get(Servo.class, "slideRight");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        //CRServo intake = hardwareMap.get(CRServo.class, "intake");

        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        wristLeft = hardwareMap.get(Servo.class,"wristLeft");
        wristRight = hardwareMap.get(Servo.class,"wristLeft");
        claw = hardwareMap.get(Servo.class, "claw");


        slideLeft.setDirection(Servo.Direction.REVERSE);
        slideRight.setDirection(Servo.Direction.REVERSE);
        claw.setDirection(Servo.Direction.REVERSE);
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
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
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
        }
        if (intakeToggle == false && spitToggle == true) {
            intakeLeft.setPower(-0.8);
            intakeRight.setPower(0.8);
        }
        if (intakeToggle == false && spitToggle == false) {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);

            if (team == "red") {
                //If robot has sample
                if (colorDetection() == colorSensorReturns.BLUE && colorSensor.getDistance(DistanceUnit.INCH) < 3) {
                    intakeRight.setPower(-1);
                }
            }
            if (team == "blue") {
                //If robot has sample
                if (colorDetection() == colorSensorReturns.RED && colorSensor.getDistance(DistanceUnit.INCH) < 3) {
                    intakeRight.setPower(-1);
                }
            }
        }

        //Safety
        if (spitToggle == true && intakeToggle == true) {
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
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

    private Enum<colorSensorReturns> colorDetection() {
        if (wristLeft.getPosition() > intakeWristThreshold && wristRight.getPosition() > intakeWristThreshold) {
            redDetection = colorSensor.red(); //Add the actual value
            greenDetection = colorSensor.green(); //Add the actual value
            blueDetection = colorSensor.blue(); //Add the actual value

            if (redDetection > (greenDetection + blueDetection)) {
                CSReturn = colorSensorReturns.RED;
            }
            if (((redDetection + greenDetection) - blueDetection) > 128) {
                CSReturn = colorSensorReturns.YELLOW;
            }
            if (blueDetection > (redDetection + greenDetection)) {
                CSReturn = colorSensorReturns.BLUE;
            }
            else {
                CSReturn = colorSensorReturns.OTHER;
            }
        }
        else {
            CSReturn = colorSensorReturns.NOT_IN_INTAKE;
        }
        return CSReturn;
    }




    // control rotation of the wrist
    private void wristRotation() {
        if (gamepad2.right_bumper && gamepad2.left_bumper == false) {
            SmartServo.setSmartPos(hardwareMap,"wristLeft", wristLeft.getPosition()+wrist_rotation_speed);
            SmartServo.setSmartPos(hardwareMap,"wristRight", wristRight.getPosition()+wrist_rotation_speed);
        }
        else if (gamepad2.left_bumper && gamepad2.right_bumper == false) {
            SmartServo.setSmartPos(hardwareMap,"wristLeft", wristLeft.getPosition()-wrist_rotation_speed);
            SmartServo.setSmartPos(hardwareMap,"wristRight", wristRight.getPosition()-wrist_rotation_speed);
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
        if (transferRequested || transferInProgress) {
            transferRequested = false;
            /*
            IDLE,
        OPENING_AND_MOVING_SERVOS,
        RETRACTING_EXTENDO,
        CLOSING_CLAW,
        MAIN_TRANSFER,
        FINISH
             */
            if (transferState == transferringStates.IDLE) {
                transferState = transferringStates.OPENING_AND_MOVING_SERVOS;
            }
            if (transferState == transferringStates.OPENING_AND_MOVING_SERVOS) {
                transferState = transferringStates.RETRACTING_EXTENDO;
            }
            if (transferState == transferringStates.RETRACTING_EXTENDO) {
                transferState = transferringStates.CLOSING_CLAW;
            }
            if (transferState == transferringStates.CLOSING_CLAW) {
                transferState = transferringStates.MAIN_TRANSFER;
            }
            if (transferState == transferringStates.MAIN_TRANSFER) {
                transferState = transferringStates.FINISH;
            }
            if (transferState == transferringStates.FINISH) {
                transferState = transferringStates.IDLE;
            }
            //TODO: This ^
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
            //SmartServo.setSmartPos(hardwareMap,"intakePivot", 0.3278);
            SmartServo.setSmartPos(hardwareMap,"outtakeRight", 0.18);
            SmartServo.setSmartPos(hardwareMap,"outtakeLeft", 0.18);
            //SmartServo.setSmartPos(hardwareMap,"lid", 0.6);
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
            //SmartServo.setSmartPos(hardwareMap, "lid", 0.1);
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
        return wristLeft.getPosition() >= 0.4;
    }



    private void oneAndDoneUpdate() {
        //Last Instance Update
        gamepad2_y_LU = gamepad2.y;
        gamepad2_b_LU = gamepad2.b;
    }
}
