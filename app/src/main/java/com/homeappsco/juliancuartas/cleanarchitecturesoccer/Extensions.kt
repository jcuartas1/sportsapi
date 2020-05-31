package com.homeappsco.juliancuartas.cleanarchitecturesoccer

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import io.reactivex.Single
import io.reactivex.disposables.Disposable

fun <T> Single<T>.applyIoToMainSchedulers(schedulersProvider: SchedulersProvider): Single<T> =
subscribeOn(schedulersProvider.io()).observeOn(schedulersProvider.mainThread())


fun Disposable?.safeDispose() {if (this?.isDisposed != true) {this?.dispose()}}

@BindingAdapter("urlFan")
fun ImageView.loadUrlFanArt(url: String?){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.poster_placeholder)
        .into(this)
}

@BindingAdapter("url")
fun ImageView.loadUrl(url: String?){
    Glide.with(this)
        .load(url)
        .into(this)
}
