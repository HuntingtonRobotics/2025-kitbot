package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;


public class encodersClass {
    private final SparkMax motor = new SparkMax(30, MotorType.kBrushless);
    //private final PIDController pidController = new PIDController(0.1, 0.1, 0.1);
    final RelativeEncoder encoder = motor.getEncoder();
    double position;
    double output;

    public void SetupForTurnOnce(){
        encoder.setPosition(0); //resets encoder
        //pidController.setTolerance(0.1); //sets the allowed error for pid
    }
    
    public void turnNum(double rotations) {
        double position = encoder.getPosition(); //gets position of encoder
        if (position >= rotations){
            motor.set(0); //sets the speed to zero if we reached num rotations
            return;
        }
        motor.set(0.1); //turns the motor the determined speed
    }
}
