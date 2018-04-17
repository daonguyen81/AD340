package com.example.daong.activitykotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Main2Activity : AppCompatActivity() {

    internal val TAG = "States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = intent
        val text = intent.getStringExtra(EXTRA_TEXT)

        val textView = findViewById(R.id.textview) as TextView

        textView.text = text
        Log.d(TAG, "Main2Activity: onCreate()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Main2Activity: onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Main2Activity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Main2Activity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Main2Activity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Main2Activity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Main2Activity: onDestroy()")
    }
}
