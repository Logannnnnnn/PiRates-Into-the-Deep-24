package org.firstinspires.ftc.teamcode.auto;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

@Autonomous(name = "blueAutoOne", group = "BLUE")
@Disabled
public class blueAutoOne extends LinearOpMode {

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double COUNTS_PER_MOTOR_REV = 384.5;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159265);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    private final ElapsedTime runtime = new ElapsedTime();
    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    PredominantColorProcessor colorSensor = new PredominantColorProcessor.Builder()
            .setRoi(ImageRegion.asUnityCenterCoordinates(-0.1, 0.1, 0.1, -0.1))
            .setSwatches(
                    PredominantColorProcessor.Swatch.RED,
                    PredominantColorProcessor.Swatch.BLUE,
                    PredominantColorProcessor.Swatch.YELLOW,
                    PredominantColorProcessor.Swatch.BLACK,
                    PredominantColorProcessor.Swatch.WHITE)
            .build();
    /*
     * Build a vision portal to run the Color Sensor process.
     *
     *  - Add the colorSensor process created above.
     *  - Set the desired video resolution.
     *      Since a high resolution will not improve this process, choose a lower resolution that is
     *      supported by your camera.  This will improve overall performance and reduce latency.
     *  - Choose your video source.  This may be
     *      .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))  .....   for a webcam
     *  or
     *      .setCamera(BuiltinCameraDirection.BACK)    ... for a Phone Camera
     */
    VisionPortal portal = new VisionPortal.Builder()
            .addProcessor(colorSensor)
            .setCameraResolution(new Size(320, 240))
            .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
            .build();
    /* Declare OpMode members. */
    private DcMotor motorFrontLeft = null;
    private DcMotor motorBackLeft = null;
    private DcMotor motorFrontRight = null;
    private DcMotor motorBackRight = null;

    @Override
    public void runOpMode() {


        // Initialize the drive system variables.
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorFrontLeft");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at", "%7d :%7d",
                motorFrontLeft.getCurrentPosition(),
                motorBackLeft.getCurrentPosition(),
                motorFrontRight.getCurrentPosition(),
                motorBackRight.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED, 48, 48, 48, 48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED, -12, 12, 12, 12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -24, -24, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // pause to display final telemetry message.
    }

    @SuppressLint("DefaultLocale")
    public void encoderDrive(double speed,
                             double frontLeftInches, double backLeftInches, double backRightInches, double frontRightInches,
                             double timeoutS) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newBackRightTarget;
        int newFrontRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = motorFrontLeft.getCurrentPosition() + (int) (frontLeftInches * COUNTS_PER_INCH);
            newBackLeftTarget = motorBackLeft.getCurrentPosition() + (int) (backLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = motorBackRight.getCurrentPosition() + (int) (backRightInches * COUNTS_PER_INCH);
            newFrontRightTarget = motorFrontRight.getCurrentPosition() + (int) (frontRightInches * COUNTS_PER_INCH);
            motorFrontLeft.setTargetPosition(newFrontLeftTarget);
            motorBackLeft.setTargetPosition(newBackLeftTarget);
            motorBackRight.setTargetPosition(newBackRightTarget);
            motorFrontRight.setTargetPosition(newFrontRightTarget);


            // Turn On RUN_TO_POSITION
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motorFrontLeft.setPower(Math.abs(speed));
            motorBackLeft.setPower(Math.abs(speed));
            motorBackRight.setPower(Math.abs(speed));
            motorFrontRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorFrontLeft.isBusy() && motorBackLeft.isBusy() && motorBackRight.isBusy() && motorFrontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d :%7d", newFrontLeftTarget, newBackLeftTarget, newBackRightTarget, newFrontRightTarget);
                telemetry.addData("Currently at", " at %7d :%7d",
                        motorFrontLeft.getCurrentPosition(), motorBackLeft.getCurrentPosition(), motorBackRight.getCurrentPosition(), motorFrontRight.getCurrentPosition());
                telemetry.update();

                telemetry.addData("DS preview on/off", "3 dots, Camera Stream\n");

                // Request the most recent color analysis.
                // This will return the closest matching colorSwatch and the predominant RGB color.
                // Note: to take actions based on the detected color, simply use the colorSwatch in a comparison or switch.
                //  eg:
                //      if (result.closestSwatch == PredominantColorProcessor.Swatch.RED) {... some code  ...}
                PredominantColorProcessor.Result result = colorSensor.getAnalysis();

                // Display the Color Sensor result.
                telemetry.addData("Best Match:", result.closestSwatch);
                telemetry.addLine(String.format("R %3d, G %3d, B %3d", Color.red(result.rgb), Color.green(result.rgb), Color.blue(result.rgb)));
                telemetry.update();

                sleep(20);
            }

            // Stop all motion;
            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorBackRight.setPower(0);
            motorFrontRight.setPower(0);

            // Turn off RUN_TO_POSITION
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
}
