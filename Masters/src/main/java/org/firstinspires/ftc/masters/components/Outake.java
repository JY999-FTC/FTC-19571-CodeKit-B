package org.firstinspires.ftc.masters.components;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Outake implements Component{

    private PIDController controller;
    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public int target = 0;

    private final double ticks_in_degree = 1425 / 180.0;

    public static double open = 0.7;
    public static double close = 0.45;
    public static double wristF = 0.9;
    public static double wristB = 0.7;

    private final Servo elbow1, elbow2, fingers;
    private final DcMotor extension1;
    private final DcMotor extension2;


    Telemetry telemetry;
    Init init;

    public Outake(Init init, Telemetry telemetry){

        this.init=init;
        this.telemetry=telemetry;
        this.extension1=init.getExtension1();
        this.extension2=init.getExtension2();
        this.elbow1=init.getElbow1();
        this.elbow2=init.getElbow2();
        this.fingers=init.getFingers();
        initializeHardware();

    }

    public void initializeHardware() {

        controller = new PIDController(p, i, d);
        controller.setPID(p, i, d);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        target = 0;

    }

    public void slidePower(int power) {
        extension1.setPower(power);
        extension2.setPower(power);
    }

    public void moveElbow(float pos) {
        elbow1.setPosition(pos);
        elbow2.setPosition(pos);
    }

    public void moveWrist(float mod) {
        elbow1.setPosition(.5 + mod);
        elbow1.setPosition(.5 - mod);
    }

    public void moveClaw(float pos) {
        fingers.setPosition(pos);
    }

    public void moveSlide(int target) {

        int rotatePos = extension1.getCurrentPosition();
        double pid = controller.calculate(rotatePos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
        double lift = pid + ff;

        extension1.setPower(lift);
        extension2.setPower(lift);

    }


}
