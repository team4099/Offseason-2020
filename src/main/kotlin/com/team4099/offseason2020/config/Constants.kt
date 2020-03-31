package com.team4099.offseason2020.config

import com.team4099.lib.config.PIDGains
import com.team4099.lib.config.ServoMotorSubsystemConfig
import com.team4099.lib.config.ServoMotorSubsystemMotionConstraints
import com.team4099.lib.geometry.Pose3d
import com.team4099.lib.motorcontroller.CTREMotorControllerFactory
import com.team4099.offseason2020.Robot
import edu.wpi.first.wpilibj.geometry.Rotation2d
import kotlin.math.tan

/**
 * Stores constants used by the robot.
 */
@SuppressWarnings("MagicNumber")
object Constants {
    object Universal {
        const val CTRE_CONFIG_TIMEOUT = 0
        const val EPSILON = 1E-9
    }

    object Tuning {
        const val TUNING_TOGGLE_PIN = 0
        val ROBOT_ID_PINS = 1..2

        enum class RobotName {
            COMPETITION,
            PRACTICE,
            MULE
        }

        val ROBOT_ID_MAP = mapOf<Int, RobotName>(
            0 to RobotName.COMPETITION,
            1 to RobotName.PRACTICE,
            2 to RobotName.MULE
        )
    }
}
