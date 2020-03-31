package com.team4099.offseason2020.config

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
        const val MAX_VEL_METERS_PER_SEC = 4.0
        const val SLOW_VEL_METERS_PER_SEC = 0.66
        const val MAX_ACCEL_METERS_PER_SEC_SQ = 2.0
        const val SLOW_ACCEL_METERS_PER_SEC_SQ = 2.0

        const val CENTRIPETAL_ACCEL_METERS_PER_SEC_SQ = 1.0

        object Characterization {
            const val LEFT_KV_FORWARD = 2.67
            const val RIGHT_KV_FORWARD = 2.67

            const val LEFT_KA_FORWARD = 0.101
            const val RIGHT_KA_FORWARD = 0.114

            const val LEFT_KS_FORWARD = 0.0371
            const val RIGHT_KS_FORWARD = 0.0408

            const val LEFT_KV_REVERSE = 2.63
            const val RIGHT_KV_REVERSE = 2.63

            const val LEFT_KA_REVERSE = 0.196
            const val RIGHT_KA_REVERSE = 0.216

            const val LEFT_KS_REVERSE = -0.0893
            const val RIGHT_KS_REVERSE = -0.0803
        }
    }
}
