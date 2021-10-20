package com.refactory.android.todoapp.ui.task

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.refactory.android.todoapp.R
import com.refactory.android.todoapp.data.local.entity.DayEntity
import com.refactory.android.todoapp.data.local.entity.TaskEntity
import com.refactory.android.todoapp.databinding.*
import com.refactory.android.todoapp.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: TaskRvAdapter
    private lateinit var dayAdapter: DayRvAdapter

    companion object {
        var CURRENT_DATE = "no"
    }

    override fun onResume() {
        super.onResume()
        val simpleDF = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val currDate = simpleDF.format(Date())
        CURRENT_DATE = currDate
        loadData(CURRENT_DATE)
    }

    private fun loadData(currDate: String){
        val data = ArrayList<TaskEntity>()
        data.addAll(viewModel.getTaskToday(currDate))

        if (data.isEmpty()) {
            binding.tvTaskInformations.text = ("Empty Task")
        }else {
            binding.tvTaskInformations.text = ""
        }
        adapter.setData(data)
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)
        binding.rvTaskList.setHasFixedSize(true)
        binding.rvTaskList.adapter = adapter
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(layoutInflater.context)
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
        adapter = TaskRvAdapter()
        dayAdapter = DayRvAdapter()

        val c = Calendar.getInstance()
        val monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        val tempCheckLight = viewModel.getAllTask()
        for (i in tempCheckLight.indices) {
            tempCheckLight[i].date = tempCheckLight[i].date.substring(0, 2)
            Log.d("CEKK", tempCheckLight[i].date)
        }
        val dayEntityArray = ArrayList<DayEntity>()
        for (i in 1 until monthMaxDays+1) {
            var tempB = false
            for (j in tempCheckLight.indices) {
                if (tempCheckLight[j].date == i.toString()) {
                    tempB = true
                    break
                }
            }
            dayEntityArray.add(DayEntity(i.toLong(), i, tempB))
        }
        dayAdapter.setData(dayEntityArray)

        val currentDate: String = SimpleDateFormat("EEE, MMM d yyyy", Locale.getDefault()).format(Date())

        binding.tvDate.text = currentDate

        dayAdapter.setOnItemClickCallBack(object : DayRvAdapter.OnItemClickCallBack {
            override fun onItemClicked(taskEntity: DayEntity, index: Int) {
                val simpleDF = SimpleDateFormat("MM/yyyy", Locale.ENGLISH)
                var nDate = simpleDF.format(Date())
                nDate = "${taskEntity.date}/$nDate"
                CURRENT_DATE = nDate
                loadData(CURRENT_DATE)
            }

        })


        adapter.setOnItemClickCallBack(object : TaskRvAdapter.OnItemClickCallBack {
            override fun onItemClicked(taskEntity: TaskEntity, index: Int) {
                val bindingDialog: DialogTaskOptionBinding =
                        DialogTaskOptionBinding.inflate(LayoutInflater.from(context))
                val builder = AlertDialog.Builder(layoutInflater.context)
                builder.setView(bindingDialog.root)
                        .setCancelable(false)
                val dialog = builder.show()

                bindingDialog.detail.setOnClickListener {
                    dialog.dismiss()
                    val bindingDialogDetail: LayoutDetailTaskBinding =
                            LayoutDetailTaskBinding.inflate(LayoutInflater.from(context))
                    val builderDetail = AlertDialog.Builder(layoutInflater.context)
                    builderDetail.setView(bindingDialogDetail.root)
                            .setCancelable(true)
                    bindingDialogDetail.title.text = taskEntity.title
                    bindingDialogDetail.date.text = taskEntity.date
                    bindingDialogDetail.time.text = ("${taskEntity.startTime} - ${taskEntity.endTime}")
                    bindingDialogDetail.desc.text = taskEntity.desc
                    builderDetail.show()
                }
                bindingDialog.edit.setOnClickListener {
                    editTaskDialog(taskEntity)
                    dialog.dismiss()
                }
                bindingDialog.delete.setOnClickListener {
                    if (taskEntity.id != null) {
                        dialog.dismiss()
                        viewModel.deleteTask(taskEntity.id!!)
                        adapter.deleteItem(index)
                        val tvInfoTask = activity?.findViewById<TextView>(R.id.tv_info_task)
                        if (adapter.sizeData() == 0) tvInfoTask?.text = ("No Task for Today")
                        else tvInfoTask?.text = ("${adapter.sizeData()} Task for Today")
                    }
                }
                bindingDialog.btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }

        })




        binding.rvTaskList.layoutManager = LinearLayoutManager(context)
        binding.rvTaskList.setHasFixedSize(true)
        binding.rvTaskList.adapter = adapter

        binding.rvDayList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        binding.rvDayList.setHasFixedSize(true)
        binding.rvDayList.adapter = dayAdapter
    }

    @SuppressLint("SimpleDateFormat")
    private fun editTaskDialog(taskEntity: TaskEntity) {
        val bindingDialogEdit: DialogAddTaskBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(layoutInflater.context)
        builder.setView(bindingDialogEdit.root)
            .setCancelable(false)
        val dialog = builder.show()

        bindingDialogEdit.inputTitle.setText(taskEntity.title)
        bindingDialogEdit.inputDescription.setText(taskEntity.desc)
        bindingDialogEdit.date.text = (taskEntity.date)
        bindingDialogEdit.startTime.text = taskEntity.startTime
        bindingDialogEdit.endTime.text = taskEntity.endTime

        val cal = Calendar.getInstance()
        // val format = SimpleDateFormat("dd/MM/yyyy")
        var selectDate = taskEntity.date
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            bindingDialogEdit.date.text = sdf.format(cal.time)
            selectDate = sdf.format(cal.time)
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bindingDialogEdit.btnCalendar.setOnClickListener {
            DatePickerDialog(layoutInflater.context,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        bindingDialogEdit.btnCancel.setOnClickListener {
            dialog.dismiss()
        }


        bindingDialogEdit.btnStartTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(context, { _, h, m ->
                val tempM = if (m < 10) "0$m" else m
                val tempH = if (h < 10) ("0$h") else h
                bindingDialogEdit.startTime.text = ("$tempH:$tempM")
            }, hour, minute, false)

            tpd.show()
        }

        bindingDialogEdit.btnEndTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(context, { _, h, m ->
                val tempM = if (m < 10) "0$m" else m
                val tempH = if (h < 10) ("0$h") else h
                bindingDialogEdit.endTime.text = ("$tempH:$tempM")
            }, hour, minute, false)

            tpd.show()
        }

        bindingDialogEdit.btnAddTask.setOnClickListener {
            val title = bindingDialogEdit.inputTitle.text.toString().trim()
            val desc = bindingDialogEdit.inputDescription.text.toString().trim()
            val startTime = bindingDialogEdit.startTime.text.toString().trim()
            val endTime = bindingDialogEdit.endTime.text.toString().trim()
            when {
                title.isEmpty() -> bindingDialogEdit.inputTitle.error = "Please Fill"
                desc.isEmpty() -> bindingDialogEdit.inputDescription.error = "Please Fill"
                startTime.isEmpty() -> Toast.makeText(context, "Select start time", Toast.LENGTH_SHORT).show()
                endTime.isEmpty() -> Toast.makeText(context, "Select end time", Toast.LENGTH_SHORT).show()
                else -> {
                    val tempData = TaskEntity()
                    tempData.id = taskEntity.id
                    tempData.title = title
                    tempData.desc = desc
                    tempData.date = selectDate
                    tempData.startTime = startTime
                    tempData.endTime = endTime
                    Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show()
                    viewModel.updateTask(tempData)
                    loadData(CURRENT_DATE)
                    dialog.dismiss()
                }
            }
        }
    }
}