package com.example.newapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newapplication.databinding.FragmentProfileBinding
import com.example.newapplication.sharedPref.sharedPref

class FragmentProfile: Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: sharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = sharedPref(requireContext())


        binding.btnlogout.setOnClickListener {
            sharedPref.logout()
            requireActivity().finish()
        }
    }

}