package com.example.kyrsach.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kyrsach.MAIN
import com.example.kyrsach.R
import com.example.kyrsach.databinding.FragmentSecondBinding
import com.example.kyrsach.db.MyDbManager
import com.example.kyrsach.db.UseListAdapter

class SecondFragment : Fragment() {

    lateinit var binding: FragmentSecondBinding
    lateinit var myDbManager: MyDbManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        myDbManager = MyDbManager(requireContext())
        readUse()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackRecycler.setOnClickListener {
            MAIN.navController.navigate(R.id.action_listFragment_to_firstFragment)
        }
    }

    fun readUse(init: Boolean = true) {
        myDbManager.openDb()
        if (init) {
            binding.recyclerView.adapter = UseListAdapter(requireContext(),myDbManager.readDbData())
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            val adapter = binding.recyclerView.adapter as UseListAdapter
            adapter.refresh(myDbManager.readDbData())
        }
    }
}