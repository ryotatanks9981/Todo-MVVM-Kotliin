package com.example.todomvvmkotolin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.todomvvmkotolin.R
import com.example.todomvvmkotolin.model.TodoItem
import com.example.todomvvmkotolin.util.toString
import com.example.todomvvmkotolin.viewmodel.MainActivityViewModel
import com.example.todomvvmkotolin.viewmodel.TodoItemDetailFragmentViewModel

private const val TAG = "TodoItemDetailFragment"

class TodoItemDetailFragment : Fragment() {

    private lateinit var binding: TodoItemDetailFragmentBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val todoItemDetailFragmentViewModel: TodoItemDetailFragmentViewModel by viewModels()

    companion object {
        fun newInstance() = TodoItemDetailFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "called onCreateView")
        
        val view = inflater.inflate(R.layout.fragment_todo_item_detail, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "called onActivityCreated")
        
        binding.buttonRight,setOnClickListener {
            closeFragment()
        }
        
        val selectedItem = mainActivityViewModel.selectedItem
        if (selectedItem == null) {
            showNewTask()
        } else {
            showTask(selectedItem)
        }
    }
    
    private fun closeFragment() {
        val mainActivity = activity
        if (mainActivity != null) {
            mainActivity.supportFragmentManager.popBackStack()
        } else {
            mainActivityViewModel.createTask()
        }
    }
    
    private fun showNewTask() {
        binding.buttonLeft.setOnClickListener { 
            todoItemDetailFragmentViewModel.createNewTask(
                binding.editTitle.text.toString(),
                binding.editDetai.text.toString()
            )
            closeFragment()
        }
    }
    
    private fun showTask(todoItem: TodoItem) {
        binding.editTitle.setText(todoItem.title, TextView.BufferType.EDITABLE)
        binding.editDetai.setText(todoItem.detail, TextView.BufferType.EDITABLE)
        binding.editCreate.text = todoItem.createDate.toString("yyyy/MM/dd")
        binding.createDate.isVisible = true
        binding.editCreate.isVisible = true
        if (todoItem.createDate != todoItem.updateDate) {
            binding.editUpdate.text = todoItem.updateDate.toString("yyyy/MM/dd")
            binding.editUpdate.isVisible = true
            binding.update.isVisible = true
        }
        binding.buttonLeft.text = "更新"
        binding.buttonLeft.setOnClickListener {
            todoItemDetailFragmentViewModel.updateTask(
                todoItem.id,
                binding.editTitle.text.toString(),
                binding.editDetail.text.toString()
            )
            closeFragment()
        }
    }

}