package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.utils.FirebaseResponse
import com.projetointegrador.pi_application.core.utils.Utils
import com.projetointegrador.pi_application.databinding.FragmentMapsBinding
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.presentation.adapter.InfoWindowAdapter
import com.projetointegrador.pi_application.presentation.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var campaignsGeneralList: List<Campaign>
    private val viewModel: MapsViewModel by viewModels()
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

        showBoxOnMarkerClick()
    }

    private fun initViews() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment?.getMapAsync(this@MapsFragment)

        getAllCampaigns()

        with(binding) {

            filterSpinner.apply {
                adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_item,
                    resources.getStringArray(R.array.donate_options)
                )
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        p1: View?,
                        i: Int,
                        p3: Long
                    ) {
                        val category = adapterView?.getItemAtPosition(i).toString()
                        getCampaignsByCategory(category)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
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
        if (category.isEmpty() || category == "Outros")
            getAllCampaigns()
        else
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
        binding.mapsProgressBar.visibility = View.VISIBLE

        campaignsList.forEachIndexed { index, campaign ->
            setMarker(campaign, index)
        }
        campaignsGeneralList = campaignsList

        binding.mapsProgressBar.visibility = View.INVISIBLE
        changeCameraPosition()
    }

    private fun setMarker(campaign: Campaign, position: Int) {

        val latLng = campaign.campaignLatLng?.latitude?.let { latitude ->
            campaign.campaignLatLng?.longitude?.let { longitude ->
                LatLng(latitude.toDouble(), longitude.toDouble())
            }
        }
        val icon = Utils.bitmapFromResource(R.drawable.ic_map_pin, requireContext())

        val marker = MarkerOptions().apply {
            latLng?.let { position(it) }
            icon(icon)
            zIndex(position.toFloat())
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
            }
        )
    }

    private fun showBoxOnMarkerClick() {
        map?.setOnMarkerClickListener { marker ->

            val campaign = campaignsGeneralList[marker.zIndex.toInt()]

            map?.setInfoWindowAdapter(InfoWindowAdapter(requireContext(), campaign))

            marker.showInfoWindow()

            true
        }

        map?.setOnInfoWindowClickListener { marker ->
            val campaign = campaignsGeneralList[marker.zIndex.toInt()]

            navController.navigate(
                MapsFragmentDirections.actionMapsFragmentToViewCampaignFragment(
                    campaign
                )
            )
        }
    }
}
