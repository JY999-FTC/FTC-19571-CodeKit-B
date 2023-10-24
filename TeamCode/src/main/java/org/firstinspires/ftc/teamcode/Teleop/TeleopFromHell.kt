package org.firstinspires.ftc.teamcode.Teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.DriveMethods
import org.firstinspires.ftc.teamcode.Variables
import org.firstinspires.ftc.teamcode.Variables.bottom
import org.firstinspires.ftc.teamcode.Variables.clawMotor
import org.firstinspires.ftc.teamcode.Variables.clawRotation
import org.firstinspires.ftc.teamcode.Variables.closedClaw
import org.firstinspires.ftc.teamcode.Variables.high
import org.firstinspires.ftc.teamcode.Variables.lMax
import org.firstinspires.ftc.teamcode.Variables.lMin
import org.firstinspires.ftc.teamcode.Variables.lPower
import org.firstinspires.ftc.teamcode.Variables.low
import org.firstinspires.ftc.teamcode.Variables.mid
import org.firstinspires.ftc.teamcode.Variables.motorBL
import org.firstinspires.ftc.teamcode.Variables.motorBR
import org.firstinspires.ftc.teamcode.Variables.motorFL
import org.firstinspires.ftc.teamcode.Variables.motorFR
import org.firstinspires.ftc.teamcode.Variables.motorSlideLeft
import org.firstinspires.ftc.teamcode.Variables.openClaw
import org.firstinspires.ftc.teamcode.Variables.rMax
import org.firstinspires.ftc.teamcode.Variables.rMin
import org.firstinspires.ftc.teamcode.Variables.rMotorL
import org.firstinspires.ftc.teamcode.Variables.rMotorR
import org.firstinspires.ftc.teamcode.Variables.rPower
import org.firstinspires.ftc.teamcode.Variables.slideRotationMotor
import org.firstinspires.ftc.teamcode.Variables.speed
import kotlin.math.exp

