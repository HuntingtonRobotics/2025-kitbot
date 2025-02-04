package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HopperSubsystem extends SubsystemBase {
    private final WPI_TalonSRX hopper = new WPI_TalonSRX(14);

    public void releaseCoral(double output) {
        hopper.set(output);
    }
}
