package org.firstinspires.ftc.teamcode.drive;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.util.Encoder;

import java.util.Arrays;
import java.util.List;

/*
 * Sample tracking wheel localizer implementation assuming the standard configuration:
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |
 *    |              |
 *    |              |
 *    \--------------/
 *
 */

/*
                                                                      .^JP?^
                                                                   :?PBGY!YGBP7:
                                                               .!5BB5!.     :757.
     ~J?JJJJJJJJJJJY!    :~              .?J~      :JJ:    .~YGBP?:    ^JP7:     .?GGJ^.          .?              ^7                   ?^
     YBB?~!!!!!!!!!!:    ~BP^            :BBY      ~BB~  ^PBGJ^    :7PBB5?PBB5!.   .~YGBP:        PB?            ^BB^                 7BB:
     ^77                 ~BBBP^           77^      ~BB~  ?BG   .~YGBP?:    .^JGBGJ~   .BB!       ?BBB7           ~!!^                !BBBB:
     .^:                 ~BB5PBP^         ^^.      ~BB~  ?BG   GB5~.           .!?^   .BB~      ^BBGBB!        .~~:^~^              ~BB^?BG.
     JBB.                ~BB~ !GBP^      .BBJ      ~BB~  ~5J   GB^                    .BB~     .GB5 JBB~      .GBB.!BB~            ^BB!  YBG.
     JBBY?JJJJJJJ7       ~BB~   !GBP^    .BBJ      ~BB~       .GB~                    .BB~     YBB.  YBB~     PBG.  YBB.          :BB7    5BP.
     JBB?!!!!!!!!~       ~BB~     !GBP^  .BBJ      ~BB~       .GB^         ^?PGGPGGP  .BB~    !BB!    YBB^   PBG.   .GB5         .GBJ     .PBP
     JBB.                ~BB~       !GBP^:BBJ      ~BB~       .BB!         ..    ?BG  .BB~   .BBY      YBB: 5BB:     ~BB7       .GBBP555555GBB5
     JBB.                ~BB~         !GBGBBJ      ~BB~        JGBP7:         :?PBP?   BB!   5BG.       5BG5BB:       JBB:     .PBP:.::::::.^GBJ
     JBB.                ~BB~           ~GBBJ      ~BB~          :75BG5!. :75BB5!.   ^JBB~   ..          5BBB:         GBG     PBG.           ..
     JBBP555555555557    ~BB~             ~GJ      ~BB~              ^?PBBBP7:   .75BGY~.  ^GG7           5B^          :BBY   5BG.            :PGJ
     .^^^^^^^^^^^^^~:    .^^.               .      .^^.                 .:   .~YGB57:      .::             :            .::  .::.              :::
                                                                          ^?GBP?^
                                                                       !5GGJ~.
                                                                       BB?
                                                                       .7~
                                                                       ~.
                                                                       5B7
                                                                        .:
 */
@Config
public class StandardTrackingWheelLocalizer extends ThreeTrackingWheelLocalizer {
    public static double TICKS_PER_REV = 2000;
    public static double WHEEL_RADIUS = 24.0 / 25.4; // mm to in
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    public static double LATERAL_DISTANCE = 14.456; // in; distance between the left and right wheels
    public static double FORWARD_OFFSET = -6; // in; offset of the lateral wheel
    public static double X_MULTIPLIER = ((90 / 90.91699286231696) + (90 / 91.06753951573171)) / 2;
    public static double Y_MULTIPLIER = ((90 / 90.7315510323255) + (90 / 90.77642052093479)) / 2;

    private Encoder leftEncoder, rightEncoder, frontEncoder;

    private List<Integer> lastEncPositions, lastEncVels;

    public StandardTrackingWheelLocalizer(HardwareMap hardwareMap, List<Integer> lastTrackingEncPositions, List<Integer> lastTrackingEncVels) {
        super(Arrays.asList(
                new Pose2d(0, LATERAL_DISTANCE / 2, 0), // left
                new Pose2d(0, -LATERAL_DISTANCE / 2, 0), // right
                new Pose2d(FORWARD_OFFSET, 0, Math.toRadians(90)) // front
        ));

        lastEncPositions = lastTrackingEncPositions;
        lastEncVels = lastTrackingEncVels;

        /*frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightBack"));

        leftEncoder.setDirection(Encoder.Direction.REVERSE);

         */

        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightBack"));

        rightEncoder.setDirection(Encoder.Direction.REVERSE);


        // TODO: reverse any encoders using Encoder.setDirection(Encoder.Direction.REVERSE)
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        int leftPos = leftEncoder.getCurrentPosition();
        int rightPos = rightEncoder.getCurrentPosition();
        int frontPos = frontEncoder.getCurrentPosition();

        lastEncPositions.clear();
        lastEncPositions.add(leftPos);
        lastEncPositions.add(rightPos);
        lastEncPositions.add(frontPos);

        return Arrays.asList(
                encoderTicksToInches(leftPos) * X_MULTIPLIER,
                encoderTicksToInches(rightPos) * X_MULTIPLIER,
                encoderTicksToInches(frontPos) * Y_MULTIPLIER
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        int leftVel = (int) leftEncoder.getCorrectedVelocity();
        int rightVel = (int) rightEncoder.getCorrectedVelocity();
        int frontVel = (int) frontEncoder.getCorrectedVelocity();

        lastEncVels.clear();
        lastEncVels.add(leftVel);
        lastEncVels.add(rightVel);
        lastEncVels.add(frontVel);

        return Arrays.asList(
                encoderTicksToInches(leftVel) * X_MULTIPLIER,
                encoderTicksToInches(rightVel) * X_MULTIPLIER,
                encoderTicksToInches(frontVel) * Y_MULTIPLIER
        );
    }
}
