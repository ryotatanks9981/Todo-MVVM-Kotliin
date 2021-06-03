package com.example.todomvvmkotolin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todomvvmkotolin.model.TodoItem
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*

class TodoItemDetailFragmentViewModel: ViewModel() {
    private val realm = Realm.getDefaultInstance()
    var id: Int = 0

    // TodoItemを新規作成する
    fun createNewTask(title: String, detail: String?) {
        realm.executeTransaction { db: Realm ->
            val maxId = db.where<TodoItem>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val todoItem = db.createObject<TodoItem>(nextId)
            todoItem.title = title
            todoItem.detail = detail ?: ""
            todoItem.createDate = Date()
        }
        //通知かなんかだす
    }

    //内容変更
    fun updateTask(id: Long, title: String, detail: String?) {
        realm.executeTransaction { db: Realm ->
            val todoItem = db.where<TodoItem>()
                    //検索
                .equalTo("id", id).findFirst()
            todoItem?.title = title
            todoItem?.detail = detail ?: ""
            todoItem?.updateDate = Date()
        }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }


}