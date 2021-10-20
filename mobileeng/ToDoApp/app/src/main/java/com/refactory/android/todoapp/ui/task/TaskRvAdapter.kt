package com.refactory.android.todoapp.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.refactory.android.todoapp.data.local.entity.TaskEntity
import com.refactory.android.todoapp.databinding.LayoutTaskListBinding

class TaskRvAdapter: RecyclerView.Adapter<TaskRvAdapter.TaskViewHolder>() {
    private var onItemClickCallBack : OnItemClickCallBack? = null

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack{
        fun onItemClicked(taskEntity: TaskEntity, index: Int)
    }

    private val mData = ArrayList<TaskEntity>()
    fun setData(items: ArrayList<TaskEntity>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskRvAdapter.TaskViewHolder {
        val mView = LayoutTaskListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(mView)
    }

    override fun onBindViewHolder(holder: TaskRvAdapter.TaskViewHolder, position: Int) {
        holder.bind(mData[position], position)
    }

    fun deleteItem(index: Int) {
        mData.removeAt(index)
        notifyDataSetChanged()
    }

    fun sizeData(): Int {
        return mData.size
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class TaskViewHolder(private val binding: LayoutTaskListBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(taskEntity: TaskEntity, position: Int) {
            with(binding) {
                title.text = taskEntity.title
                time.text = ("${taskEntity.startTime} - ${taskEntity.endTime}")
                tvStartTime.text = taskEntity.startTime

                btnMenu.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(taskEntity, position)
                }
            }
        }
    }
}