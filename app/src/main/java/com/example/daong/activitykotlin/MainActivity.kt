package com.example.daong.activitykotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.R.attr.bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.view.Gravity
import android.widget.Toast


const val EXTRA_TEXT = "com.example.daong.activitykotlin.EXTRA_TEXT"

class MainActivity : AppCompatActivity() {
    internal val TAG = "States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<View>(R.id.imageView) as ImageView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.daofamily)
        val round = RoundedBitmapDrawableFactory.create(resources, bitmap)
        round.isCircular = true
        imageView.setImageDrawable(round)

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener { openActivity2() }
        val zbutton = findViewById(R.id.z_button) as Button
        zbutton.setOnClickListener { openZombieList() }
        Log.d(TAG, "MainActivity: onCreate()")
    }

    fun openActivity2() {

        val editText = findViewById(R.id.edittext) as EditText
        val text = editText.text.toString()

        val intent = Intent(this, Main2Activity::class.java)
        intent.putExtra(EXTRA_TEXT, text)
        startActivity(intent)
    }

    private fun openZombieList() {
        val intent = Intent(this, ZombieList::class.java)
        startActivity(intent)
    }

    fun onDisplayToast1(v: View) {
        val toast = Toast.makeText(this@MainActivity, "This is TOAST 1!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 16)
        toast.show()
    }

    fun onDisplayToast2(v: View) {
        val toast = Toast.makeText(this@MainActivity, "This is TOAST 2!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 16)
        toast.show()
    }

    fun onDisplayToast3(v: View) {
        val toast = Toast.makeText(this@MainActivity, "This is TOAST 3!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 16)
        toast.show()
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
