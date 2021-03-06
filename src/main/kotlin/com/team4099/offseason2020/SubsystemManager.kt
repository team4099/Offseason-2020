package com.team4099.offseason2020

import com.team4099.lib.logging.HelixLogger
import com.team4099.lib.loop.Loop
import com.team4099.lib.subsystem.ServoMotorSubsystem
import com.team4099.lib.subsystem.Subsystem
import com.team4099.offseason2020.config.DashboardConfigurator

object SubsystemManager {
    private val subsystems = mutableListOf<Subsystem>()
    val enabledLoop = object : Loop {
        override fun onStart(timestamp: Double) {
            subsystems.forEach { it.onStart(timestamp) }
        }

        override fun onLoop(timestamp: Double, dT: Double) {
            subsystems.forEach { it.onLoop(timestamp, dT) }
            outputTelemetry()
        }

        override fun onStop(timestamp: Double) {
            subsystems.forEach { it.onStop(timestamp) }
        }
    }

    val disabledLoop = object : Loop {
        override fun onStart(timestamp: Double) {}

        override fun onLoop(timestamp: Double, dT: Double) {
            outputTelemetry()
        }

        override fun onStop(timestamp: Double) {}
    }

    /**
     * Registers a subsystem with the subsystem manager, including
     * it in the [enabledLoop].
     *
     * @param subsystem The subsystem object to register
     */
    fun register(subsystem: Subsystem) {
        subsystem.registerLogging()
        if (subsystem is ServoMotorSubsystem && Robot.tuningEnabled) DashboardConfigurator.registerSubsystem(subsystem)

        subsystems.add(subsystem)
    }

    /**
     * Registers multiple subsystems with the subsystem manager, including
     * them in the [enabledLoop].
     *
     * @param subsystemColl The subsystems to register
     */
    fun register(subsystemColl: Collection<Subsystem>) {
        subsystemColl.forEach { register(it) }
    }

    /**
     * Output telemetry from all subsystems.
     */
    fun outputTelemetry() {
        subsystems.forEach { it.outputTelemetry() }
        HelixLogger.saveLogs()
    }
}
