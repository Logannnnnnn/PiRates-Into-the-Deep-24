package org.firstinspires.ftc.teamcode.auto;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous
public class autoBasketTwo extends LinearOpMode {
    @Override
    public void runOpMode() {

        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight"); // input 0
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft"); // input 1
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight"); // input 2
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");  // input 3

        DcMotor motorOuttakeLiftOne = hardwareMap.dcMotor.get("motorOuttakeLiftOne"); // ex input 0
        DcMotor motorOuttakeLiftTwo = hardwareMap.dcMotor.get("motorOuttakeLiftTwo"); // ex input 1
        DcMotor motorIntakeExtend = hardwareMap.dcMotor.get("motorIntakeExtend"); // ex input 2


        Servo servoOuttakeBucket = hardwareMap.servo.get("servoOuttakeBucket"); // servo ex 0
        Servo servoOuttakeClaw = hardwareMap.servo.get("servoOuttakeClaw"); // servo ex 1

        Servo servoIntakeClaw = hardwareMap.servo.get("servoIntakeClaw"); // servo 0
        Servo servoIntakeRotate = hardwareMap.servo.get("servoIntakeRotate"); // servo 1
        Servo servoIntakeClawSpin = hardwareMap.servo.get("servoIntakeClawSpin");// servo 2

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        motorFrontLeft.setZeroPowerBehavior(BRAKE);
        motorFrontRight.setZeroPowerBehavior(BRAKE);
        motorBackLeft.setZeroPowerBehavior(BRAKE);
        motorBackRight.setZeroPowerBehavior(BRAKE);


        motorOuttakeLiftOne.setZeroPowerBehavior(BRAKE);
        motorOuttakeLiftTwo.setZeroPowerBehavior(BRAKE);

        motorIntakeExtend.setZeroPowerBehavior(BRAKE);


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            servoIntakeRotate.setPosition(0.6);

            motorFrontLeft.setPower(-0.4);
            motorBackLeft.setPower(-0.4);
            motorFrontRight.setPower(-0.4);
            motorBackRight.setPower(-0.4);

            sleep(1455);

            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);

            motorOuttakeLiftOne.setPower(-0.4);
            motorOuttakeLiftTwo.setPower(0.4);

            sleep(4850);

            motorOuttakeLiftOne.setZeroPowerBehavior(BRAKE);
            motorOuttakeLiftTwo.setZeroPowerBehavior(BRAKE);

            motorFrontLeft.setPower(-0.4);
            motorBackLeft.setPower(-0.4);
            motorFrontRight.setPower(0.4);
            motorBackRight.setPower(0.4);

            sleep(345);

            servoOuttakeBucket.setPosition(0);

            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);

            sleep(1955);

            servoOuttakeBucket.setPosition(0.8);

            motorFrontLeft.setPower(0.4);
            motorBackLeft.setPower(0.4);
            motorFrontRight.setPower(0.4);
            motorBackRight.setPower(0.4);

            sleep(450);

            motorFrontLeft.setPower(-0.4);
            motorBackLeft.setPower(-0.4);
            motorFrontRight.setPower(0.4);
            motorBackRight.setPower(0.4);

            sleep(505);

            motorFrontLeft.setPower(-0.4);
            motorBackLeft.setPower(0.4);
            motorFrontRight.setPower(0.4);
            motorBackRight.setPower(-0.4);

            sleep(1175);

            servoIntakeRotate.setPosition(0.4);

            motorOuttakeLiftOne.setPower(0.4);
            motorOuttakeLiftTwo.setPower(-0.4);

            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);

            motorIntakeExtend.setPower(-0.6);

            sleep(2850);

            servoIntakeRotate.setPosition(0.05);

            sleep(250);

            servoIntakeClawSpin.setPosition(0.65);

            sleep(300);

            servoIntakeClaw.setPosition(1);

            sleep(400);

            motorFrontLeft.setPower(0.4);
            motorBackLeft.setPower(0.4);
            motorFrontRight.setPower(0.4);
            motorBackRight.setPower(0.4);

            sleep(325);

            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);

            servoIntakeClaw.setPosition(0.15);

            sleep(400);

            servoIntakeRotate.setPosition(0.55);

            sleep(450);

            servoIntakeClawSpin.setPosition(1);

            sleep(300);

            motorIntakeExtend.setPower(0.6);

            sleep(2700);

            motorIntakeExtend.setPower(0);

            servoIntakeClaw.setPosition(0.85);

            sleep(350);

            motorFrontLeft.setPower(-0.4);
            motorBackLeft.setPower(-0.4);
            motorFrontRight.setPower(0 - 0.4);
            motorBackRight.setPower(-0.4);

            sleep(450);

            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);

            motorOuttakeLiftOne.setPower(-0.4);
            motorOuttakeLiftTwo.setPower(0.4);

            sleep(4850);

            motorOuttakeLiftOne.setZeroPowerBehavior(BRAKE);
            motorOuttakeLiftTwo.setZeroPowerBehavior(BRAKE);


            sleep(350);

            motorFrontLeft.setPower(0.4);
            motorBackLeft.setPower(0.4);
            motorFrontRight.setPower(-0.4);
            motorBackRight.setPower(-0.4);

            sleep(350);

            servoOuttakeBucket.setPosition(0);

            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);


            motorFrontLeft.setZeroPowerBehavior(BRAKE);
            motorFrontRight.setZeroPowerBehavior(BRAKE);
            motorBackLeft.setZeroPowerBehavior(BRAKE);
            motorBackRight.setZeroPowerBehavior(BRAKE);
            sleep(8000000);

        }
    }
}