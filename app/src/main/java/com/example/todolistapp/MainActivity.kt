package com.example.todolistapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var taskInput: EditText
    private lateinit var addTaskButton: Button
    private lateinit var taskListView: ListView

    private val tasks = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskInput = findViewById(R.id.taskInput)
        addTaskButton = findViewById(R.id.addTaskButton)
        taskListView = findViewById(R.id.taskListView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        taskListView.adapter = adapter

        addTaskButton.setOnClickListener {
            val task = taskInput.text.toString().trim()
            if (task.isNotEmpty()) {
                tasks.add(task)
                adapter.notifyDataSetChanged()
                taskInput.text.clear()
            }
        }

        taskListView.setOnItemClickListener { _, _, position, _ ->
            showEditDialog(position)
        }

        taskListView.setOnItemLongClickListener { _, _, position, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete") { _, _ ->
                    tasks.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }
    }

    private fun showEditDialog(position: Int) {
        val editText = EditText(this)
        editText.setText(tasks[position])

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(editText)
            .setPositiveButton("Update") { _, _ ->
                val newText = editText.text.toString().trim()
                if (newText.isNotEmpty()) {
                    tasks[position] = newText
                    adapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
