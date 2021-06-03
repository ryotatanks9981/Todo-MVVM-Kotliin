package com.example.todomvvmkotolin

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todomvvmkotolin.model.TodoItem
import com.example.todomvvmkotolin.viewmodel.TodoListFragmentViewModel
import io.realm.RealmRecyclerViewAdapter

private const val TAG = "TodoItemAdapter"

class TodoItemAdapter(viewModel: TodoListFragmentViewModel): RealmRecyclerViewAdapter<TodoItem, TodoItemAdapter.TodoItemViewHolder>(viewModel.data, true) {
    
    private lateinit var listener: OnItemClickListener
    private lateinit var longListener: OnLongItemClickListener
    private lateinit var checkboxListener: OnCheckBoxClickListener
    
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    
    interface OnItemClickListener {
        fun onItemClickListener(todoItem: TodoItem, position: Int)
    }
    
    fun setOnLongItemListener(longListener: OnLongItemClickListener) {
        this.longListener = longListener
    }

    interface OnLongItemClickListener {
        fun onLongItemClickListener(todoItem: TodoItem, position: Int): Boolean
    }
    
    fun setOnCheckBoxListener(checkBoxListener: OnCheckBoxClickListener) {
        this.checkboxListener = checkBoxListener
    }
    
    interface OnCheckBoxClickListener {
        fun onCheckBoxClickListener(todoItem: TodoItem, position: Int)
    }
    
    init {
        setHasStableIds(true)
    }
    
    class TodoItemViewHolder(
        binding: TodoItemFragmentBinding,
        private var listener: OnItemClickListener,
        private var longListener: OnLongItemClickListener,
        private var checkBoxListener: OnCheckBoxClickListener
    ): RecyclerView.ViewHolder(binding.root) {
        private var mBinding: TodoItemFragmentBinding = binding
        var title: TextView = binding.title
        
        fun bindTo(todoItem: TodoItem, position: Int) {
            Log.d(TAG, "called bindTo")
            mBinding.title.text = todoItem.title
            mBinding.checkBox.isChecked = todoItem.isDone
            mBinding.root.setOnClickListener {
                Log.d(TAG, "called bindTo Click listener")
                listener.onItemClickListener(todoItem, position)
            }

            mBinding.root.setOnLongClickListener {
                Log.d(TAG, "called bindTo long Click listener")
                longListener.onLongItemClickListener(todoItem, position)
            }

            mBinding.checkBox.setOnClickListener {
                Log.d(TAG, "called bindTo click checkBoxListener")
                checkBoxListener.onCheckBoxClickListener(todoItem, position)
            }
            mBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        Log.d(TAG, "called oncreateviewHolder")
        setOnItemClickListener(listener)
        setOnCheckBoxListener(checkboxListener)
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemFragmentBinding.inflate(inflater, parent, false)
        return TodoItemViewHolder(binding, listener, longListener, checkboxListener)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        Log.d(TAG, "called onBindViewModel")
        val todoItem: TodoItem? = getItem(position)
        if (todoItem != null) {
            holder.bindTo(todoItem, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)?.id ?: 0
    }
}