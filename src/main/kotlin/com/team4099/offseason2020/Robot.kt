package com.team4099.offseason2020

import com.team4099.lib.auto.AutoModeExecuter
import com.team4099.lib.logging.CrashTracker
import com.team4099.lib.logging.HelixEvents
import com.team4099.lib.logging.HelixLogger
import com.team4099.lib.loop.Looper
import com.team4099.offseason2020.auto.PathStore
import com.team4099.offseason2020.config.Constants
import com.team4099.offseason2020.config.DashboardConfigurator
import com.team4099.offseason2020.loops.FaultDetector
import com.team4099.offseason2020.loops.VoltageEstimator
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.RobotController
import edu.wpi.first.wpilibj.TimedRobot
import kotlin.math.pow

object Robot : TimedRobot() {
    private lateinit var autoModeExecuter: AutoModeExecuter

    private val disabledLooper = Looper("disabledLooper", Constants.Looper.LOOPER_DT)
    private val enabledLooper = Looper("enabledLooper", Constants.Looper.LOOPER_DT)

    //    private val tuningTogglePin = DigitalInput(Constants.Tuning.TUNING_TOGGLE_PIN)
    val tuningEnabled: Boolean
        get() = true

    var robotName: Constants.Tuning.RobotName

    init {
        // Determine what robot we're running on.
        val robotId = Constants.Tuning.ROBOT_ID_PINS.withIndex().map { (i, pin) ->
            if (DigitalInput(pin).get()) 0 else 2.0.pow(i).toInt()
        }.sum()
        robotName = Constants.Tuning.ROBOT_ID_MAP.getOrDefault(robotId, Constants.Tuning.RobotName.COMPETITION)

        PathStore // Invoke path store to initialize it and generate the contained trajectories

        HelixEvents.addEvent("ROBOT", "Robot Construction (running on $robotName)")
        HelixLogger.addSource("Battery Voltage", RobotController::getBatteryVoltage)

        HelixLogger.addSource("Enabled Looper dT") { enabledLooper.dt }
        HelixLogger.addSource("Disabled Looper dT") { disabledLooper.dt }
    }

    override fun robotInit() {
        try {
            HelixEvents.startLogging()

            // Register all subsystems
            // SubsystemManager.register(Drive)

            enabledLooper.register(SubsystemManager.enabledLoop)
            enabledLooper.register(FaultDetector)

            disabledLooper.register(SubsystemManager.disabledLoop)
            disabledLooper.register(VoltageEstimator)
            disabledLooper.register(FaultDetector)
            if (tuningEnabled) disabledLooper.register(DashboardConfigurator)

            // Must come after subsystem registration
            HelixLogger.createFile()
            DashboardConfigurator.initDashboard()
            HelixEvents.addEvent("ROBOT", "Robot Initialized")
        } catch (t: Throwable) {
            CrashTracker.logThrowableCrash("robotInit", t)
            throw t
        }
    }

    override fun disabledInit() {
        try {
            enabledLooper.stop() // end EnabledLooper
            disabledLooper.start() // start DisabledLooper

            HelixEvents.addEvent("ROBOT", "Robot Disabled")
        } catch (t: Throwable) {
            CrashTracker.logThrowableCrash("disabledInit", t)
            throw t
        }
    }

    override fun autonomousInit() {
        try {
            if (::autoModeExecuter.isInitialized) autoModeExecuter.stop()
//            Drive.zeroSensors()

            disabledLooper.stop() // end DisabledLooper
            enabledLooper.start() // start EnabledLooper

            autoModeExecuter = AutoModeExecuter(DashboardConfigurator.getSelectedAutoMode())
            autoModeExecuter.start()

            HelixEvents.addEvent("ROBOT", "Autonomous Enabled")
        } catch (t: Throwable) {
            CrashTracker.logThrowableCrash("autonomousInit", t)
            throw t
        }
    }

    override fun teleopInit() {
        try {
            if (::autoModeExecuter.isInitialized) autoModeExecuter.stop()
            disabledLooper.stop()
            enabledLooper.start()
            HelixEvents.addEvent("ROBOT", "Teleop Enabled")
        } catch (t: Throwable) {
            CrashTracker.logThrowableCrash("teleopInit", t)
            throw t
        }
    }

    override fun disabledPeriodic() {
        try {
            // outputAllToSmartDashboard()
        } catch (t: Throwable) {
            CrashTracker.logThrowableCrash("disabledPeriodic", t)
            throw t
        }
    }

    override fun autonomousPeriodic() {
        try {
            // outputAllToSmartDashboard()
        } catch (t: Throwable) {
            CrashTracker.logThrowableCrash("autonomousPeriodic", t)
            throw t
        }
    }

    override fun teleopPeriodic() {
        try {} catch (t: Throwable) {
            CrashTracker.logThrowableCrash("teleopPeriodic", t)
            throw t
        }
    }

    override fun testInit() {
        try {
            disabledLooper.stop()
            enabledLooper.start()
        } catch (t: Throwable) {
            CrashTracker.logThrowableCrash("testInit", t)
            throw t
        }
    }

    override fun testPeriodic() {}
}
