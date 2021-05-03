package com.mahmoudbashir.weathertask_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen_Fragment : Fragment() {

    lateinit var splashBinding:FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        splashBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_splash_screen_, container, false)

        GlobalScope.launch (Dispatchers.Main){
            navigateToHome()
        }

        return splashBinding.root
    }
    private suspend fun navigateToHome(){
        delay(3000)
        findNavController().navigate(SplashScreen_FragmentDirections.actionSplashScreenFragmentToMainHomeFragment())
    }
}