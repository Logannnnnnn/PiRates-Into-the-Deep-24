package org.firstinspires.ftc.teamcode.auto;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous
public class autoBasket extends LinearOpMode {
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

            sleep(375);

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