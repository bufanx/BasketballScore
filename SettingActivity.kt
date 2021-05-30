package com.permissionx.bbscore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    private lateinit var redTeamName:String
    private lateinit var blueTeamName:String
    private lateinit var totalTimeMinute:String
    private lateinit var totalTimeSecond:String
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        if (!isIgnoringBatteryOptimizations()){
            requestIgnoreBatteryOptimizations()
        }
        begin.setOnClickListener {
            redTeamName = red_team_name?.text.toString()
            blueTeamName = blue_team_name?.text.toString()
            totalTimeMinute = total_time_minute?.text.toString()
            totalTimeSecond = total_time_second?.text.toString()
            Log.d("team",redTeamName)
            val intent = Intent(this,MainActivity::class.java)
            Log.d("team",redTeamName)
            if (redTeamName.isEmpty()){
                redTeamName = "TEAM A"
            }
            if (blueTeamName.isEmpty()){
                blueTeamName = "TEAM B"
            }
            if (totalTimeMinute.isEmpty()){
                totalTimeMinute = 0.toString()
            }
            if (totalTimeSecond.isEmpty()){
                totalTimeSecond = 0.toString()
            }
            Info.redTeamName = redTeamName
            Info.blueTeamName = blueTeamName
            Info.totalTime = (totalTimeMinute.toInt() * 60) + totalTimeSecond.toInt()
            startActivity(intent)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun isIgnoringBatteryOptimizations():Boolean{
        var isIgnoring :Boolean = false
        val powerManager: PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName)
        return isIgnoring
    }
    @SuppressLint("BatteryLife")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestIgnoreBatteryOptimizations(){
        try {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}