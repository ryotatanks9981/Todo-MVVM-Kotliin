package com.example.todomvvmkotolin

import android.app.Application
import android.util.Log
import com.example.todomvvmkotolin.util.MyRealmMigration
import io.realm.Realm
import io.realm.RealmConfiguration

private const val TAG = "TodoAppSampleKotlinApplication"

class TodoAppSampleKotlinApplication: Application() {

    @Suppress("LongLogTag")
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "called onCreate")
        realmMigration()
    }

    private fun realmMigration() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .schemaVersion(1L)
            .migration(MyRealmMigration())
            .build()

        Realm.setDefaultConfiguration(realmConfig)
    }

}