package com.projetointegrador.pi_application.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentMapsBinding

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap


    }

    override fun onCameraMove() {
        TODO("Not yet implemented")
    }
    private fun initViews(){
        with(binding){
            val mapFragment = childFragmentManager.findFragmentById(R.id.map_view
            ) as SupportMapFragment?
            mapFragment?.getMapAsync(this@MapsFragment)
        }
    }

}