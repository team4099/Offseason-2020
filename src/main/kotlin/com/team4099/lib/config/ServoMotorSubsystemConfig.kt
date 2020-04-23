package com.team4099.lib.config

import com.team4099.lib.motorcontroller.CTREMotorControllerFactory

/**
 * The configuration of a [com.team4099.lib.subsystem.ServoMotorSubsystem]
 */
data class ServoMotorSubsystemConfig(
    val name: String,
    val unitsName: String,
    val positionPIDGains: PIDGains,
    val velocityPIDGains: PIDGains,
    val homePosition: Double,
    val motionConstraints: ServoMotorSubsystemMotionConstraints,
    val ticksPerUnitDistance: Double, // encoderResolution / unitsPerEncoderRev
    val brakeMode: Boolean = false
)
