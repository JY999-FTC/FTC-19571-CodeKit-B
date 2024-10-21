package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.concurrent.TimeUnit;

@TeleOp
@Config
public class CameraAlgorithm extends LinearOpMode {
    public static int exposure = 10;
    public static int gain = 10;
    @Override
    public void runOpMode() throws InterruptedException {
        BotVision bv = new BotVision();
        ColorIsolationPipeline p = new ColorIsolationPipeline();
        bv.init(hardwareMap, p, "Webcam 1");
        while (!bv.inited){}

        waitForStart();
        while (opModeIsActive()) {
            bv.webcam.getExposureControl().setMode(ExposureControl.Mode.Manual);
            bv.webcam.getGainControl().setGain(gain);
            bv.webcam.getExposureControl().setExposure(exposure, TimeUnit.MILLISECONDS);
        }

    }
}
