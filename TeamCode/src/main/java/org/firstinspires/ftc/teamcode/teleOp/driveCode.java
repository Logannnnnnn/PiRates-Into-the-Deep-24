package org.firstinspires.ftc.teamcode.teleOp;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class driveCode extends LinearOpMode {
    @Override
    public void runOpMode() {

        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight"); // input 0
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight"); // input 1
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft"); // input 2
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");  // input 3

        DcMotor motorOuttakeLiftOne = hardwareMap.dcMotor.get("motorOuttakeLiftOne"); // ex input 0
        DcMotor motorOuttakeLiftTwo = hardwareMap.dcMotor.get("motorOuttakeLiftTwo"); // ex input 1
        DcMotor motorIntakeExtendOne = hardwareMap.dcMotor.get("motorIntakeExtendOne"); // ex input 2
        DcMotor motorIntakeExtendTwo = hardwareMap.dcMotor.get("motorIntakeExtendTwo"); // ex input 3

        Servo servoIntakeGrab = hardwareMap.servo.get("servoIntakeGrab"); // servo ex 0
        Servo servoIntakeRotate = hardwareMap.servo.get("servoIntakeRotate"); // servo ex 1
        Servo servoOuttakeRotateOne = hardwareMap.servo.get("servoOuttakeRotateOne"); // servo 0
        Servo servoOuttakeRotateTwo = hardwareMap.servo.get("servoOuttakeRotateTwo"); // servo 1
        Servo servoOuttakeGrab = hardwareMap.servo.get("servoOuttakeGrab"); // servo 2
        Servo servoOuttakeSpin = hardwareMap.servo.get("servoOuttakeSpin"); // servo 3

        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motorFrontLeft.setZeroPowerBehavior(BRAKE);
        motorFrontRight.setZeroPowerBehavior(BRAKE);
        motorBackLeft.setZeroPowerBehavior(BRAKE);
        motorBackRight.setZeroPowerBehavior(BRAKE);

        motorOuttakeLiftOne.setZeroPowerBehavior(BRAKE);
        motorOuttakeLiftTwo.setZeroPowerBehavior(BRAKE);
        motorIntakeExtendOne.setZeroPowerBehavior(BRAKE);
        motorIntakeExtendTwo.setZeroPowerBehavior(BRAKE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);


            double extenderPowerOut = (gamepad2.right_trigger);
            if (gamepad2.right_trigger > 0.01) {
                motorIntakeExtendOne.setPower(extenderPowerOut);
                motorIntakeExtendTwo.setPower(extenderPowerOut);
            } else {
                motorIntakeExtendOne.setPower(0);
                motorIntakeExtendTwo.setPower(0);
            }
            double extenderPowerIn = (gamepad2.left_trigger);
            if (gamepad2.left_trigger > 0.01) {
                motorIntakeExtendOne.setPower(-extenderPowerIn);
                motorIntakeExtendTwo.setPower(-extenderPowerIn);
            } else {
                motorIntakeExtendOne.setPower(0);
                motorIntakeExtendTwo.setPower(0);
            }

            if (gamepad2.left_bumper) {
                while (gamepad2.left_bumper) {
                    double position = servoIntakeRotate.getPosition();
                    servoIntakeRotate.setPosition(position - 0.05);
                }
            } else {
                servoIntakeRotate.setPosition(0);
            }

            if (gamepad2.right_bumper) {
                while (gamepad2.right_bumper) {
                    double position = servoIntakeRotate.getPosition();
                    servoIntakeRotate.setPosition(position + 0.05);
                }
            } else {
                servoIntakeRotate.setPosition(0);
            }


            double OuttakeLiftPower = gamepad2.left_stick_y;
            if (gamepad2.right_stick_y > 0.1 || gamepad2.right_stick_y < -0.1) {
                motorOuttakeLiftOne.setPower(OuttakeLiftPower);
                motorOuttakeLiftTwo.setPower(OuttakeLiftPower);
            } else {
                motorOuttakeLiftOne.setPower(0);
                motorOuttakeLiftTwo.setPower(0);
            }

            if (gamepad2.dpad_up) {
                while (gamepad2.dpad_up) {
                    double position = servoOuttakeRotateOne.getPosition();
                    double positionTwo = servoOuttakeRotateTwo.getPosition();
                    servoOuttakeRotateOne.setPosition(position + 0.05);
                    servoOuttakeRotateTwo.setPosition(positionTwo + 0.05);
                }
            } else {
                servoOuttakeRotateOne.setPosition(0);
                servoOuttakeRotateTwo.setPosition(0);
            }

            if (gamepad2.dpad_up) {
                while (gamepad2.dpad_down) {
                    double position = servoOuttakeRotateOne.getPosition();
                    double positionTwo = servoOuttakeRotateTwo.getPosition();
                    servoOuttakeRotateOne.setPosition(position - 0.05);
                    servoOuttakeRotateTwo.setPosition(positionTwo - 0.05);
                }
            } else {
                servoOuttakeRotateOne.setPosition(0);
                servoOuttakeRotateTwo.setPosition(0);
            }
            

            if (gamepad2.dpad_left) {
                servoOuttakeGrab.setPosition(0);
            }
            if (gamepad2.dpad_right) {
                servoOuttakeGrab.setPosition(1);
            }
            if (gamepad2.a) {
                servoOuttakeSpin.setPosition(0);
            }
            if (gamepad2.y) {
                servoOuttakeSpin.setPosition(1);
            }
            if (gamepad2.x) {
                servoIntakeGrab.setPosition(1);
            }
            if (gamepad2.b) {
                servoIntakeGrab.setPosition(0);
            }
        }
    }
}