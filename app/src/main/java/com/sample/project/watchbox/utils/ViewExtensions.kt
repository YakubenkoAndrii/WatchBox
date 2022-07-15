package com.sample.project.watchbox.utils

import android.view.View
import androidx.core.view.isVisible

fun View.show(show: Boolean) {
    this.isVisible = show
}
