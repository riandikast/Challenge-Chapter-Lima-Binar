package com.binar.challengechapterlima.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.binar.challengechapterlima.R


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        Handler().postDelayed({
            if (
                (requireContext().getSharedPreferences("login", Context.MODE_PRIVATE).contains("login_state")))
            {
                view.findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }else{

                view.findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)

            }

        }, 2000)

        return view
    }

}