package com.sample.project.watchbox.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class RecyclerViewHelper<H : RecyclerView.ViewHolder>(
    recyclerView: RecyclerView,
    var adapter: RecyclerView.Adapter<H>,
    orientationHorizontal: Boolean = false
) {

    init {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            if (orientationHorizontal) {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
    }
}

class BindingViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
