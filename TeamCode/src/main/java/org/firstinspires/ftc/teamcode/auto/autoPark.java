package org.firstinspires.ftc.teamcode.auto;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name = "autoPark", group = "AUTONOMOUS")
public class autoPark extends LinearOpMode {

    @Override
    public void runOpMode() {


        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft"); // input 0
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight"); // input 1
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");  // input 2
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight"); // input 3


        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);


        motorFrontLeft.setZeroPowerBehavior(BRAKE);
        motorFrontRight.setZeroPowerBehavior(BRAKE);
        motorBackLeft.setZeroPowerBehavior(BRAKE);
        motorBackRight.setZeroPowerBehavior(BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            motorFrontLeft.setPower(0.2);
            motorBackLeft.setPower(0.2);
            motorFrontRight.setPower(0.2);
            motorBackRight.setPower(0.2);
            sleep(40);
        }
    }
}
