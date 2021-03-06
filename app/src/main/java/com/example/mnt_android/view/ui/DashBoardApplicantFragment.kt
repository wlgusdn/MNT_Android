package com.example.mnt_android.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.mnt_android.R
import com.example.mnt_android.base.BaseFragment
import com.example.mnt_android.bus.MISSION_LIST_FROM_ME
import com.example.mnt_android.bus.MISSION_LIST_TO_ME
import com.example.mnt_android.bus.sendFilteringEvent
import com.example.mnt_android.databinding.FragmentDashBoardApplicantBinding
import com.example.mnt_android.view.dialog.ConfirmDialog
import com.example.mnt_android.view.dialog.CustomAlertDialog
import com.example.mnt_android.view.dialog.NoticeDialog
import com.example.mnt_android.viewmodel.DashBoardViewModel
import kotlinx.android.synthetic.main.fragment_dash_board_applicant.*
import org.jetbrains.anko.sdk21.listeners.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class DashBoardApplicantFragment : BaseFragment() {
    companion object {
        private const val TAG = "Dashboard Applicant Dialog"
    }

    private val viewModel by viewModel<DashBoardViewModel>()
    private lateinit var binding: FragmentDashBoardApplicantBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bind(inflater, container, R.layout.fragment_dash_board_applicant)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel.apply {
            loadDashBoard()
        }
        return binding.root
    }

    override fun initializeUI() {
        setEventListener()
    }

    private fun changeTimeLineFragment(
        supportFragmentManager: FragmentManager,
        filterType: String
    ) {
        val fragment = TimeLineFragment(arrayOf(filterType, viewModel.getUserId()))
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.game_layout, fragment)
        transaction.commit()
    }

    private fun setEventListener() {
        val supportFragmentManager = (context as FragmentActivity).supportFragmentManager
        go_send_mission_layout.onClick {
            changeTimeLineFragment(supportFragmentManager, MISSION_LIST_FROM_ME)
        }
        go_receive_mission_layout.onClick {
            changeTimeLineFragment(supportFragmentManager, MISSION_LIST_TO_ME)
        }
        notification_switch.onClick {
            viewModel.setOnNotification(notification_switch.isChecked)
        }
        dev_info_layout.onClick {
            NoticeDialog("개발자정보", "고민중").show(supportFragmentManager, TAG)
        }
        exit_room_layout.onClick {
            if (viewModel.getCheckNaeto()) {
                ConfirmDialog(
                    "프루또 방을 나가시겠습니까?\n" +
                            "방을 나가면 다시 들어올 수 없습니다."
                ) {
                    viewModel.exitRoom {
                        viewModel.clearManitoData()
                        val i = Intent(context, SplashActivity::class.java)
                        context?.startActivity(i)
                    }
                }.show(
                    supportFragmentManager,
                    TAG
                )
            } else {
                CustomAlertDialog(
                    "당신의 호의를 기다리는 마니또를 생각하세요.",
                    "확인"
                ).show(
                    supportFragmentManager,
                    TAG
                )
            }
        }
    }
}
