package com.example.todomvvmkotolin.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.todomvvmkotolin.R
import com.example.todomvvmkotolin.model.TodoItem
import com.example.todomvvmkotolin.viewmodel.MainActivityViewModel
import com.example.todomvvmkotolin.viewmodel.TodoListFragmentViewModel

private const val TAG = "TodoListFragment"

class TodoListFragment : Fragment() {

    private lateinit var binding: TodoItemFragmentBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val todoListFragmentViewModel: TodoListFragmentViewModel by viewModels()


    companion object {
        fun newInstance() = TodoListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    /**
     * Fragmentの表示とデータバインドの初期化のみ
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "called onCreateView")
        val view = inflater.inflate(R.layout.fragment_todo_list, container, false)
        binding = TodoListFragmentBinding.bind(view).apply {
            viewmodel = todoListFragmentViewModel
        }

        binding.lifecycleOwner = this.viewLifecycleOwner

        // Inflate the layout for this fragment
        return view
    }


    /**
     * ActivityCreatedでbindingやリスナーのセットを行う
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d(TAG, "called onActivityCreated")

        //adapterのバインド処理
        val adapter = TodoItemAdapter(todoListFragmentViewModel)
        binding.list.adapter = adapter

        //TodoItemをクリックしたときに詳細画面を表示する
        adapter.setOnItemClickListener(object : TodoItemAdapter.OnItemClickListener {
            override fun onItemClickListener(todoItem: TodoItem, position: Int) {
                Log.d(TAG, "called onItemClickListener")
                mainActivityViewModel.todoItemClicked(todoItem)
            }
        })

        //Todoitemを長押しした時は、TodoItemを削除する
        adapter.setOnItemClickListener(todoItem: TodoItemAdapter.OnLongItemClickListener {
            override fun onLongItemClickListener(todoItem: TodoItem, position: Int): Boolean {
                Log.d(TAG, "called onLongItemClickListener")
                todoListFragmentViewModel.deleteTask(todoItem)
                return true
            }
        })

        // checkBoxの状態を変更する
        adapter.setOnCheckBoxClickListener(object : TodoItemAdapter.OnCheckBoxClickListener {
            override fun onCheckBoxClickListener(todoItem: TodoItem, position: Int) {
                Log.d(TAG, "called onCheckBoxClickListener")
                todoListFragmentViewModel.isDoneStateChange(todoItem.id)
                todoListFragmentViewModel.updateUI()
            }
        })
        // FABを押したら新規作成を行う
        binding.floatingActionButton.setOnClickListener {
            Log.d(TAG, "FAB listener clicked")
            mainActivityViewModel.createTask()
        }
        //画面の更新
        todoListFragmentViewModel.updateUI()
    }
}

