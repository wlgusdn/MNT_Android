package com.example.mnt_android.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mnt_android.R
import com.example.mnt_android.databinding.FragmentCreateroom3Binding
import com.example.mnt_android.viewmodel.CreateRoomViewModel

class CreateRoomFragment3 : Fragment()
{
    lateinit var createRoomViewModel: CreateRoomViewModel
    lateinit var binding: FragmentCreateroom3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_createroom3,container,false)




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            createRoomViewModel = (activity as CreateRoomActivity).createRoomViewModel


            binding.createRoomViewModel=createRoomViewModel
            binding.createRoomActivity = (activity as CreateRoomActivity)
            binding.lifecycleOwner=this
        }


    }




}