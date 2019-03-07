package bmt.android.com.bookmytractor.helper

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import bmt.android.com.bookmytractor.utils.CommUtils

class InfiniteScrollListener(
        val func: () -> Unit,
        val layoutManager: NpaGridlayoutManager) : RecyclerView.OnScrollListener()
{

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 20
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    @SuppressLint("ShowToast")
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached
                Log.i("InfiniteScrollListener", "End reached")
                func()
                loading = true
            }
        }
    }

}