package com.sample.project.watchbox.utils.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProviderImpl : SchedulerProvider {
    override fun schedulerIO(): Scheduler = Schedulers.io()
    override fun schedulerUI(): Scheduler = AndroidSchedulers.mainThread()
    override fun schedulerComputation(): Scheduler = Schedulers.computation()
}
