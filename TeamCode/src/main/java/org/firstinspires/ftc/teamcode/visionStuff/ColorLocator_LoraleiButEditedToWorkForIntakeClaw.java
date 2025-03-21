package org.firstinspires.ftc.teamcode.visionStuff;

import android.annotation.SuppressLint;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

import java.util.Arrays;
import java.util.List;
@Autonomous(name = "LocatorTest1", group = "Teleop")
public class ColorLocator_LoraleiButEditedToWorkForIntakeClaw extends LinearOpMode {
    public ColorLocator_LoraleiButEditedToWorkForIntakeClaw(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }
HardwareMap hardwareMap;
    public VisionPortal portal;
    public Point[] myBoxCorners;
    public double[] centerX = {16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265};
    public double[] centerY = {16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265,16265};
    public boolean seeBlueBlobs = false;
    public ColorBlobLocatorProcessor colorLocator;
    public int blobNum = 0;
    public boolean problem = false;
    public double blobs;
    public void Setup(){
        //WebcamName webcamName = hardwareMap.get(WebcamName.class,"Webcam 1");
        /* Build a "Color Locator" vision processor based on the ColorBlobLocatorProcessor class.
         * - Specify the color range you are looking for.  You can use a predefined color, or create you own color range
         *     .setTargetColorRange(ColorRange.BLUE)                      // use a predefined color match
         *       Available predefined colors are: RED, BLUE YELLOW GREEN
         *     .setTargetColorRange(new ColorRange(ColorSpace.YCrCb,      // or define your own color match
         *                                           new Scalar( 32, 176,  0),
         *                                           new Scalar(255, 255, 132)))
         *
         * - Focus the color locator by defining a RegionOfInterest (ROI) which you want to search.
         *     This can be the entire frame, or a sub-region defined using:
         *     1) standard image coordinates or 2) a normalized +/- 1.0 coordinate system.
         *     Use one form of the ImageRegion class to define the ROI.
         *         ImageRegion.entireFrame()
         *         ImageRegion.asImageCoordinates(50, 50,  150, 150)  100x100 pixel square near the upper left corner
         *         ImageRegion.asUnityCenterCoordinates(-0.5, 0.5, 0.5, -0.5)  50% width/height square centered on screen
         *
         * - Define which contours are included.
         *     You can get ALL the contours, or you can skip any contours that are completely inside another contour.
         *        .setContourMode(ColorBlobLocatorProcessor.ContourMode.ALL_FLATTENED_HIERARCHY)  // return all contours
         *        .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)            // exclude contours inside other contours
         *        note: EXTERNAL_ONLY helps to avoid bright reflection spots from breaking up areas of solid color.
         *
         * - turn the display of contours ON or OFF.  Turning this on helps debugging but takes up valuable CPU time.
         *        .setDrawContours(true)
         *
         * - include any pre-processing of the image or mask before looking for Blobs.
         *     There are some extra processing you can include to improve the formation of blobs.  Using these features requires
         *     an understanding of how they may effect the final blobs.  The "pixels" argument sets the NxN kernel size.
         *        .setBlurSize(int pixels)    Blurring an image helps to provide a smooth color transition between objects, and smoother contours.
         *                                    The higher the number of pixels, the more blurred the image becomes.
         *                                    Note:  Even "pixels" values will be incremented to satisfy the "odd number" requirement.
         *                                    Blurring too much may hide smaller features.  A "pixels" size of 5 is good for a 320x240 image.
         *        .setErodeSize(int pixels)   Erosion removes floating pixels and thin lines so that only substantive objects remain.
         *                                    Erosion can grow holes inside regions, and also shrink objects.
         *                                    "pixels" in the range of 2-4 are suitable for low res images.
         *        .setDilateSize(int pixels)  Dilation makes objects more visible by filling in small holes, making lines appear thicker,
         *                                    and making filled shapes appear larger. Dilation is useful for joining broken parts of an
         *                                    object, such as when removing noise from an image.
         *                                    "pixels" in the range of 2-4 are suitable for low res images.
         */
        colorLocator = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(ColorRange.RED)         // use a predefined color match
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
                .setRoi(ImageRegion.asUnityCenterCoordinates(-1, 1, 1, -1))  // search central 1/4 of camera view
                .setDrawContours(true)                        // Show contours on the Stream Preview
                .setBlurSize(5)                               // Smooth the transitions between different colors in image
                .build();

        /*
         * Build a vision portal to run the Color Locator process.
         *
         *  - Add the colorLocator process created above.
         *  - Set the desired video resolution.
         *      Since a high resolution will not improve this process, choose a lower resolution that is
         *      supported by your camera.  This will improve overall performance and reduce latency.
         *  - Choose your video source.  This may be
         *      .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))  .....   for a webcam
         *  or
         *      .setCamera(BuiltinCameraDirection.BACK)    ... for a Phone Camera
         */
        portal = new VisionPortal.Builder()
                .addProcessor(colorLocator)
                .setCameraResolution(new Size(640, 480))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        telemetry.setMsTransmissionInterval(50);   // Speed up telemetry updates, Just use for debugging.
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

    }
    public void colorFinder(){
        seeBlueBlobs = false;
        // Read the current list
        List<ColorBlobLocatorProcessor.Blob> blobs = colorLocator.getBlobs();

        /*
         * The list of Blobs can be filtered to remove unwanted Blobs.
         *   Note:  All contours will be still displayed on the Stream Preview, but only those that satisfy the filter
         *          conditions will remain in the current list of "blobs".  Multiple filters may be used.
         *
         * Use any of the following filters.
         *
         * ColorBlobLocatorProcessor.Util.filterByArea(minArea, maxArea, blobs);
         *   A Blob's area is the number of pixels contained within the Contour.  Filter out any that are too big or small.
         *   Start with a large range and then refine the range based on the likely size of the desired object in the viewfinder.
         *
         * ColorBlobLocatorProcessor.Util.filterByDensity(minDensity, maxDensity, blobs);
         *   A blob's density is an indication of how "full" the contour is.
         *   If you put a rubber band around the contour you would get the "Convex Hull" of the contour.
         *   The density is the ratio of Contour-area to Convex Hull-area.
         *
         * ColorBlobLocatorProcessor.Util.filterByAspectRatio(minAspect, maxAspect, blobs);
         *   A blob's Aspect ratio is the ratio of boxFit long side to short side.
         *   A perfect Square has an aspect ratio of 1.  All others are > 1
         */
        ColorBlobLocatorProcessor.Util.filterByArea(20, 300000000, blobs);  // filter out very small blobs.
        /*
         * The list of Blobs can be sorted using the same Blob attributes as listed above.
         * No more than one sort call should be made.  Sorting can use ascending or descending order.
         *     ColorBlobLocatorProcessor.Util.sortByArea(SortOrder.DESCENDING, blobs);      // Default
         *     ColorBlobLocatorProcessor.Util.sortByDensity(SortOrder.DESCENDING, blobs);
         *     ColorBlobLocatorProcessor.Util.sortByAspectRatio(SortOrder.DESCENDING, blobs);
         */

        telemetry.addLine(" Area Density Aspect  Center");

        // Display the size (area) and center location for each Blob.
        for (int i = 0; i < centerY.length; i++){
            centerX[i] = 16265;
            centerY[i] = 16265;
        }
        if (centerX[0] != 16265 || centerY[0] != 16265){
            problem = true;
        }
        blobNum = 0;
        for(ColorBlobLocatorProcessor.Blob b : blobs)
        {
            telemetry.addLine("we got all the way here");
            seeBlueBlobs = true;
            blobNum = blobNum + 1;
            RotatedRect boxFit = b.getBoxFit();
            // Display boxFit.points(), an array of the box's four (X, Y) corner points,
            // clockwise from top left corner.
            myBoxCorners = new Point[4];
            boxFit.points(myBoxCorners);
            telemetry.addLine(Arrays.toString(myBoxCorners));
            centerX[blobNum] = boxFit.center.x;
            centerY[blobNum] = boxFit.center.y;
            telemetry.addLine(String.valueOf(centerX));
            telemetry.addLine(String.valueOf(centerY[blobNum]));
        }
        colorLocator.getBlobs().removeAll(colorLocator.getBlobs());
        sleep(50);
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode()
    {
        Setup();
        // WARNING:  To be able to view the stream preview on the Driver Station, this code runs in INIT mode.
        while (opModeIsActive() || opModeInInit()) {
            colorFinder();
        }
    }
    @Override
    public void waitForStart(){
        super.waitForStart();
    }
}
