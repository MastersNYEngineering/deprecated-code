/*
    # # # # # # # # # # # # # # # # # # 
    # Masters School Robotics         #
    # Written by Matthew Nappo        #
    # GitHub: @xoreo                  #
    #                                 #
    # Class TriangleDrive             #
    # # # # # # # # # # # # # # # # # # 
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

@TeleOp(name="Main: Triangle Drive", group="Iterative Opmode")
public class TriangleDrive extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor w0 = null;
    private DcMotor w1 = null;
    private DcMotor w2 = null;
    private Servo deploy_servo = null;
    private double max_speed;

    public DcMotor init_motor(String id) {
        DcMotor m = null;
        m = hardwareMap.get(DcMotor.class, id);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        m.setDirection(DcMotor.Direction.FORWARD);
        return m;
    }

    @Override
    public void init() {
        deploy_servo = hardwareMap.get(Servo.class, "deploy");
        
        // max_speed = 1;
        max_speed = 0.5;
        // max_speed = 0.125;
        init_motor("w0");
        init_motor("w1");
        init_motor("w2");
    }

    double[] move() {
        double x_left_joy = gamepad1.left_stick_x;
        double y_left_joy = gamepad1.left_stick_y;
        
        double phi_joy = Math.atan2(y_left_joy, x_left_joy);
        
        double x_left_joy_sq = Math.pow(x_left_joy, 2);
        double y_left_joy_sq = Math.pow(y_left_joy, 2);
        
        double r_joy = Math.sqrt(x_left_joy_sq + y_left_joy_sq);
        
        double speed = max_speed * r_joy;
        
        double alpha_1 = Math.PI / 2;
        double alpha_2 = 7 * Math.PI / 6;
        double alpha_3 = 11 * Math.PI / 6;
        
        double theta_1 = alpha_1 - phi_joy;
        double theta_2 = alpha_2 - phi_joy;
        double theta_3 = alpha_3 - phi_joy;
        
        double w0_power = speed * Math.sin(theta_1);
        double w1_power = speed * Math.sin(theta_2);
        double w2_power = speed * Math.sin(theta_3);
        
        telemetry.addData("w0_power", w0_power);
        telemetry.addData("w1_power", w1_power);
        telemetry.addData("w2_power", w2_power);
        
        double[] speeds = {
            w0_power,
            w1_power,
            w2_power
        };

        return speeds;
    }

    double turn() {
        double x_right_joy = gamepad1.right_stick_x;
        double speed = Range.clip(x_right_joy, -1.0, 1.0) * max_speed;

        return speed;
    }

    void deploy_marker() {
        if (gamepad1.a) {
            deploy_servo.setPosition(100);
        }
    }


    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        deploy_servo.setPosition(0);
        runtime.reset();
    }

    @Override
    public void loop() {
        double[] move = move();
        double turn = turn();
        w0.setPower(move[0] + turn);
        w1.setPower(move[1] + turn);
        w2.setPower(move[2] + turn);

        deploy_marker();

        telemetry.addData("Run Time", runtime.toString());
    }

    @Override
    public void stop() {
        w0 = null;
        w1 = null;
        w2 = null;
    }
}