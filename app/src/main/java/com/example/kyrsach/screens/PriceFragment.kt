package com.example.kyrsach.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kyrsach.MAIN
import com.example.kyrsach.R
import com.example.kyrsach.databinding.FragmentFirstBinding
import com.example.kyrsach.databinding.FragmentPriceBinding

class PriceFragment : Fragment() {

    lateinit var binding: FragmentPriceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            MAIN.navController.navigate(R.id.action_priceFragment2_to_firstFragment)
        }
    }
}