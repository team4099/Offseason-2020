package com.team4099.lib.auto

/**
 * Action to wait for a given amount of time To use this Action, call
 * runAction(new WaitAction(your_time))
 */
class WaitAction(private val timeToWait: Double) : Action {
    private var startTime = 0.0

    override fun isFinished(timestamp: Double): Boolean {
        return timestamp - startTime >= timeToWait
    }

    override fun onLoop(timestamp: Double, dT: Double) {}

    override fun onStop(timestamp: Double) {}

    override fun onStart(timestamp: Double) {
        startTime = timestamp
    }
}
