package com.example.todomvvmkotolin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.todomvvmkotolin.view.TodoListFragment
import com.example.todomvvmkotolin.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.navigateToFragment.observe(this, Observer {
            it?.getContentIfNotHandled()?.let { fragmentRequest ->
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fragmentContainer, fragmentRequest.fragment, fragmentRequest.tag
                )
                if (fragmentRequest.backStack) transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        val fragment = TodoListFragment.newInstance()
        viewModel.showFragment(fragment)
    }
}