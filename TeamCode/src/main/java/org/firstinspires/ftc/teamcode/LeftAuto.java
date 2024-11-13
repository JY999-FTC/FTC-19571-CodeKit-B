package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Left-auto", group="auto")
public class LeftAuto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Bot robot = new Bot(this);

        robot.init(hardwareMap);

        waitForStart();

        //arm position in ticks
        //45 degrees=700
        //0=horizontal
        //extend pos is in inches(2 digits)

        //drives forward and plants a C4 on the referee's back
        robot.encoderDrive(1,19.4);
        robot.setArmPos(1045);
        robot.setExtendPos(13.94);
        robot.setArmPos(450);
        robot.setExtendPos(0.25);
        robot.setArmPos(25);
        //drives to the samples
        robot.encoderStrafe(1, 33);
        robot.encoderTurn(1,180);
        robot.encoderDrive(1,-37);
        robot.encoderStrafe(1, -18);
        //sample 1
        robot.encoderDrive(1, 10.3);
        robot.autoIntake(1.5);
        robot.encoderDrive(1, 25);
        robot.setExtendPos(0);
        robot.setArmPos(1150);
        robot.encoderTurn(1,42.5);

        robot.encoderDrive(1,8.5);

        robot.setArmPos(1000);
        robot.setExtendPos(-1550);
        robot.runIntakeForTime(2.5, -1);
        robot.setArmPos(1024);
        robot.setExtendPos(0);
        robot.setArmPos(0);

//        //sample2
//        robot.encoderDrive(1,-48);
//        robot.encoderTurn(1,-12);
//        robot.encoderStrafe(1,-12.2);
//        robot.encoderDrive(1,45);
//        //sample3
//        robot.encoderDrive(1,-45);
//        robot.encoderStrafe(1,-12);
//        robot.encoderDrive(1,43.5);
    }
}