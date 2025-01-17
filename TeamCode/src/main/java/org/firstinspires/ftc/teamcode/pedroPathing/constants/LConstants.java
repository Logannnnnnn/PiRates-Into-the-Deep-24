package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.constants.OTOSConstants;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class LConstants {
    static {
        OTOSConstants.useCorrectedOTOSClass = false;
        OTOSConstants.hardwareMapName = "sensor_otos";
        OTOSConstants.linearUnit = DistanceUnit.INCH;
        OTOSConstants.angleUnit = AngleUnit.DEGREES;
        OTOSConstants.offset = new SparkFunOTOS.Pose2D(5.875, 3.875, Math.PI / 2);
        OTOSConstants.linearScalar = 1.0;
        OTOSConstants.angularScalar = 1.0;
    }
}



