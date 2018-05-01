package com.example.daong.activitykotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.support.design.widget.NavigationView
import android.view.Gravity
import android.widget.Toast
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View


const val EXTRA_TEXT = "com.example.daong.activitykotlin.EXTRA_TEXT"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal val TAG = "States"
    private var mDrawerLayout: DrawerLayout? = null
    private var mToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawerLayout) as DrawerLayout
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout!!.addDrawerListener(mToggle!!)
        mToggle!!.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById(R.id.navigation_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener { openActivity2() }
        val zbutton = findViewById(R.id.z_button) as Button
        zbutton.setOnClickListener { openZombieList() }
        Log.d(TAG, "MainActivity: onCreate()")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.about_us) {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }

        if (id == R.id.main_list) {
            val intent = Intent(this, ZombieList::class.java)
            startActivity(intent)
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (mToggle!!.onOptionsItemSelected(item)) {
            return true
        }

        if (id == R.id.action_setting) {
            val toast = Toast.makeText(this@MainActivity, "This is setting option menu", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 10)
            toast.show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openAboutActivity() {
        val intent = Intent(this, About::class.java)
        startActivity(intent)
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

    fun onDisplayToast1(v:View) {
        val toast = Toast.makeText(this@MainActivity, "This is TOAST 1!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 10)
        toast.show()
    }

    fun onDisplayToast2(v:View) {
        val toast = Toast.makeText(this@MainActivity, "This is TOAST 2!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 10)
        toast.show()
    }

    fun onDisplayToast3(v:View) {
        val toast = Toast.makeText(this@MainActivity, "This is TOAST 3!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 10)
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
