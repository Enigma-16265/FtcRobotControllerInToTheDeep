package org.firstinspires.ftc.teamcode.Mantas.FunStuffs;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SoundThing {
    //make constructor
    //make variables
    //do sound things
    // Declare OpMode members.
    private boolean goldFound;      // Sound file present flags
    private boolean silverFound;
    private boolean happyFound;
    int silverSoundID;
    int goldSoundID;
    int happySoundID;
    HardwareMap hardwareMap;

    public SoundThing(HardwareMap hardwareMap) {

        // Determine Resource IDs for sounds built into the RC application.
        silverSoundID = hardwareMap.appContext.getResources().getIdentifier("silver", "raw", hardwareMap.appContext.getPackageName());
        goldSoundID = hardwareMap.appContext.getResources().getIdentifier("gold", "raw", hardwareMap.appContext.getPackageName());
        happySoundID = hardwareMap.appContext.getResources().getIdentifier("happy", "raw", hardwareMap.appContext.getPackageName());
        // Determine if sound resources are found.
        // Note: Preloading is NOT required, but it's a good way to verify all your sounds are available before you run.
        if (goldSoundID != 0)
            goldFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, goldSoundID);

        if (silverSoundID != 0)
            silverFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, silverSoundID);

        if (happySoundID != 0)
            happyFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, happySoundID);

        this.hardwareMap = hardwareMap;
    }

    public void runSounds () {
        if (silverFound) {
            SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, silverSoundID);
        }
        if (goldFound) {
            SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, goldSoundID);
        }
        if (happyFound) {
            SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, happySoundID);
        }
    }
}

