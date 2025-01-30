package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with split
 * arcade steering and an Xbox controller.
 */
public class RobotContainer {

    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(4);
    private final WPI_TalonSRX backRight = new WPI_TalonSRX(2);
    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(3);
    private final WPI_TalonSRX backLeft = new WPI_TalonSRX(1);
    private final DifferentialDrive m_robotDrive;
    private final CommandXboxController m_driverController = new CommandXboxController(0);
    private GenericEntry m_maxSpeed;
    private final WPI_TalonSRX hopper = new WPI_TalonSRX(14);
    private final DriveSubsystem driveSubsystem;

    public RobotContainer() {
        backRight.follow(frontRight);
        backLeft.follow(frontLeft);
        m_robotDrive = new DifferentialDrive(frontLeft::set, frontRight::set);
        driveSubsystem = new DriveSubsystem(m_robotDrive);

        SendableRegistry.addChild(m_robotDrive, frontLeft);
        SendableRegistry.addChild(m_robotDrive, backLeft);

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        frontRight.setInverted(true);
        backRight.setInverted(true);

        // m_maxSpeed = Shuffleboard.getTab("Configuration")
        //         .add("Max Speed", 1)
        //         .withWidget("Number Slider")
        //         .withPosition(1, 1)
        //         .withSize(2, 1)
        //         .getEntry();
        //m_maxSpeed.getDouble(1.0)
        double percentPower = 1.0;
        m_robotDrive.setMaxOutput(percentPower);

        configureBindings();
    }

    private void configureBindings() {
        hopper.set(-m_driverController.getRightY());

        // Drive with split arcade drive.
        // That means that the Y axis of the left stick moves forward and backward,
        // and the X of the right stick turns left and right.

        // the get left for x and y value are both backwards on the controller
        m_robotDrive.arcadeDrive(-m_driverController.getLeftY(), -m_driverController.getLeftX());
    }

    public Command getAutonomousCommand() {
        return new PathPlannerAuto("Drive Square");
    }
}
