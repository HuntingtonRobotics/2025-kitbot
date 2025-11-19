package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import org.ejml.equation.Variable;
import frc.robot.pid;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.config.AbsoluteEncoderConfig;
import edu.wpi.first.wpilibj.XboxController;




public class encoder {
    private final SparkMax motor = new SparkMax(30, MotorType.kBrushless);
    private final PIDController pidController = new PIDController(0.1, 0.1, 0.1);
    final RelativeEncoder encoder = motor.getEncoder();
    double position;
    double output;

    public void SetupForTurnOnce(){
        encoder.setPosition(0); //resets encoder
        //pidController.setTolerance(0.1); //sets the allowed error for pid
    }
    
    public void turnNum() {
        double position = encoder.getPosition(); //gets position of encoder
        if (position >= 1) {
            motor.set(0); //sets the speed to zero if we reached num rotations
            return;
        }
        motor.set(0.1); //turns the motor the determined speed
    }
}
