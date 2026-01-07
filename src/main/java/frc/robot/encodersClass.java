package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.lang.Object;
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
import com.ctre.phoenix6.hardware.core.CoreCANcoder;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.jni.CtreJniWrapper;

public class encodersClass extends Command {

    private final SparkMax motor = new SparkMax(30, MotorType.kBrushless);
    // private final PIDController pidController = new PIDController(0.1, 0.1, 0.1);
    CANcoder encoder = new CANcoder(0);
    double position;
    double startPosition = 0;
    boolean motorRunning = false;
    boolean aPressed = false;
    double ticks = 0;
    double allowedError = 0.001;
    private final pid m_pid = new pid();
    private final CommandXboxController controller = new CommandXboxController(0);
    public final XboxController m_driverController = new XboxController(0);

    public void SetupForTurnOnce() {
    }

    public void turnNum(double rotations) {
        aPressed = controller.a().getAsBoolean();
        if (aPressed || motorRunning) {
            position = encoder.getPosition().getValueAsDouble() - startPosition;
            System.out.println(m_pid.PID(rotations - position, ticks / 20));
            motor.set(m_pid.PID(rotations - position, ticks / 20));
            ticks++;
            if (Math.abs(position) >= rotations - allowedError) {
                motor.set(0);
                motorRunning = false;
            } else {
                motorRunning = true;
            }
        } else {
            motorRunning = false;
            startPosition = encoder.getPosition().getValueAsDouble();
        }
    }

    
    public void rot(double rotations){

        aPressed = controller.a().getAsBoolean();
        if (aPressed || motorRunning) {
            motor.set(1);
            if(encoder.getPosition().getValueAsDouble()>=0.9){
                position++;
            }
            if (Math.abs(position)>=rotations){
                motor.set(0);
                motorRunning = false;
            }else{
                motorRunning = true;
            }
        }
        }
}