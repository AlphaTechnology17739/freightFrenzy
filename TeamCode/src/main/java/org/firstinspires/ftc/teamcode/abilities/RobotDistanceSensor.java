package org.firstinspires.ftc.teamcode.abilities;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotDistanceSensor extends OpMode{
    private DistanceSensor distSensor;
    public double valueDist;
    /*************************************************************************
    * distSensor - distance sensor                                           *
    * valueDist - distance value                                                 *
    * ************************************************************************/

    @Override
    public void init() { distSensor = hardwareMap.get(DistanceSensor.class, "sensorDist"); }

    @Override
    public void loop() { captureDistance(); }

    public void captureDistance() {
        valueDist = distSensor.getDistance(DistanceUnit.CM);
        telemetry.addData("Distance: ", valueDist);
        telemetry.update();
    }
}
