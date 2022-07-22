package com.sample.project.watchbox.ui.base

import androidx.lifecycle.ViewModel
import com.sample.project.watchbox.utils.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }
    private val onNextStub: (Any) -> Unit = {}
    private val onErrorStub: (Throwable) -> Unit = {}
    private val onCompleteStub: () -> Unit = {}

    /**
     * simplifying subscribe Single with auto adding schedulers and to CompositeDisposable
     */
    protected fun <T : Any> Single<T>.subBy(
        onError: (Throwable) -> Unit = onErrorStub,
        onSuccess: (T) -> Unit = onNextStub
    ) {
        async().subscribeBy(onError, onSuccess).autoDispose()
    }

    /**
     * simplifying subscribe Observable with auto adding schedulers and to CompositeDisposable
     */
    protected fun <T : Any> Observable<T>.subBy(
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ) {
        async().subscribeBy(onError, onComplete, onNext).autoDispose()
    }

    /**
     * simplifying subscribe Completable with auto adding schedulers and to CompositeDisposable
     */
    protected fun Completable.subBy(
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub
    ) {
        async().subscribeBy(onError, onComplete).autoDispose()
    }

    private fun <T : Any> Single<T>.async(): Single<T> {
        return subscribeOn(schedulerProvider.schedulerIO()).observeOn(schedulerProvider.schedulerUI())
    }

    private fun <T : Any> Observable<T>.async(): Observable<T> {
        return subscribeOn(schedulerProvider.schedulerIO()).observeOn(schedulerProvider.schedulerUI())
    }

    private fun Completable.async(): Completable {
        return subscribeOn(schedulerProvider.schedulerIO()).observeOn(schedulerProvider.schedulerUI())
    }

    private fun Disposable.autoDispose() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.dispose()
    }
}