@TeleOp(name = "TeleopFromHell", group = "TeleopFinal")
class TeleopFromHell: DriveMethods() {
    override fun runOpMode() {
        initMotorsSecondBot() //init rack and pinion & wheel motors
        initSlideMotors() //init claw/slide motors

        when ((0..25).random()) {
            1 -> telemetry.addLine("good luck buddy")
            2 -> telemetry.addLine("\"what spectrum?\"")
            3 -> telemetry.addLine("MostlyOp >>> AHoT")
            4 -> telemetry.addLine("01101011 01101001 01101100 01101100 00100000 01111001 01101111 01110101 01110010 01110011 01100101 01101100 01100110")
            5 -> telemetry.addLine("I LOVE ULTRAKILL!!!!!!!!!!!!")
            6 -> telemetry.addLine("\"just hit clean build\"")
            7 -> telemetry.addLine("this match is gonna be ghoulish green")
            8 -> telemetry.addLine("we are so back")
            9 -> telemetry.addLine("ok so would you rather have a 1% chance of becoming a turkey everyday or...")
            10 -> telemetry.addLine("RIP damien mode 2022-2023")
            11 -> telemetry.addLine("ok programming, you have one week to do vision, rack and pinions that move at different speeds, a slide and claw with slippage, oh yeah and we need drive practice. Sounds good?")
            12 -> telemetry.addLine("\"who unqueued my song?\"")
            13 -> telemetry.addLine("at least we don't have a pushbot! (not confirmed, pushbot still possible)")
            14 -> telemetry.addLine("whoever set continuous rotation as the default is my #1 opp")
            15 -> telemetry.addLine("shoutout to Huy for being our insider <3")
            16 -> telemetry.addLine("why does jack always come to TR3? Is he stupid?")
            17 -> telemetry.addLine("Nick, I need you to sand this.")
            18 -> telemetry.addLine("I wish fame and good fortune upon Sachals bloodline")
            19 -> telemetry.addLine("-((2 / (1 + (exp(-(target - Pos) / speed)))) - 1) * max")
            20 -> telemetry.addLine("\"the grid system is stupid.\" *starts pointing at poles*")
            21 -> telemetry.addLine("James, how many orange cups have you eaten today?")
            22 -> telemetry.addLine("Tennisball is the newest sport sweeping the nation!")
            23 -> telemetry.addLine("our robot has been too big for the bounding box on 3 different occasions.")
            24 -> telemetry.addLine("build freezes are not real")
            25 -> telemetry.addLine("in Raytheon we trust")
        }
        telemetry.update()

        waitForStart()
        //set claw position into bounds
        clawRotation!!.position = 0.3
        clawMotor!!.position = closedClaw
        //reset motors
        motorSlideLeft!!.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorSlideLeft!!.mode = DcMotor.RunMode.RUN_USING_ENCODER;
        //set up variables
        var leftY: Double
        var leftYGPadTwo: Double
        var leftX: Double
        var rightX: Double
        var upOrDown = "Up"
        var slideTarget = bottom
        var targetHeight = 0
        var slidePos = 0
        var speedDiv = 1.66
        while (opModeIsActive()) {
            //set gamepad inputs
            leftY = (-gamepad1.left_stick_y).toDouble()
            leftYGPadTwo = (-gamepad2.left_stick_y).toDouble()
            leftX = gamepad1.left_stick_x.toDouble()
            rightX = gamepad1.right_stick_x.toDouble()

            //set motor speeds
            slideRotationMotor?.power = leftYGPadTwo //not confirmed, could be wrong direction
            motorFL?.power = (leftY + leftX + rightX) / speedDiv
            motorBL?.power = (leftY - leftX + rightX) / speedDiv
            motorFR?.power = (leftY - leftX - rightX) / speedDiv
            motorBR?.power = (leftY + leftX - rightX) / speedDiv

            //open/close claw
            if (gamepad2.b) {
                clawMotor!!.position = closedClaw
            }
            if (gamepad2.a) {
                clawMotor!!.position = openClaw
            }

            //raise/lower slide
            if (gamepad2.left_trigger >= 0.8) {
                if (targetHeight < 3) {
                    targetHeight++
                }
                sleep(150)
            }
            if (gamepad2.left_bumper) {
                if (targetHeight > 0) {
                    targetHeight--
                }
                sleep(150)
            }

            when (targetHeight) {
                0 -> {slideTarget = bottom}
                1 -> {slideTarget = low}
                2 -> {slideTarget = mid}
                3 -> {slideTarget = high}
            }

            slidePos = motorSlideLeft?.let { -(it.currentPosition) }!!
            speed = if (slideTarget < slidePos!!) {
                50
            } else {
                300
            }
            motorSlideLeft?.power = -((2 / (1 + (exp((-(slideTarget - slidePos) / speed).toDouble())))) - 1)

            //rack & pinion control
            if (gamepad2.right_bumper) {
                if (upOrDown == "Up") {
                    if (rMotorL?.currentPosition!! < lMax) {
                        rMotorL!!.power = lPower
                    }
                } else {
                    if (rMotorL?.currentPosition!! > lMin) {
                        rMotorL!!.power = -lPower
                    }
                }
            }
            if (gamepad2.right_trigger >= 0.5) {
                if (upOrDown == "Up") {
                    if (rMotorR?.currentPosition!! < rMax) {
                        rMotorR!!.power = rPower
                    }
                } else {
                    if (rMotorR?.currentPosition!! > rMin) {
                        rMotorR!!.power = -rPower
                    }
                }
            }
            if (gamepad2.x) {
                if (upOrDown == "Up") {
                    upOrDown == "Down"
                } else {
                    upOrDown == "Up"
                }
            }

            //claw stuff
            if (gamepad2.y) {
                //swap between intake and place claw rotation
            }
        }
    }
}