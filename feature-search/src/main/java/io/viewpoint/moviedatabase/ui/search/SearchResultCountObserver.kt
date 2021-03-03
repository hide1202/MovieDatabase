package io.viewpoint.moviedatabase.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

// PagingData 에서 데이터 개수를 직접 가져올 수 없는 문제의 workaround
class SearchResultCountObserver(
    adapter: RecyclerView.Adapter<*>
) : RecyclerView.AdapterDataObserver() {
    private val adapterRef = WeakReference(adapter)

    private val _currentItemCount = MutableLiveData(0)
    val currentItemCount: LiveData<Int>
        get() = _currentItemCount

    override fun onChanged() {
        val itemCount = adapterRef.get()?.itemCount ?: return
        _currentItemCount.value = itemCount
    }

    override fun onItemRangeRemoved(
        positionStart: Int,
        itemCount: Int
    ) {
        val current = _currentItemCount.value ?: 0
        _currentItemCount.value = current - itemCount
    }

    override fun onItemRangeInserted(
        positionStart: Int,
        itemCount: Int
    ) {
        val current = _currentItemCount.value ?: 0
        _currentItemCount.value = current + itemCount
    }
}