package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.databinding.FragmentMapsBinding
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.presentation.adapter.CampaignInfoWindow
import com.projetointegrador.pi_application.presentation.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate) {
    private val viewModel: MapsViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var infoWindow: CampaignInfoWindow
    private var initialCameraSet = false

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    private fun setupMap() {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        binding.mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }
        infoWindow =
            CampaignInfoWindow(binding.mapView) { campaignId ->
                navController.navigate(MapsFragmentDirections.actionMapsFragmentToViewCampaignFragment(campaignId))
            }
    }

    private fun initViews() {
        getAllCampaigns()
        with(binding) {
            filterSpinner.apply {
                adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.donate_options))
                onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            adapterView: AdapterView<*>?,
                            p1: View?,
                            i: Int,
                            p3: Long
                        ) {
                            getCampaignsByCategory(adapterView?.getItemAtPosition(i).toString())
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {}
                    }
            }
            arrowBack.setOnClickListener { navController.popBackStack() }
        }
    }

    private fun getAllCampaigns() {
        viewModel.getAllCampaigns().observe(viewLifecycleOwner) { response ->
            when (response) {
                is TaskResponse.Success -> setAllMarkers(response.data)
                is TaskResponse.Failure -> {}
            }
        }
    }

    private fun getCampaignsByCategory(category: String) {
        if (category.isEmpty() || category == "Outros") {
            getAllCampaigns()
        } else {
            viewModel.getCampaignByCategory(category).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is TaskResponse.Success -> setAllMarkers(response.data)
                    is TaskResponse.Failure -> {}
                }
            }
        }
    }

    private fun setAllMarkers(campaignsList: List<Campaign>) {
        binding.mapView.overlays.clear()
        binding.mapsProgressBar.visibility = View.VISIBLE
        campaignsList.forEach { setMarker(it) }
        binding.mapView.invalidate()
        binding.mapsProgressBar.visibility = View.INVISIBLE
        if (!initialCameraSet) {
            changeCameraPosition()
            initialCameraSet = true
        }
    }

    private fun setMarker(campaign: Campaign) {
        val lat = campaign.campaignLatLng?.latitude?.toDoubleOrNull() ?: return
        val lng = campaign.campaignLatLng?.longitude?.toDoubleOrNull() ?: return
        Marker(binding.mapView).apply {
            position = GeoPoint(lat, lng)
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_map_pin)
            relatedObject = campaign
            this.infoWindow = this@MapsFragment.infoWindow
            setOnMarkerClickListener { marker, _ ->
                if (marker.isInfoWindowShown) marker.closeInfoWindow() else marker.showInfoWindow()
                true
            }
            binding.mapView.overlays.add(this)
        }
    }

    private fun changeCameraPosition() {
        binding.mapView.controller.apply {
            setZoom(10.0)
            animateTo(GeoPoint(-26.9056537, -49.0782393))
        }
    }
}
