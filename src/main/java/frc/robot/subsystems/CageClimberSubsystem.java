package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CageClimberSubsystem extends SubsystemBase {
    private WPI_TalonSRX motor = new WPI_TalonSRX(-1);

    public Command engage() {
        return this.runOnce(() -> motor.set(1.0));
    }
    
}
