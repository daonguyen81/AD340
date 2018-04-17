package com.example.daong.activitykotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

const val EXTRA_TEXT = "com.example.daong.activitykotlin.EXTRA_TEXT"

class MainActivity : AppCompatActivity() {
    internal val TAG = "States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener { openActivity2() }

        Log.d(TAG, "MainActivity: onCreate()")
    }

    fun openActivity2() {

        val editText = findViewById(R.id.edittext) as EditText
        val text = editText.text.toString()

        val intent = Intent(this, Main2Activity::class.java)
        intent.putExtra(EXTRA_TEXT, text)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "MainActivity: onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity: onDestroy()")
    }

}
