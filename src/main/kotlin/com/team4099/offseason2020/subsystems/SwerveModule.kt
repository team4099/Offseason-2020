package com.team4099.offseason2020.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.team4099.lib.config.ServoMotorSubsystemConfig
import com.team4099.lib.hardware.CTREServoMotorHardware
import com.team4099.lib.motorcontroller.CTREMotorControllerFactory
import com.team4099.lib.subsystem.ServoMotorSubsystem
import com.team4099.offseason2020.config.Constants

class SwerveModule(ids: Pair<Int, Int>, config: ServoMotorSubsystemConfig): ServoMotorSubsystem(
    config,
    CTREServoMotorHardware(
        ids.second,
        listOf(),
        feedbackDevice = FeedbackDevice.CTRE_MagEncoder_Absolute
    )
) {
    private enum class SwerveDriveState {
        VELOCITY_PID,
        OPEN_LOOP
    }

    private var driveState = SwerveDriveState.VELOCITY_PID
    private val driveMotor = CTREMotorControllerFactory.createDefaultTalonFX(ids.first)

    var rotationPositionTarget = 0.0
        set(value) {
            positionSetpointMotionProfile = value
            field = value
        }

    var driveVelocitySetpoint = 0.0
        set(value) {
            driveState = SwerveDriveState.VELOCITY_PID
            field = value
        }
    var driveAccel = 0.0

    var driveOpenLoop = 0.0
        set(value) {
            driveState = SwerveDriveState.OPEN_LOOP
            field = value
        }

    init {
        driveMotor.config_kP(0, Constants.Drive.SWERVE_DRIVE_VELOCITY_PID_GAINS.kP)
        driveMotor.config_kI(0, Constants.Drive.SWERVE_DRIVE_VELOCITY_PID_GAINS.kI)
        driveMotor.config_kD(0, Constants.Drive.SWERVE_DRIVE_VELOCITY_PID_GAINS.kD)
        driveMotor.config_kF(0, Constants.Drive.SWERVE_DRIVE_VELOCITY_PID_GAINS.kF)
    }

    override fun onStart(timestamp: Double) {
        super.onStart(timestamp)
        rotationPositionTarget = 0.0
        driveVelocitySetpoint = 0.0
    }

    override fun onLoop(timestamp: Double, dT: Double) {
        super.onLoop(timestamp, dT)
        when (driveState) {
            SwerveDriveState.VELOCITY_PID -> {
                val feedforwardVolts = Constants.Drive.Characterization.KV * driveVelocitySetpoint +
                    Constants.Drive.Characterization.KA * driveAccel + Constants.Drive.Characterization.KS
                driveMotor.set(
                    ControlMode.Velocity,
                    metersPerSecondToNative(driveVelocitySetpoint),
                    DemandType.ArbitraryFeedForward,
                    feedforwardVolts / 12.0
                )
            }
            SwerveDriveState.OPEN_LOOP -> driveMotor.set(ControlMode.PercentOutput, driveOpenLoop)
        }
    }

    override fun onStop(timestamp: Double) {
        super.onStop(timestamp)
        rotationPositionTarget = 0.0
        driveVelocitySetpoint = 0.0
    }

    private fun nativeToMeters(nativeUnits: Int): Double {
        return (nativeUnits / Constants.Drive.ENCODER_TICKS_PER_REV) * Constants.Drive.WHEEL_DIAMETER_METERS * Math.PI
    }

    private fun metersToNative(meters: Double): Double {
        return (meters * Constants.Drive.ENCODER_TICKS_PER_REV) / (Constants.Drive.WHEEL_DIAMETER_METERS * Math.PI)
    }

    private fun rpmToMetersPerSecond(rpm: Double): Double {
        return (rpm) / 60 * Math.PI * Constants.Drive.WHEEL_DIAMETER_METERS
    }

    private fun nativeToMetersPerSecond(nativeUnits: Int): Double {
        return nativeToMeters(nativeUnits) * 10
    }

    private fun metersPerSecondToNative(meters: Double): Double {
        return metersToNative(meters) / 10
    }

    private fun metersToRotations(meters: Double): Double {
        return meters / (Constants.Drive.WHEEL_DIAMETER_METERS * Math.PI)
    }

    private fun metersPerSecondToRpm(metersPerSecond: Double): Double {
        return metersToRotations(metersPerSecond) * 60
    }
}