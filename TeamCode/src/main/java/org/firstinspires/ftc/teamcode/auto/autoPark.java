package org.firstinspires.ftc.teamcode.auto;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.VisionPortal;


@Autonomous(name = "autoPark", group = "COMP")
@Disabled
public class autoPark extends LinearOpMode {

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double COUNTS_PER_MOTOR_REV = 384.5;    // eg: TETRIX Motor Encoder
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV);

    static final double LIFT_SPEED = 0.5;
    static final double EXTEND_SPEED = 0.5;
    private final ElapsedTime runtime = new ElapsedTime();
    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    Servo servoOuttakeClaw = hardwareMap.servo.get("servoOuttakeClaw"); // ex servo 0
    Servo servoOuttakeBucket = hardwareMap.servo.get("servoOuttakeBucket"); // ex servo 1
    Servo servoIntakeClaw = hardwareMap.servo.get("servoIntakeClaw"); // servo 0
    Servo servoIntakeRotate = hardwareMap.servo.get("servoIntakeRotate"); // servo 1
    Servo servoIntakeClawSpin = hardwareMap.servo.get("servoIntakeClawSpin");// servo 2
    /* Declare OpMode members. */

    private final DcMotor motorOuttakeLiftOne = null;
    private final DcMotor motorOuttakeLiftTwo = null;
    private final DcMotor motorIntakeExtend = null;

    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {


        // Initialize the drive system variables.
        DcMotor motorOuttakeLiftOne = hardwareMap.dcMotor.get("motorOuttakeLiftOne"); // ex input 0
        DcMotor motorOuttakeLiftTwo = hardwareMap.dcMotor.get("motorOuttakeLiftTwo"); // ex input 1
        DcMotor motorIntakeExtend = hardwareMap.dcMotor.get("motorIntakeExtend"); // ex input 2

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips

        motorOuttakeLiftOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorOuttakeLiftTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorIntakeExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorOuttakeLiftOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorOuttakeLiftTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorIntakeExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at", "%7d :%7d",
                motorOuttakeLiftOne.getCurrentPosition(),
                motorOuttakeLiftTwo.getCurrentPosition(),
                motorIntakeExtend.getCurrentPosition());
        telemetry.update();


        // Wait for the game to start (driver presses START)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(LIFT_SPEED, 15, 15, 15, 1.0);
        encoderDrive(EXTEND_SPEED, 15, 15, 15, 1.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.


    }

    @SuppressLint("DefaultLocale")
    public void encoderDrive(double speed,
                             double liftOneInches, double liftTwoInches, double extendInches,
                             double timeoutS) {


        int newLiftOneTarget;
        int newLiftTwoTarget;
        int newExtendTarget;


        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLiftOneTarget = motorOuttakeLiftOne.getCurrentPosition() + (int) (liftOneInches * COUNTS_PER_INCH);
            newLiftTwoTarget = motorOuttakeLiftTwo.getCurrentPosition() + (int) (liftTwoInches * COUNTS_PER_INCH);
            newExtendTarget = motorIntakeExtend.getCurrentPosition() + (int) (extendInches * COUNTS_PER_INCH);


            motorOuttakeLiftOne.setTargetPosition(newLiftOneTarget);
            motorOuttakeLiftTwo.setTargetPosition(newLiftTwoTarget);
            motorIntakeExtend.setTargetPosition(newExtendTarget);


            // Turn On RUN_TO_POSITION
            motorOuttakeLiftOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorOuttakeLiftTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorIntakeExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motorOuttakeLiftOne.setPower(Math.abs(speed));
            motorOuttakeLiftTwo.setPower(Math.abs(speed));
            motorIntakeExtend.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorOuttakeLiftOne.isBusy() && motorOuttakeLiftTwo.isBusy() && motorIntakeExtend.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d :%7d", newLiftOneTarget, newLiftTwoTarget, newExtendTarget);
                telemetry.addData("Currently at", " at %7d :%7d",
                        motorOuttakeLiftOne.getCurrentPosition(), motorOuttakeLiftTwo.getCurrentPosition(), motorIntakeExtend.getCurrentPosition());
                telemetry.update();


                sleep(20);


            }

            // Stop all motion;
            motorOuttakeLiftOne.setPower(0);
            motorOuttakeLiftTwo.setPower(0);
            motorIntakeExtend.setPower(0);


            // Turn off RUN_TO_POSITION
            motorOuttakeLiftOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorOuttakeLiftTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorIntakeExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(250);   // optional pause after each move.
        }
    }
}