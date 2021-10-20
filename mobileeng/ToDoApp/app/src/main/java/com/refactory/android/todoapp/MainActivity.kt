package com.refactory.android.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.refactory.android.todoapp.data.local.entity.TaskEntity
import com.refactory.android.todoapp.databinding.ActivityMainBinding
import com.refactory.android.todoapp.databinding.DialogAddTaskBinding
import com.refactory.android.todoapp.ui.login.LoginActivity
import com.refactory.android.todoapp.ui.task.TaskFragment.Companion.CURRENT_DATE
import com.refactory.android.todoapp.ui.task.TaskRvAdapter
import com.refactory.android.todoapp.utils.ViewModelFactory
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var bi: ActivityMainBinding

    private lateinit var adapter: TaskRvAdapter

    override fun onResume() {
        super.onResume()
        val simpleDF = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val currDate = simpleDF.format(Date())
        CURRENT_DATE = currDate
        loadData(CURRENT_DATE)
    }
    private fun loadData(currDate: String){

        val rvTaskList = findViewById<RecyclerView>(R.id.rv_task_list)
        val tvTaskInformations = findViewById<TextView>(R.id.tv_task_informations)

        val data = ArrayList<TaskEntity>()
        data.addAll(viewModel.getTaskToday(currDate))

        if (data.size == 0) bi.tvInfoTask.text = ("No Task for Today")
        else bi.tvInfoTask.text = ("${data.size} Task for Today")
        if (data.isEmpty()) {
            tvTaskInformations.text = ("Tidak ada konten yang disimpan")
        }else {
            tvTaskInformations.text = ""
        }

        adapter.setData(data)
        rvTaskList.layoutManager = LinearLayoutManager(this)
        rvTaskList.setHasFixedSize(true)
        rvTaskList.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)

        adapter = TaskRvAdapter()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        auth = FirebaseAuth.getInstance()
        checkUser()


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

        val bottomMenuView = navView.getChildAt(0) as BottomNavigationMenuView
        val view = bottomMenuView.getChildAt(1)
        val itemView = view as BottomNavigationItemView

        val viewCustom = LayoutInflater.from(this).inflate(
                R.layout.button_add_task,
                bottomMenuView,
                false
        )
        itemView.addView(viewCustom)

        val btnAddTask: FrameLayout = findViewById(R.id.btn_add_task)
        val btnAddTaskImg: ImageButton = findViewById(R.id.btn_img_add_task)

        // to handle middle navigation shadow dont delete
        btnAddTask.setOnClickListener {

        }

        btnAddTaskImg.setOnClickListener {
            showDialog()
        }
    }

    private fun checkUser(){
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }else {
            var name: String = firebaseUser.displayName!!
            val urlPhoto = "${firebaseUser.photoUrl}"
            name = if (name.contains(" ")) name.split(" ")[0] else name

            bi.tvGreeting.text = ("Hello, $name!")
            Picasso.get().load(urlPhoto).into(bi.userImage)
        }
    }

    private fun showDialog() {
        val binding: DialogAddTaskBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(binding.root)
                .setCancelable(false)
        val dialog = builder.show()

        val simpleDF = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        binding.date.text = simpleDF.format(Date())
        val cal = Calendar.getInstance()
        var selectDate = simpleDF.format(Date())
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.date.text = sdf.format(cal.time)
            selectDate = sdf.format(cal.time)
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.btnCalendar.setOnClickListener {
            DatePickerDialog(this@MainActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnStartTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, { _, h, m ->
                val tempM = if (m < 10) "0$m" else m
                val tempH = if (h < 10) ("0$h") else h
                binding.startTime.text = ("$tempH:$tempM")
            }, hour, minute, false)

            tpd.show()
        }

        binding.btnEndTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, { _, h, m ->
                val tempM = if (m < 10) "0$m" else m
                val tempH = if (h < 10) ("0$h") else h
                binding.endTime.text = ("$tempH:$tempM")
            }, hour, minute, false)

            tpd.show()
        }

        binding.btnAddTask.setOnClickListener {
            val title = binding.inputTitle.text.toString().trim()
            val desc = binding.inputDescription.text.toString().trim()
            val startTime = binding.startTime.text.toString().trim()
            val endTime = binding.endTime.text.toString().trim()
            when {
                title.isEmpty() -> binding.inputTitle.error = "Please Fill"
                desc.isEmpty() -> binding.inputDescription.error = "Please Fill"
                startTime.isEmpty() -> Toast.makeText(this, "Select start time", Toast.LENGTH_SHORT).show()
                endTime.isEmpty() -> Toast.makeText(this, "Select end time", Toast.LENGTH_SHORT).show()
                else -> {
                    val tempData = TaskEntity()
                    tempData.title = title
                    tempData.desc = desc
                    tempData.date = selectDate
                    tempData.startTime = startTime
                    tempData.endTime = endTime
                    Toast.makeText(this, "Added Success", Toast.LENGTH_SHORT).show()
                    viewModel.insertTask(tempData)
                    loadData(CURRENT_DATE)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }
    }
}