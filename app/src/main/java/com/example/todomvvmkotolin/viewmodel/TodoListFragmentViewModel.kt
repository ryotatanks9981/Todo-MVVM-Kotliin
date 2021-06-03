package com.example.todomvvmkotolin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todomvvmkotolin.model.TodoItem
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class TodoListFragmentViewModel: ViewModel() {
    private lateinit var realm: Realm
    private lateinit var todoItems: MutableList<TodoItem>
    lateinit var title: MutableLiveData<String>

    //調べる
    lateinit var data: OrderedRealmCollection<TodoItem>

    init {
        updateUI()
    }

    fun updateUI() {
        realm = Realm.getDefaultInstance()
        todoItems = realm.where<TodoItem>().findAll()
        data = (todoItems as RealmResults<TodoItem>?)!!
    }

    fun isDoneStateChange(id: Long) {
        realm.executeTransaction { db: Realm ->
            val todoItem = db.where<TodoItem>()
                .equalTo("id", id).findFirst()
            todoItem?.isDone = !todoItem?.isDone!!
        }
    }
}