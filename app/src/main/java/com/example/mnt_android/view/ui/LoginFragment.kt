package com.example.mnt_android.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mnt_android.R
import com.example.mnt_android.databinding.FragmentLogin1Binding
import com.example.mnt_android.viewmodel.LoginViewModel

class LoginFragment : Fragment()
{
    lateinit var loginViewModel: LoginViewModel
    lateinit var binding: FragmentLogin1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login1,container,false)




        //Log.d("wlgusdnzzz",loginViewModel.kuser.nickname.value)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            loginViewModel=(activity as LoginActivity).loginViewModel

            binding.loginViewModel = (activity as LoginActivity).loginViewModel
            binding.loginActivity=(activity as LoginActivity)
            binding.lifecycleOwner=this
        }
    }
}