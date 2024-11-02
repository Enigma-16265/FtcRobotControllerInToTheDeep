package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SoundThing {
    //make constructor
    //make varyables
    //do sound things
    // Declare OpMode members.
    private boolean goldFound;      // Sound file present flags
    private boolean silverFound;
    int silverSoundID;
    int goldSoundID;

    public SoundThing(HardwareMap hardwareMap) {

        // Determine Resource IDs for sounds built into the RC application.
        silverSoundID = hardwareMap.appContext.getResources().getIdentifier("silver", "raw", hardwareMap.appContext.getPackageName());
        goldSoundID = hardwareMap.appContext.getResources().getIdentifier("gold", "raw", hardwareMap.appContext.getPackageName());

        // Determine if sound resources are found.
        // Note: Preloading is NOT required, but it's a good way to verify all your sounds are available before you run.
        if (goldSoundID != 0)
            goldFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, goldSoundID);

        if (silverSoundID != 0)
            silverFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, silverSoundID);

    }

    public void runSounds (HardwareMap hardwareMap) {
        if (silverFound) {
            // say Silver each time gamepad X is pressed (This sound is a resource)
            SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, silverSoundID);
        }
        if (goldFound) {
            // say Gold each time gamepad B is pressed  (This sound is a resource)
            SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, goldSoundID);
        }
    }
}

