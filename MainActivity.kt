package com.permissionx.bbscore

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.permissionx.bbscore.Info.redTeamName
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.acl.Owner
import kotlin.properties.Delegates


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {
    private lateinit var myViewModel: MyViewModel
    private lateinit var redTeamName:String
    private lateinit var blueTeamName:String
    private var totalTime by Delegates.notNull<Int>()
    var TFSecond:Long = Info.TFSecond
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonClickable(true)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        redTeamName = Info.redTeamName
        blueTeamName = Info.blueTeamName
        totalTime = Info.totalTime
        var totalTime = Info.totalTime
        team_1.text=redTeamName
        team_3.text = blueTeamName
        total_time.text = changeTime(totalTime)
        //倒计时
        val countDownTimer = object :CountDownTimer((totalTime*1000+1000).toLong(),1000){
            override fun onTick(millisUntilFinished: Long) {
                total_time.text = changeTime(totalTime)
                totalTime -= 1
            }

            override fun onFinish() {
            }
        }
        val TFcountDownTimer = object :CountDownTimer((Info.TFSecond*1000+1000).toLong(),1000){
            override fun onTick(millisUntilFinished: Long) {
                second_left.text = TFSecond.toString()
                TFSecond-=1
            }

            override fun onFinish() {
            }
        }
        lifecycleScope.launch {
            while (true){
                if (totalTime <= 0) {
                    buttonClickable(false)
                    countDownTimer.cancel()
                    TFcountDownTimer.cancel()
                    second_left.text = "0"
                    total_time.text = "00:00"
                    val msg = when {
                        myViewModel.aTeamScore.value!! > myViewModel.bTeamScore.value!! -> "总比分:\n${myViewModel.aTeamScore.value!!} : ${myViewModel.bTeamScore.value!!}\n${team_1.text}获胜!"
                        myViewModel.aTeamScore.value!! < myViewModel.bTeamScore.value!! -> "总比分:\n${myViewModel.aTeamScore.value!!} : ${myViewModel.bTeamScore.value!!}\n${team_3.text}获胜!"
                        else -> "总比分:\n" +
                                "${myViewModel.aTeamScore.value!!} : ${myViewModel.aTeamScore.value!!}\n" +
                                "平局!"
                    }
                    val temDialog = AlertDialog.Builder(this@MainActivity).setCancelable(true)
                            .setTitle("比赛时间结束!")
                            .setMessage(msg)
                            .setPositiveButton("加时") { _, _ ->
                            }
                            .setNegativeButton("结束") { _, _ -> }
                            .create()
                    temDialog.show()
                    break
                }
                if (TFSecond<=0){
                    TFSecond = Info.TFSecond
                    TFcountDownTimer.cancel()
                    TFcountDownTimer.start()
                }
                delay(1000)
            }
        }
        myViewModel.aTeamScore.observe(this, Observer {
            a_score.text = myViewModel.aTeamScore.value.toString()
        })
        myViewModel.bTeamScore.observe(this, Observer {
            b_score.text = myViewModel.bTeamScore.value.toString()
        })
        buttonA1.setOnClickListener {
            myViewModel.aTeamAdd(1)
        }
        buttonA2.setOnClickListener {
            myViewModel.aTeamAdd(2)
        }
        buttonA3.setOnClickListener {
            myViewModel.aTeamAdd(3)
        }
        buttonB1.setOnClickListener {
            myViewModel.bTeamAdd(1)
        }
        buttonB2.setOnClickListener {
            myViewModel.bTeamAdd(2)
        }
        buttonB3.setOnClickListener {
            myViewModel.bTeamAdd(3)
        }
        imageButton_undo.setOnClickListener {
            myViewModel.undo()
        }
        imageButton_reset.setOnClickListener {
            myViewModel.togglePlayerStatus()
        }
        reset_24.setOnClickListener {
            TFSecond = Info.TFSecond
            TFcountDownTimer.cancel()
            TFcountDownTimer.start()
        }
        myViewModel.playerStatus.observe(this, Observer {
            when(it){
                PlayStatus.Playing -> {
                    imageButton_reset.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    countDownTimer.cancel()
                    TFcountDownTimer.cancel()
                }
                PlayStatus.Paused -> {
                    imageButton_reset.setImageResource(R.drawable.ic_baseline_pause_24)
                    countDownTimer.start()
                    TFcountDownTimer.start()
                }
                else -> imageButton_reset.setImageResource(R.drawable.ic_baseline_pause_24)
            }
        })
    }
    private fun buttonClickable(clickable:Boolean){
        buttonA1.isClickable = clickable
        buttonA2.isClickable = clickable
        buttonA3.isClickable = clickable
        buttonB1.isClickable = clickable
        buttonB2.isClickable = clickable
        buttonB3.isClickable = clickable
        imageButton_undo.isClickable = clickable
        imageButton_undo.isClickable = clickable
        reset_24.isClickable = clickable
    }
    private fun changeTime(time:Int):String{
        val second :Int= time
        if (second>3600){
            val hours:Int = second/60/60
            val minutes:Int = second/60%60
            val seconds:Int = second%60
            return "${formatTime(hours)}:${formatTime(minutes)}:${formatTime(seconds)}"
        }else{
            val minute:Int = second/60
            val seconds:Int = second%60
            return "${formatTime(minute)}:${formatTime(seconds)}"
        }
    }
    private fun formatTime(time:Int):String{
        return when {
            time<=0 -> {
                "00"
            }
            time in 1..9 -> {
                "0${time}"
            }
            else -> {
                time.toString()
            }
        }
    }
}