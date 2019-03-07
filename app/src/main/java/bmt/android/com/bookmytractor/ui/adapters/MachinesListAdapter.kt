package bmt.android.com.bookmytractor.ui.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.model.MachinesListPojo.Machine
import kotlinx.android.synthetic.main.crop_list_content.view.*
import kotlinx.android.synthetic.main.machine_list_content.view.*

class MachinesListAdapter(val items: ArrayList<Machine>, val context: Context, val machineListClickListener: MachineListClickListener) : RecyclerView.Adapter<MachinesViewHolder>()
{

    companion object {
        var mClickListener: MachineListClickListener? = null
    }

    private val TAG = MachinesListAdapter::class.java!!.getSimpleName()
    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachinesViewHolder {
        return MachinesViewHolder(LayoutInflater.from(context).inflate(R.layout.machine_list_content, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: MachinesViewHolder, position: Int)
    {
        mClickListener = machineListClickListener

        holder.machineName.setText(items.get(position).name)
        holder.machinePrice.setText(items.get(position).price.toString())
        holder.mCropsNames.setText(items.get(position).crops.toString())

        holder.machineListLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                if (machineListClickListener != null)
                    machineListClickListener.onBtnClick(position, holder.machineListLayout)
            }
        })
    }
}


class MachinesViewHolder(view: View) : RecyclerView.ViewHolder(view)
{
    val machineListLayout = view.relateGrid!!
    val machineName= view.machineName!!
    val machinePrice= view.machinePrice!!
    val mCropsNames= view.cropsNames!!


}

interface MachineListClickListener {
    fun onBtnClick(position: Int, frameLayout: FrameLayout)
}