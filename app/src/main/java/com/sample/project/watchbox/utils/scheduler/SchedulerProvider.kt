package com.sample.project.watchbox.utils.scheduler

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    fun schedulerIO(): Scheduler
    fun schedulerUI(): Scheduler
    fun schedulerComputation(): Scheduler
}