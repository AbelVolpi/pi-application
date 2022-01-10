package com.projetointegrador.pi_application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentMapsBinding
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.Utils
import com.projetointegrador.pi_application.viewmodel.MapsViewModel

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private lateinit var binding: FragmentMapsBinding
    private val viewModel: MapsViewModel by activityViewModels()
    private var map: GoogleMap? = null
    private val navController by lazy {
        findNavController()
    }

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

    private fun initViews() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment?.getMapAsync(this@MapsFragment)

        getAllCampaigns()

        with(binding) {
            filterFieldOptions.apply {
                setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        resources.getStringArray(R.array.donate_options)
                    )
                )
                setOnItemClickListener { adapterView, _, i, _ ->
                    val category = adapterView.getItemAtPosition(i).toString()
                    getCampaignsByCategory(category)
                }
            }
            arrowBack.setOnClickListener {
                navController.popBackStack()
            }
        }

    }

    private fun getAllCampaigns() {
        viewModel.getAllCampaigns().observe(viewLifecycleOwner) { response ->
            when (response) {
                is FirebaseResponse.Success -> {
                    setAllMarkers(response.data)
                }
                is FirebaseResponse.Failure -> {

                }
            }
        }
    }

    private fun getCampaignsByCategory(category: String) {
        viewModel.getCampaignByCategory(category).observe(viewLifecycleOwner) { response ->
            when (response) {
                is FirebaseResponse.Success -> {
                    setAllMarkers(response.data)
                }
                is FirebaseResponse.Failure -> {

                }
            }
        }
    }

    private fun setAllMarkers(campaignsList: List<Campaign>) {
        map?.clear()

        campaignsList.forEach { campaign ->
            setMarker(campaign)
        }
        changeCameraPosition()

    }

    private fun setMarker(campaign: Campaign) {

        val latLng = campaign.campaignLatLng?.latitude?.let { latitude ->
            campaign.campaignLatLng?.longitude?.let { longitude ->
                LatLng(latitude.toDouble(), longitude.toDouble())
            }
        }
        val icon = Utils.bitmapFromResource(R.drawable.ic_map_pin, requireContext())

        val marker = MarkerOptions().apply {
            latLng?.let { position(it) }
            icon(icon)
        }

        map?.addMarker(
            marker
        )

    }

    private fun changeCameraPosition() {
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(-26.9056537, -49.0782393), 10f),
            900,
            object : GoogleMap.CancelableCallback {
                override fun onCancel() {

                }

                override fun onFinish() {

                }
            })
    }


}