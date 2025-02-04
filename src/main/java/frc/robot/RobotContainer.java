package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.*;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs
 * the motors with split
 * arcade steering and an Xbox controller.
 */
public class RobotContainer {

    private final CommandXboxController m_driverController = new CommandXboxController(0);
    private GenericEntry m_maxSpeed;

    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final Hopper hopper = new Hopper();

    public RobotContainer() {

        // m_maxSpeed = Shuffleboard.getTab("Configuration")
        // .add("Max Speed", 1)
        // .withWidget("Number Slider")
        // .withPosition(1, 1)
        // .withSize(2, 1)
        // .getEntry();
        // m_maxSpeed.getDouble(1.0 = 100%)(.5 = half speed or 50%)
        double percentPower = 1.0;
        driveSubsystem.setMaxOutput(percentPower);

        configureBindings();
    }

    private void configureBindings() {

        // Drive with split arcade drive.
        // That means that the Y axis of the left stick moves forward and backward,
        // and the X of the right stick turns left and right.

        // the get left for x and y value are both backwards on the controller
        driveSubsystem.setDefaultCommand(
                Commands.run(
                        () -> driveSubsystem.arcadeDrive(
                                -m_driverController.getLeftY(), -m_driverController.getRightX()),
                        driveSubsystem));

        hopper.releaseCoral(-m_driverController.getRightY());
    }

    public Command getAutonomousCommand() {
        return new PathPlannerAuto("Drive Square");
    }
}
