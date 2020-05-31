package com.homeappsco.juliancuartas.cleanarchitecturesoccer

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

object SchedulersProvider : ISchedulersProvider {

    override fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun computation(): Scheduler {

        return Schedulers.computation()
    }

    override fun single(): Scheduler {

        return Schedulers.single()
    }

    override fun trampoline(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun from(executor: Executor): Scheduler {
        return Schedulers.from(executor)
    }
}


interface ISchedulersProvider {
    fun mainThread(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
    fun single(): Scheduler
    fun trampoline(): Scheduler
    fun from(executor: Executor): Scheduler
}