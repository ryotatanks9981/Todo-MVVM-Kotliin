package com.example.todomvvmkotolin.viewmodel

import android.content.ContentValues.TAG
import android.util.EventLog
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todomvvmkotolin.model.TodoItem
import com.example.todomvvmkotolin.util.Event

class MainActivityViewModel: ViewModel() {
    var selectedItem: TodoItem? = null
    val navigateToFragment: LiveData<Event<FragmentNavigationRequest>>
        get() = _navigateToFragment

    private val _navigateToFragment = MutableLiveData<Event<FragmentNavigationRequest>>()

    fun createTask() {
        Log.d(TAG, "createTask")
        selectedItem = null
        showFragment(TodoItemDetailFragment.newInstance())
    }

    fun showFragment(fragment: Fragment, backStack: Boolean = true, tag: String? = null) {
        _navigateToFragment.value = Event(FragmentNavigationRequest(fragment, backStack, tag))
    }

    fun todoItemClicked(todoItem: TodoItem) {
        Log.d(TAG, "called todoItemClicked")
        selectedItem = todoItem
        showFragment(TodoItemDetailFragment.newInstance())
    }
}

data class FragmentNavigationRequest(
    val fragment: Fragment,
    val backStack: Boolean,
    val tag: String? = null
)