@file:Suppress("unused")

package com.rookia.android.androidutils.extensions

import android.os.Parcel
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java.lang.ref.WeakReference
import java.text.Normalizer
import java.util.*


fun String.normalizedString(): String {
    val normalized: String = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("[^\\p{ASCII}]".toRegex(), "").trim { it <= ' ' }.toLowerCase(Locale.getDefault())
}

fun <T> WeakReference<T>.safe(body : T.() -> Unit) {
    this.get()?.body()
}

class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this) { it?.let { mediator.value = it } }
    return mediator
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun Parcel.supportReadBoolean(): Boolean =
    this.readByte().toInt() != 0

fun Parcel.supportWriteBoolean(value: Boolean) {
    val byte: Byte = if(value) 1 else 0
    this.writeByte(byte)
}

