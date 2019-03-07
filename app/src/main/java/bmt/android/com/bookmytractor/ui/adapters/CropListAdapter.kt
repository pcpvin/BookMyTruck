package bmt.android.com.bookmytractor.ui.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import bmt.android.com.bookmytractor.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.crop_list_content.view.*
import java.util.*

class CropListAdapter(val items: ArrayList<String>, val context: Context, val btnlistener: BtnClickListener) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        var mClickListener: BtnClickListener? = null
    }

    private val TAG = CropListAdapter::class.java.getSimpleName()
    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.crop_list_content, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        mClickListener = btnlistener
        holder.cropName.setText(items.get(position))
        holder.cropListLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                if (mClickListener != null)
                    mClickListener?.onBtnClick(position, holder.cropListLayout)
            }
        })
    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val cropName = view.cropList!!
    val cropListLayout = view.cropListCard!!


}

interface BtnClickListener {
    fun onBtnClick(position: Int, cardView: CardView)
}