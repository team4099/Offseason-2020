package com.team4099.offseason2020.config

import com.team4099.lib.config.PIDGains
import com.team4099.lib.config.ServoMotorSubsystemConfig
import com.team4099.lib.config.ServoMotorSubsystemMotionConstraints

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

    object Looper {
        const val LOOPER_DT = 0.02 // 50 Hz
    }

    object Autonomous {
        const val AUTON_DT = 0.02 // 50 Hz
        const val DEFAULT_MODE_NAME = "Shoot 3 + Forward"
        const val DEFAULT_DELAY = 0.0
    }

    object Joysticks {
        const val DRIVER_PORT = 0
        const val SHOTGUN_PORT = 1
    }

    object BrownoutDefender {
        const val COMPRESSOR_STOP_VOLTAGE = 10
        const val COMPRESSOR_STOP_CURRENT = 70
    }

    object Drive {
        // Pair(Drive Motor, Turning Motor)
        val MODULE_IDS = listOf(Pair(0, 1), Pair(2, 3), Pair(4, 5), Pair(6, 7))
        val BASE_MODULE_CONFIG = ServoMotorSubsystemConfig(
            "Swerve Module Template",
            "degrees",
            PIDGains(0, 0.0, 0.0, 0.0, 0.0, 0),
            PIDGains(1, 0.0, 0.0, 0.0, 0.0, 0),
            0.0,
            ServoMotorSubsystemMotionConstraints(Double.NaN, Double.NaN, 1.0, 1.0, 0),
            4096.0 / 360.0
        )

        val MODULE_CONFIGS = listOf(
            BASE_MODULE_CONFIG.copy(name = "FL Swerve Module"),
            BASE_MODULE_CONFIG.copy(name = "FR Swerve Module"),
            BASE_MODULE_CONFIG.copy(name = "BL Swerve Module"),
            BASE_MODULE_CONFIG.copy(name = "BR Swerve Module")
        )
        val SWERVE_DRIVE_VELOCITY_PID_GAINS = PIDGains(0, 0.0, 0.0, 0.0, 0.0, 0)

        const val MAX_VEL_METERS_PER_SEC = 4.0
        const val SLOW_VEL_METERS_PER_SEC = 0.66
        const val MAX_ACCEL_METERS_PER_SEC_SQ = 2.0
        const val SLOW_ACCEL_METERS_PER_SEC_SQ = 2.0

        const val CENTRIPETAL_ACCEL_METERS_PER_SEC_SQ = 1.0

        const val ENCODER_TICKS_PER_REV = 2048 / 0.08665966387
        const val WHEEL_DIAMETER_METERS = 0.1524

        object Characterization {
            const val KS = 0.0371
            const val KV = 2.63
            const val KA = 0.196
        }
    }
}
