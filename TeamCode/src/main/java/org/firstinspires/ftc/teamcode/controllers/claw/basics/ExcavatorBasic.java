package org.firstinspires.ftc.teamcode.controllers.claw.basics;

import static org.firstinspires.ftc.teamcode.odometry.control.DriveConstants.MOTOR_VELO_PID;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.odometry.control.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot_components.MotorComponentsClaw;

@TeleOp(name = "Excavator Control", group = "Controllers")
public class ExcavatorBasic extends LinearOpMode {
    MotorComponentsClaw motors = new MotorComponentsClaw();
    ElapsedTime timer = new ElapsedTime();

    // Excavator weight: 362 g

    double command = 0.0;  // motor strength
    int tP = 5;            // set point
    double feedF = 0.362;  // feed-forward
    double integralSum;    // integral sum
    double Kp = 5;
    double Ki = 0.4;
    double Kd = 0.3;
    double lastError;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        motors.init(hardwareMap);

        waitForStart();
        drive.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, MOTOR_VELO_PID);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {
            armUp();
            armDown();
            command = controlLoop(tP, motors.cL.getCurrentPosition());
            runPower();
            intake();
            drive.setWeightedDrivePower(
                    new Pose2d(
                            gamepad1.left_stick_y,
                            gamepad1.left_stick_x,
                            gamepad1.right_stick_x
                    )
            );
        }
    }

    public void armUp() {
        if(gamepad1.y){
            tP = 60;
        }
    }

    public void armDown() {
        if(gamepad1.b){
            tP = 5;
        }
    }

    public double controlLoop(double SP, double PV) {
        timer.reset();
        double error = SP - PV;
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) * timer.seconds();
        lastError = error;

        timer.reset();

        return (error * Kp) + (integralSum * Ki) + (derivative * Kd) + (Math.cos(SP) * feedF);
    }

    public void runPower() {
        motors.cL.setPower(command);
        motors.cR.setPower(command);
    }

    public void intake(){
        if(gamepad1.left_bumper){
            motors.take.setPower(1);
        }
        if (gamepad1.right_bumper){
            motors.take.setPower(-1);
        }
        if(gamepad1.y){
            motors.take.setPower(0);
        }
    }
}
