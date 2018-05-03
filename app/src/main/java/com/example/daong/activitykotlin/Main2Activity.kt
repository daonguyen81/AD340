package com.example.daong.activitykotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.daong.activitykotlin.MainActivity.EXTRA_TEXT

class Main2Activity : AppCompatActivity() {

    internal val TAG = "States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Message"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        val intent = intent
        val text = intent.getStringExtra(EXTRA_TEXT)

        val textView = findViewById(R.id.textview) as TextView

        textView.text = text
        Log.d(TAG, "Main2Activity: onCreate()")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_setting) {
            val toast = Toast.makeText(this@Main2Activity, "This is setting option menu!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 10)
            toast.show()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
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
