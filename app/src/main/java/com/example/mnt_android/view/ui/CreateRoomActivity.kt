package com.example.mnt_android.view.ui

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.mnt_android.R
import com.example.mnt_android.databinding.ActivityCreateMissionBinding
import com.example.mnt_android.databinding.ActivityCreateroomBinding
import com.example.mnt_android.service.model.CheckRoom
import com.example.mnt_android.service.model.Room
import com.example.mnt_android.viewmodel.BackPressViewModel
import com.example.mnt_android.viewmodel.CreateRoomViewModel
import com.google.firebase.iid.FirebaseInstanceId
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.*
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import kotlinx.android.synthetic.main.fragment_createroom2.*
import java.util.*

class CreateRoomActivity :FragmentActivity()
{
    lateinit var createRoomViewModel: CreateRoomViewModel
    lateinit var createRoomFragment : CreateRoomFragment
    lateinit var createRoomFragment2 : CreateRoomFragment2
    lateinit var createRoomFragment3 : CreateRoomFragment3
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager
    lateinit var backPressViewModel : BackPressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<ActivityCreateroomBinding>(this, R.layout.activity_createroom)
        binding.lifecycleOwner = this // LiveData를 사용하기 위해서 없으면 Observe할때마다 refresh안딤


        Log.d("wlgusdnzzz",FirebaseInstanceId.getInstance().token!!)

        createRoomViewModel = ViewModelProviders.of(this)[CreateRoomViewModel::class.java]
        backPressViewModel=  ViewModelProviders.of(this)[BackPressViewModel::class.java]

        fragmentManager =supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        createRoomFragment= CreateRoomFragment()
        createRoomFragment2= CreateRoomFragment2()
        createRoomFragment3= CreateRoomFragment3()

        setFrag(0)





        val intent = intent
        val str = intent.data
        if(str!=null)
            Toast.makeText(this@CreateRoomActivity,intent.data.getQueryParameter("roomnum").toString(),Toast.LENGTH_LONG).show()

        //내가 참여하고 있는 Room정보
        val checkRoom: CheckRoom? = intent.getParcelableExtra("checkRoom")
        val fragNum = intent.getIntExtra("fragNum",0)
        if(checkRoom!=null)
        {
            //MainActivity에서 방이 존재한다고 판단하여 방정보를 넘김
            createRoomViewModel.fragmentNum=fragNum
            createRoomViewModel.room.value=checkRoom.room
            createRoomViewModel.id=checkRoom.room.id.toString()
            setFrag(fragNum)

        }

        createRoomViewModel.isCreated.observe(this,androidx.lifecycle.Observer {
            if(it==true)
                setFrag(2)
        })


    }

    fun setDate(index : Int)
    {
        val today = Date()
        var strdate: String? = null

        var format1 = SimpleDateFormat()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            format1 = SimpleDateFormat("yyyy-MM-dd")
            strdate = format1.format(today)
        }

        val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            if(index==1)
            {
                createRoomViewModel.startDay.value=year.toString()+"-"+month.toString()+"-"+dayOfMonth.toString()
                Log.d("wlgusdnzzz",createRoomViewModel.startDay.value)
            }
            else
            {
                createRoomViewModel.endDay.value=year.toString()+"-"+month.toString()+"-"+dayOfMonth.toString()
                Log.d("wlgusdnzzz", createRoomViewModel.endDay.value)
            }


        },  strdate!!.split("-")[0].toInt(),strdate!!.split("-")[1].toInt()-1,strdate!!.split("-")[2].toInt())


        dialog.show()




    }

    fun sendKakaoLink(roomnum : String)
    {
    var params = TextTemplate
        .newBuilder("마니또를 생성하였습니다", LinkObject.newBuilder().setAndroidExecutionParams("https://www.naver.com").build())
        .addButton(ButtonObject("앱에서 보기",LinkObject.newBuilder().setWebUrl("'https://www.naver.com'").setMobileWebUrl("'https://www.naver.com'")
            .setAndroidExecutionParams("roomnum=$roomnum").build())).build()

    var serverCallbackArgs  = HashMap<String, String>();
    var aa : Map<Any,Any> = HashMap<Any,Any>()


    var aaa  = object : ResponseCallback<KakaoLinkResponse>(){
        override fun onSuccess(result: KakaoLinkResponse?) {


        }

        override fun onFailure(errorResult: ErrorResult?) {

        }

    }

    KakaoLinkService.getInstance().sendDefault( this, params, serverCallbackArgs,aaa)

}



    fun setFrag(n : Int)
    {
        fragmentTransaction = fragmentManager.beginTransaction()

        when(n)
        {
            0 ->
            {
                fragmentTransaction.replace(R.id.frag_createroom,createRoomFragment)
                createRoomViewModel.fragmentNum=0
                fragmentTransaction.commit()

            }
            1->
            {
                fragmentTransaction.replace(R.id.frag_createroom,createRoomFragment2)
                createRoomViewModel.fragmentNum=1
                fragmentTransaction.commit()
            }
            2->
            {
                fragmentTransaction.replace(R.id.frag_createroom,createRoomFragment3)
                createRoomViewModel.fragmentNum=2
                fragmentTransaction.commit()
            }
        }
    }

    override fun onBackPressed() {

        when(createRoomViewModel.fragmentNum)
        {
            0->
            {
                //backPressViewModel.onBackPressed(this)
                finish()
            }
            1->
            {
                setFrag(0)
            }
            2->
            {
                backPressViewModel.onBackPressed(this)
            }
        }

    }

}