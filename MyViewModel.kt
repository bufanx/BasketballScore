package com.permissionx.bbscore

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class PlayStatus{
    Playing,Paused
}
class MyViewModel:ViewModel(){
    private var _aTeamScore = MutableLiveData(0)
    private var _bTeamScore = MutableLiveData(0)
    val aTeamScore: LiveData<Int> = _aTeamScore
    val bTeamScore: LiveData<Int> = _bTeamScore
    private var aBack: Int = 0
    private var bBack: Int = 0
    private val _playStatus = MutableLiveData(PlayStatus.Playing)
    val playerStatus:LiveData<PlayStatus> = _playStatus

    init {
        Log.d("songshidian-log", "init, aTeamScore=$aTeamScore")
        _aTeamScore.value = 0
        _bTeamScore.value = 0
        _playStatus.value = PlayStatus.Paused
    }

//    public fun aScore() : Int? {
//        Log.d("songshidian-log", "aScore")
//        return aTeamScore.value
//    }
//
//    public fun bScore() : Int? {
//        Log.d("songshidian-log", "bScore")
//        return bTeamScore.value
//    }

    public fun aTeamAdd(p: Int) {
        aBack = _aTeamScore.value!!
        bBack = _bTeamScore.value!!
        Log.d("songshidian-log", "aTeamAdd:p=$p")
        _aTeamScore.value = _aTeamScore.value?.plus(p)
        Log.d("songshidian-log", "aTeamAdd:ascore=${_aTeamScore.value}")
    }

    fun togglePlayerStatus(){
        when(_playStatus.value){
            PlayStatus.Playing -> {
                _playStatus.value = PlayStatus.Paused
            }
            PlayStatus.Paused -> {
                _playStatus.value = PlayStatus.Playing
            }
            else -> return
        }
    }
    public fun bTeamAdd(p: Int) {
        aBack = _aTeamScore.value!!
        bBack = _bTeamScore.value!!
        Log.d("songshidian-log", "bTeamAdd:p=$p")
        _bTeamScore.value = _bTeamScore.value?.plus(p)
        Log.d("songshidian-log", "bTeamAdd:ascore=${_bTeamScore.value}")
    }

    public fun reset() {
        aBack = _aTeamScore.value!!
        bBack = _bTeamScore.value!!
        _aTeamScore.value = 0
        _bTeamScore.value = 0
    }
    public fun undo() {
        _aTeamScore.value = aBack
        _bTeamScore.value = bBack
    }
}