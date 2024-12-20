package com.example.mychatappclone.chatApp

import android.app.Application
import com.example.mychatappclone.R
import com.parse.Parse
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        )

    }
}