package com.team4099.lib.motorcontroller

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonFX

class LazyTalonFX(deviceNumber: Int) : TalonFX(deviceNumber) {
    private var lastSet = Double.NaN
    private var lastControlMode: ControlMode = ControlMode.Disabled

    override fun set(controlMode: ControlMode, value: Double) {
        if (value != lastSet || controlMode != lastControlMode) {
            lastSet = value
            lastControlMode = controlMode
            super.set(controlMode, value)
        }
    }
}
