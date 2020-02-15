package com.example.mnt_android.viewmodel

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mnt_android.service.model.Room
import com.example.mnt_android.service.model.RoomId
import com.example.mnt_android.service.model.RoomInfo
import com.example.mnt_android.service.model.SendRoom
import com.example.mnt_android.service.repository.DBRepository
import com.example.mnt_android.view.ui.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.random.Random

class CreateRoomViewModel : ViewModel()
{


 var room : MutableLiveData<Room> = MutableLiveData()
  var fragmentNum = 0
  private val repository = DBRepository()
  var name : String=""
  var startDay : MutableLiveData<String> = MutableLiveData()
    var endDay :MutableLiveData<String> = MutableLiveData()
  var maxPeople : String = ""
 var id : String = ""
 var isCreated : MutableLiveData<Boolean> = MutableLiveData()
  lateinit var roomId : RoomId
 var userId : String = ""
 lateinit var format : SimpleDateFormat

  init {
      room.value = Room("",0,0,0,1,name,"")

      roomId = RoomId(0)
     format = SimpleDateFormat("yyyy-MM-dd")




  }

  fun setDate()
  {

  }

  fun setRoomInfo()
  {



  }

  fun startRoom()
   {


  }



  fun createRoom()
  {
   room.value = Room( endDay.value!!,0,0,0,maxPeople.toInt(),name,startDay.value!!)
    repository.createRoom(SendRoom(format.parse(room.value!!.endDay),0,0,0,maxPeople.toInt(),name,format.parse(room.value!!.startDay)),MainActivity.userId)
     .subscribeOn(Schedulers.io())
     .observeOn(AndroidSchedulers.mainThread())
     .subscribe({ t: RoomId? ->

      roomId.roomId=t!!.roomId
      id = roomId.roomId.toString()

      Log.d("wlgusdnzzz",t?.roomId.toString())

      isCreated.value=true

     })

   //API로 방 생성하기





   // repository.createRoom()
  }


  fun findParticipants()
  {
    //api로 참가자들 정보 받아오기
  }






}