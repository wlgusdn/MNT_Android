package com.example.mnt_android.view.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mnt_android.R
import com.example.mnt_android.databinding.FragmentJoinroom3Binding
import com.example.mnt_android.viewmodel.JoinRoomViewModel
import kotlinx.android.synthetic.main.fragment_joinroom3.*

class JoinRoomFragment3 : Fragment()
{
    lateinit var joinRoomViewModel: JoinRoomViewModel
    lateinit var binding: FragmentJoinroom3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joinroom3,container,false)





        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            joinRoomViewModel=(activity as JoinRoomActivity).joinRoomViewModel

            binding.joinRoomViewModel = joinRoomViewModel
            binding.lifecycleOwner = this



        }
    }
    }