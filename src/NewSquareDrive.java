/*
    # # # # # # # # # # # # # # # # # # 
    # Masters School Robotics         #
    # Written by Matthew Nappo        #
    # GitHub: @xoreo                  #
    #                                 #
    # Class NewSquareDrive            #
    # # # # # # # # # # # # # # # # # # 
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

@TeleOp(name="Main: New Square Drive", group="Iterative Opmode")
public class NewSquareDrive extends OpMode {

    String NAME_deploy_servo = "marker";
    String NAME_claw = "claw";
    String NAME_lift_rotate = "claw_rotate";
    String NAME_lift_0 = "drive_claw_left";
    String NAME_lift_1 = "drive_claw_right";
    String NAME_lock_arm = "arm_lock";

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor w0 = null;
    private DcMotor w1 = null;
    private DcMotor w2 = null;
    private DcMotor w3 = null;

    private DcMotor lift_rotate = null;
    private Servo lift_0 = null;
    private Servo lift_1 = null;
    
    private Servo deploy_servo = null;
    private Servo claw = null;
    private Servo lock_arm = null;
    
    private double max_speed;

    private DcMotor init_motor(String id) {
        DcMotor m = null;
        m = hardwareMap.get(DcMotor.class, id);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        m.setDirection(DcMotor.Direction.REVERSE);
        return m;
    }

    private Servo init_servo(String id) {
        Servo s = null;
        s = hardwareMap.get(Servo.class, id);
        s.setDirection(Servo.Direction.FORWARD);
        return s;
    }

    @Override
    public void init() {
        // max_speed = 1;
        // max_speed = 0.5;
        max_speed = 0.125;

        w0 = init_motor("w0");
        w1 = init_motor("w1");
        w2 = init_motor("w2");
        w3 = init_motor("w3");

        deploy_servo = init_servo(NAME_deploy_servo);
        lift_rotate = init_motor(NAME_lift_rotate);
        claw = init_servo(NAME_claw);
        lift_0 = init_servo(NAME_lift_0);
        lift_1 = init_servo(NAME_lift_1);
        lock_arm = init_servo(NAME_lock_arm);
    }

    double[] move() {
        double x_left_joy = gamepad1.left_stick_x;
        double y_left_joy = gamepad1.left_stick_y;
        
        double phi_joy = Math.atan2(y_left_joy, x_left_joy);
        
        double x_left_joy_sq = Math.pow(x_left_joy, 2);
        double y_left_joy_sq = Math.pow(y_left_joy, 2);
        
        double r_joy = Math.sqrt(x_left_joy_sq + y_left_joy_sq);
        
        double speed = max_speed * r_joy;
        
        double alpha_1 = Math.PI / 4;
        double alpha_2 = 3 * Math.PI / 4;
        double alpha_3 = 5 * Math.PI / 4;
        double alpha_4 = 7 * Math.PI / 4;
        
        double theta_1 = alpha_1 - phi_joy;
        double theta_2 = alpha_2 - phi_joy;
        double theta_3 = alpha_3 - phi_joy;
        double theta_4 = alpha_4 - phi_joy;
        
        double w0_power = speed * Math.sin(theta_1);
        double w1_power = speed * Math.sin(theta_2);
        double w2_power = speed * Math.sin(theta_3);
        double w3_power = speed * Math.sin(theta_4);
        
        telemetry.addData("w0_power", w0_power);
        telemetry.addData("w1_power", w1_power);
        telemetry.addData("w2_power", w2_power);
        telemetry.addData("w3_power", w3_power);
        
        double[] speeds = {
            w0_power,
            w1_power,
            w2_power,
            w3_power
        };

        return speeds;
    }

    double turn() {
        double x_right_joy = gamepad1.right_stick_x;
        double speed = Range.clip(x_right_joy, -1.0, 1.0) * max_speed;

        return speed;
    }
    
    boolean marker_bool = false;
    void deploy_marker() {
        double current_position = deploy_servo.getPosition();
        if (gamepad1.y) {
            if (marker_bool == true) {
                if (current_position == 1) {
                    deploy_servo.setPosition(-1);
                }
                marker_bool = false;
            } else if (marker_bool == false) {
                if (current_position == -1) {
                    deploy_servo.setPosition(1);
                }
                marker_bool = true;
            }
            
        }
    }
    

    // I DONT EVEN NEED THIS FUNCTION, IT WILL HAPPEN AT THE START OF EVERYTHING, INCLUDING AUTONOMOUS
    boolean arm_func_bool = false;
    void lock_arm_func() {
        double current_position = lock_arm.getPosition();
        telemetry.addData("ARM LOCK POSITION", current_position);
        if (gamepad1.x) {
            if (arm_func_bool == true) {
                if (current_position == 1) {
                    lock_arm.setPosition(-1);
                }
                arm_func_bool = false;
            } else if (arm_func_bool == false) {
                if (current_position == 0) {
                    lock_arm.setPosition(1);
                }
                arm_func_bool = true;
            }
            
        }
    }
    
    void move_claw() {
        double current_position = claw.getPosition();
        if (gamepad1.b) {
            claw.setPosition(1);
        }
        if (gamepad1.a) {
            claw.setPosition(-1);
        }
    }
    
    void rotate_lift() {
        // telemetry.addData("pressed", gamepad1.pressed(gamepad1.right_trigger));
        telemetry.addData("direct", gamepad1.right_trigger);
        
        double right = gamepad1.right_trigger;
        double left = gamepad1.left_trigger;
        if (right > 0) {
            lift_rotate.setPower(right);
        } else {
            lift_rotate.setPower(0);
        }
        
        if (left > 0) {
            lift_rotate.setPower(-left);
        } else {
            lift_rotate.setPower(0);
        }
    }

            /*
                For driving the lift, I need to find out a way to do this. Maybe i wire the motor into the servo port but deal with it 
                as if its a dcmotor class
            */

            
    void drive_lift() {
        if (gamepad1.right_bumper) {
            lift_0.setPosition(1);
            lift_1.setPosition(1);
        }
        if (gamepad1.left_bumper) {
            lift_0.setPosition(-1);
            lift_1.setPosition(-1);
        }
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        deploy_servo.setPosition(0);
        lock_arm.setPosition(0);
        runtime.reset();
    }

    @Override
    public void loop() {
        double[] move = move();
        double turn = turn();
        w0.setPower(move[0] + turn);
        w1.setPower(move[1] + turn);
        w2.setPower(move[2] + turn);
        w3.setPower(move[3] + turn);

        deploy_marker();
        rotate_lift();
        move_claw();
        lock_arm_func();

        // drive_lift();
        telemetry.addData("SERVO", deploy_servo.getPosition());
        telemetry.addData("Run Time", runtime.toString());
    }

    @Override
    public void stop() {
        w0 = null;
        w1 = null;
        w2 = null;
        w3 = null;
    }
}