package io.viewpoint.moviedatabase.util

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback

private val diffUtil = object : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(
        oldItem: Any,
        newItem: Any
    ): Boolean = oldItem === newItem

    override fun areContentsTheSame(
        oldItem: Any,
        newItem: Any
    ): Boolean = oldItem == newItem
}

private val updateCallback = object : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
    override fun onMoved(fromPosition: Int, toPosition: Int) = Unit
    override fun onInserted(position: Int, count: Int) = Unit
    override fun onRemoved(position: Int, count: Int) = Unit
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> asyncPagingDataDiffer(): AsyncPagingDataDiffer<T> =
    AsyncPagingDataDiffer(
        diffCallback = diffUtil,
        updateCallback = updateCallback
    ) as AsyncPagingDataDiffer<T>