package org.firstinspires.ftc.teamcode.versionCode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "No Lift TeleOp Meet 0 Final")
public class NoLift_20231013_Final_TeleOp_Code_Meet_00 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("motorFL");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("motorBL");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("motorFR");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("motorBR");

        Servo droneServo = hardwareMap.servo.get("drone");
        Servo wristServo = hardwareMap.servo.get("wrist");
        Servo clawServo = hardwareMap.servo.get("claw");

        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        droneServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setDirection(Servo.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");

        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB backward
        imu.initialize(parameters);

        droneServo.scaleRange(0, 1);
        droneServo.setPosition(0.85);

        wristServo.scaleRange(0, 1);
        wristServo.setPosition(0);
        double wristTargetPos = wristServo.getPosition();
        double openClaw = 0;
        double closeClaw = 0.5;



        waitForStart();

        while (opModeIsActive()) {
            //gamepad 1 - drive base
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.x) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = ((rotY + rotX + rx) / denominator);
            double backLeftPower = ((rotY - rotX + rx) / denominator);
            double frontRightPower = ((rotY - rotX - rx) / denominator);
            double backRightPower = ((rotY + rotX - rx) / denominator);

            frontLeftMotor.setPower(frontLeftPower*0.65);
            backLeftMotor.setPower(backLeftPower*0.65);
            frontRightMotor.setPower(frontRightPower*0.65);
            backRightMotor.setPower(backRightPower*0.65);

            if (gamepad2.left_bumper){
                wristTargetPos += 0.1;
                wristServo.setPosition(wristTargetPos);
            } else if (gamepad2.right_bumper){
                wristTargetPos -= 0.1;
                wristServo.setPosition(wristTargetPos);
            }

            if (gamepad2.dpad_up) {
                clawServo.setPosition(closeClaw);
            }
            if (gamepad2.dpad_down) {
                clawServo.setPosition(openClaw);
            }


            //drone launcher code

            double droneServoPosition = droneServo.getPosition();

            if (gamepad2.a) {
                droneServo.setPosition(1);
                sleep(1500);
                droneServo.setPosition(0.85);
            }

            // ADDED CODE - sends info about current servo position to driver station
            telemetry.addData("Drone Servo Position: ", droneServoPosition);

            telemetry.addData("Wrist Position: ", wristServo.getPosition());
            telemetry.addData("Wrist Target: ", wristTargetPos);

            telemetry.addData("Claw Position: ", clawServo.getPosition());
            //telemetry.addData("Claw Target: ", claw);

            telemetry.update();
        }
    }
}