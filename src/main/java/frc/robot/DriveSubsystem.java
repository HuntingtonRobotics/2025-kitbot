package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.AnalogGyro;

public class DriveSubsystem extends SubsystemBase {
    DifferentialDrive m_RobotDrive;
    DifferentialDriveKinematics kinematics;

    public DriveSubsystem(DifferentialDrive robotDrive) {
        // All other subsystem initialization
        // ...
        m_RobotDrive = robotDrive;
        double trackWidthInches = 21.5;
        double trackWidthMeters = Units.inchesToMeters(trackWidthInches);
        kinematics = new DifferentialDriveKinematics(trackWidthMeters);

        // Load the RobotConfig from the GUI settings. You should probably
        // store this in your Constants file
        RobotConfig config;
        try {
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return;
        }

        // Configure AutoBuilder last
        AutoBuilder.configure(
                this::getPose, // Robot pose supplier
                this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
                this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT
                                                                      // RELATIVE ChassisSpeeds. Also optionally outputs
                                                                      // individual module feedforwards
                new PPLTVController(0.02), // PPLTVController is the built in path following controller for differential
                                           // drive trains
                config, // The robot configuration
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red
                    // alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                this // Reference to this subsystem to set requirements
        );
    }

    private Pose2d getPose() {
        return new Pose2d(new Translation2d(), new Rotation2d());
    }

    private void resetPose(Pose2d pose2d) {

    }

    private ChassisSpeeds getRobotRelativeSpeeds() {
        return kinematics.toChassisSpeeds(new DifferentialDriveWheelSpeeds(-0.3, -0.3));
    }

    private void driveRobotRelative(ChassisSpeeds speeds) {
        m_RobotDrive.arcadeDrive(speeds.vxMetersPerSecond, 0);
    }

    public void arcadeDrive(double forwardSpeed, double rotationSpeed) {
        m_RobotDrive.arcadeDrive(forwardSpeed, rotationSpeed);
    }

    public void stop() {
        m_RobotDrive.arcadeDrive(0, 0);
    }
}