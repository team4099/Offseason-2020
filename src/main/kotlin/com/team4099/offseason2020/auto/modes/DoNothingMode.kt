package com.team4099.offseason2020.auto.modes

import com.team4099.lib.auto.AutoMode
import com.team4099.offseason2020.config.Constants

class DoNothingMode : AutoMode(0.0, Constants.Autonomous.AUTON_DT) {
    override fun routine() {}

    override fun done() {}
}
