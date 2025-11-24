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
import edu.wpi.first.networktables.GenericEntry;
import frc.robot.encodersClass;
import java.io.Console;

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
  private final DifferentialDrive m_robotDrive;
  private final XboxController m_driverController = new XboxController(0);
  private GenericEntry m_maxSpeed;
  private final WPI_TalonSRX hopper = new WPI_TalonSRX(14);
  private final encodersClass m_encodersClass = new encodersClass();

  public Robot() {

    backRight.follow(frontRight);
    backLeft.follow(frontLeft);    
    m_robotDrive = new DifferentialDrive(frontLeft::set, frontRight::set);

    SendableRegistry.addChild(m_robotDrive, frontLeft);
    SendableRegistry.addChild(m_robotDrive, backLeft);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    frontRight.setInverted(true);
    backRight.setInverted(true);

    //encoder.SetupForTurnOnce();

    m_maxSpeed =
    Shuffleboard.getTab("Configuration")
        .add("Max Speed", 1)
        .withWidget("Number Slider")
        .withPosition(1, 1)
        .withSize(2, 1)
        .getEntry();

    m_robotDrive.setMaxOutput(m_maxSpeed.getDouble(1.0));
  }

  @Override
  public void teleopPeriodic() {
    //hopper.set(-m_driverController.getRightY());
    //if (m_driverController().getAButtonPressed()){
      m_encodersClass.turnNum(2.0);
    //}
    // Drive with split arcade drive.
    // That means that the Y axis of the left stick moves forward and backward,
    //  and the X of the right stick turns left and right.
    
    //the get left for x and y value are both backwards on the controller
    //m_robotDrive.arcadeDrive(-m_driverController.getLeftY(), -m_driverController.getLeftX());
    //System.out.println(encoder1.getEncoder(0,1));
  }
}
