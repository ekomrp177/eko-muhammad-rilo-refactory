package com.refactory.android.todoapp.ui.task

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refactory.android.todoapp.R
import com.refactory.android.todoapp.data.local.entity.DayEntity
import com.refactory.android.todoapp.databinding.LayoutDayTaskBinding

class DayRvAdapter: RecyclerView.Adapter<DayRvAdapter.DayViewHolder>() {
    private var onItemClickCallBack : OnItemClickCallBack? = null

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack{
        fun onItemClicked(taskEntity: DayEntity, index: Int)
    }

    private val mData = ArrayList<DayEntity>()
    fun setData(items: ArrayList<DayEntity>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): DayRvAdapter.DayViewHolder {
        val mView = LayoutDayTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(mView)
    }

    override fun onBindViewHolder(holder: DayRvAdapter.DayViewHolder, position: Int) {
        holder.bind(mData[position], position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class DayViewHolder(private val binding: LayoutDayTaskBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(dayEntity: DayEntity, position: Int) {
            with(binding) {
                dayInitial.text = ("T")
                dateOfDay.text = dayEntity.date.toString()
                if (dayEntity.infoLight) infoLight.visibility = View.VISIBLE
                else infoLight.visibility = View.INVISIBLE

                layoutDayTask.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(dayEntity, position)
                }
            }
        }
    }
}