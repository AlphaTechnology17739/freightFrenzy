package org.firstinspires.ftc.teamcode.initial_tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.computer_vision.tensorflow.ObjectsDetected;
import org.firstinspires.ftc.teamcode.locomotion.hardware.MotorEncodersSetup;

@Autonomous(name = "Test Autonomous", group = "Autonomous")
public class InitialAutoTests extends LinearOpMode {
    MotorEncodersSetup robot = new MotorEncodersSetup(); // access robot components configuration

    @Override
    public void runOpMode() {
        robot.runOpMode();

        telemetry.addData(">", "Press Play to Begin");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                straightToTheHub();
            }
            telemetry.update();
        }
    }

    public void moveStorageUnit(){
        robot.moveRobot(500, 500,500,500, 1);
    }

    public void straightToTheHub(){
        if(ObjectsDetected.Dir) {
            robot.moveRobot(200, 200,0,0, 1);
        }
        else robot.moveRobot(0, 0, 200, 200, 1);
        sleep(5000);
        switch (ObjectsDetected.DuckBarcode){
            case 1:
                break;
            case 2:
                assert true;
                break;
            case 3:
                assert false;
                break;
            default:
        }
        pointToCarousel();
    }

    public void pointToCarousel(){
        robot.moveRobot(50, 50,-50,-50, 1);
        sleep(5000);
        goingToCarousel();
    }

    public void goingToCarousel(){
        robot.moveRobot(500, 500,500,500, 1);
        sleep(5000);
        walkingSideways();
    }

    // TODO: Knock the duck with carousel controller

    public void walkingSideways(){
        robot.moveRobot(-100, 100,100,-100, 1);
        sleep(5000);
        straightToTheDeposit();
    }

    public void straightToTheDeposit(){robot.moveRobot(100, 100,100,100, 1);}
}
