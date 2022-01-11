package com.projetointegrador.pi_application.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentProfileBinding
import com.projetointegrador.pi_application.databinding.SideBarHeaderBinding
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initDrawerToggle(container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        with(binding) {
            cardViewCreateCampaignProfile.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_createCampaignFragment)
            }
            cardViewGoToMapProfile.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_mapsFragment)
            }
            menuButton.setOnClickListener {
                drawerLayout.openDrawer(Gravity.LEFT)
            }
        }
    }

    private fun initDrawerToggle(viewGroup: ViewGroup?) {
        with(binding) {
            val sideBarHeader = SideBarHeaderBinding.inflate(
                LayoutInflater.from(requireContext()),
                viewGroup,
                false
            )
            sideBarHeader.userEmailPlaceHolder.text = SessionManager.getUserEmail()
            navView.addHeaderView(sideBarHeader.root)

            drawerToggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(drawerToggle)
            drawerToggle.syncState()

            navView.setNavigationItemSelectedListener { menuItem ->
                drawerLayout.closeDrawer(Gravity.LEFT)
                when (menuItem.itemId) {
                    R.id.historic_item -> {
                        navController.navigate(R.id.action_profileFragment_to_campaignsHistoricFragment)
                    }

                    R.id.about_item -> {

                    }

                    R.id.logout_item -> {
                        viewModel.logOut()
                        navController.navigate(R.id.action_profileFragment_to_homeFragment)
                    }
                }

                true
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}