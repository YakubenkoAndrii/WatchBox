package com.sample.project.watchbox.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseViewModel : ViewModel() {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }
    private val onNextStub: (Any) -> Unit = {}
    private val onErrorStub: (Throwable) -> Unit = {}
    private val onCompleteStub: () -> Unit = {}

    protected fun <T : Any> Single<T>.subBy(
        onError: (Throwable) -> Unit = onErrorStub,
        onSuccess: (T) -> Unit = onNextStub
    ) {
        async().subscribeBy(onError, onSuccess).autoDispose()
    }

    protected fun <T : Any> Observable<T>.subBy(
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ) {
        async().subscribeBy(onError, onComplete, onNext).autoDispose()
    }

    protected fun Completable.subBy(
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub
    ) {
        async().subscribeBy(onError, onComplete).autoDispose()
    }

    private fun <T : Any> Single<T>.async(): Single<T> {
        return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun <T : Any> Observable<T>.async(): Observable<T> {
        return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun Completable.async(): Completable {
        return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun Disposable.autoDispose() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.dispose()
    }
}
