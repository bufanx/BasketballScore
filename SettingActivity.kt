package com.permissionx.bbscore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    private lateinit var redTeamName:String
    private lateinit var blueTeamName:String
    private lateinit var totalTimeMinute:String
    private lateinit var totalTimeSecond:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
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

}