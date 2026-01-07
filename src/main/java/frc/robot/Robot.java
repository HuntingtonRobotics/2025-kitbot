// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.networktables.GenericEntry;
import frc.robot.encodersClass;
import java.io.Console;
import java.lang.ModuleLayer.Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with split
 * arcade steering and an Xbox controller.
 */
public class Robot extends TimedRobot {

  private final WPI_TalonSRX frontRight = new WPI_TalonSRX(4);
  private final WPI_TalonSRX backRight = new WPI_TalonSRX(2);
  private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(3);
  private final WPI_TalonSRX backLeft = new WPI_TalonSRX(1);
  private final DifferentialDrive robotDrive;
  private final XboxController controller = new XboxController(0);
  private GenericEntry maxSpeed;
  private final WPI_TalonSRX hopper = new WPI_TalonSRX(14);
  private final encodersClass encodersClass = new encodersClass();
  private final pid robotPID = new pid();
  private final LimelightCamera limelight1 = new LimelightCamera(1, RotationsPerSecond.of(0.75).in(RadiansPerSecond));

  public Robot() {

    backRight.follow(frontRight);
    backLeft.follow(frontLeft);    
    robotDrive = new DifferentialDrive(frontLeft::set, frontRight::set);

    SendableRegistry.addChild(robotDrive, frontLeft);
    SendableRegistry.addChild(robotDrive, backLeft);
  }



  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    frontRight.setInverted(true);
    backRight.setInverted(true);
    encodersClass.SetupForTurnOnce();


    //encoder.SetupForTurnOnce();

    maxSpeed =
    Shuffleboard.getTab("Configuration")
        .add("Max Speed", 1)
        .withWidget("Number Slider")
        .withPosition(1, 1)
        .withSize(2, 1)
        .getEntry();

    robotDrive.setMaxOutput(maxSpeed.getDouble(1.0));
  }

  @Override
  public void teleopPeriodic() {
    double yMove = -controller.getLeftY();
    double rot = -controller.getLeftX();
    
    encodersClass.rot(1);
    if (controller.getAButton()) {
      // Do limelight stuff
      final double limelightRot = limelight1.aimProportional();
      rot = limelightRot;
      final double limelightForward = limelight1.rangeProportional();
      yMove = limelightForward;
    }

    robotDrive.arcadeDrive(yMove, rot);
  }
}