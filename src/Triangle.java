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
public class Main extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor w0 = null;
    private DcMotor w1 = null;
    private DcMotor w2 = null;

    @Override
    public void init() {
        w0 = hardwareMap.get(DcMotor.class, "w0");
        w1 = hardwareMap.get(DcMotor.class, "w1");
        w2 = hardwareMap.get(DcMotor.class, "w2");

        w0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        w1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        w2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

        w0.setDirection(DcMotor.Direction.FORWARD);
        w1.setDirection(DcMotor.Direction.FORWARD);
        w2.setDirection(DcMotor.Direction.FORWARD);
        
        telemetry.addData("Init", "Motors");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        float x_joy = gamepad1.left_stick_x;
        float y_joy = gamepad1.left_stick_y;

        float phi_joy = Math.atan2(y_joy, x_joy);
        
        float x_joy_sq = Math.pow(x_joy, 2);
        float y_joy_sq = Math.pow(y_joy, 2);
        
        float r_joy = Math.sqrt(x_joy_sq + y_joy_sq);

        float max_speed = 1000;
        float speed = max_speed * r_joy;

        float alpha_1 = Math.PI / 2;
        float alpha_2 = 7 * Math.PI / 6;
        float alpha_3 = 11 * Math.PI / 6;

        float theta_1 = alpha_1 - phi_joy;
        float theta_2 = alpha_2 - phi_joy;
        float theta_3 = alpha_3 - phi_joy;

        float w0_power = speed * Math.sin(theta_1);
        float w1_power = speed * Math.sin(theta_2);
        float w2_power = speed * Math.sin(theta_3);

        // w0.setPower(w0_power);
        // w1.setPower(w1_power);
        // w2.setPower(w2_power);


        // double w0_power;
        // double w1_power;
        // double w2_power;

        // double drive = gamepad1.right_stick_x;
        // double turn = -gamepad1.left_stick_x;
        // w0_power = Range.clip(drive + turn, -1.0, 1.0);
        // w1_power = Range.clip(drive + turn, -1.0, 1.0);
        // w2_power = Range.clip(drive + turn, -1.0, 1.0);

        // w0.setPower(w0_power * 100);
        // w1.setPower(w1_power * 100);
        // w2.setPower(w2_power * 100);

        telemetry.addData("Run Time", runtime.toString());
    }

    @Override
    public void stop() {

    }
}
