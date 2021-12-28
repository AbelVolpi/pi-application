package com.projetointegrador.pi_application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        val donateOptions = resources.getStringArray(R.array.donate_options)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,donateOptions)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

}