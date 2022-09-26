package io.sample.task.home

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*
import io.sample.task.*

class SortListAdapter(private var data:ArrayList<Int>?) : RecyclerView.Adapter<SortListAdapter.SortViewHolder>() {

    class SortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item:TextView = itemView.findViewById(R.id.item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sort_item,parent,false)
        return SortViewHolder((view))
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        holder.item.text = "${data?.get(position)}"
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setData(x:ArrayList<Int>){
        if (data == null){
            data = ArrayList()
        }
        data?.clear()
        data?.addAll(x)
        notifyItemRangeChanged(0,data?.size ?: 0)
    }
}