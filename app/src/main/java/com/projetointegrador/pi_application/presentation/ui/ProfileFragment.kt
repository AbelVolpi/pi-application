package com.projetointegrador.pi_application.presentation.ui

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.core.utils.Utils.showAboutDialog
import com.projetointegrador.pi_application.databinding.AccountActionsDialogLayoutBinding
import com.projetointegrador.pi_application.databinding.DoubleOptionsDialogBinding
import com.projetointegrador.pi_application.databinding.FragmentProfileBinding
import com.projetointegrador.pi_application.databinding.SideBarHeaderBinding
import com.projetointegrador.pi_application.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private val navController by lazy { findNavController() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initDrawerToggle()
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
            menuButton.setOnClickListener { drawerLayout.openDrawer(Gravity.LEFT) }
        }
    }

    private fun initDrawerToggle() {
        with(binding) {
            val sideBarHeader = SideBarHeaderBinding.inflate(layoutInflater, navView, false)
            sideBarHeader.userEmailPlaceHolder.text = viewModel.getUserEmail()
            navView.addHeaderView(sideBarHeader.root)

            drawerToggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(drawerToggle)
            drawerToggle.syncState()

            navView.setNavigationItemSelectedListener { menuItem ->
                drawerLayout.closeDrawer(Gravity.LEFT)
                when (menuItem.itemId) {
                    R.id.historic_item -> navController.navigate(R.id.action_profileFragment_to_campaignsHistoricFragment)
                    R.id.account_item -> showAccountActionsDialog()
                    R.id.about_item -> showAboutDialog(requireContext(), layoutInflater)
                    R.id.logout_item -> showConfirmLogoutDialog()
                }
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    private fun showAccountActionsDialog() {
        val layout = AccountActionsDialogLayoutBinding.inflate(layoutInflater, null, false)
        Dialog(requireContext()).apply {
            setContentView(layout.root)
            layout.deleteAccountLayout.setOnClickListener {
                dismiss()
                showRemoveAccountDialog()
            }
            window?.attributes =
                WindowManager.LayoutParams().apply {
                    copyFrom(window?.attributes)
                    width = (resources.displayMetrics.widthPixels * 0.85).toInt()
                }
            show()
        }
    }

    private fun showRemoveAccountDialog() {
        val layout = DoubleOptionsDialogBinding.inflate(layoutInflater, null, false)
        Dialog(requireContext()).apply {
            setContentView(layout.root)
            layout.textBody.text = getString(R.string.are_you_sure)
            layout.yesOption.setOnClickListener {
                viewModel.removeAccount()
                dismiss()
                navController.navigate(R.id.action_profileFragment_to_homeFragment)
            }
            layout.noOption.setOnClickListener { dismiss() }
            window?.attributes =
                WindowManager.LayoutParams().apply {
                    copyFrom(window?.attributes)
                    width = (resources.displayMetrics.widthPixels * 0.85).toInt()
                }
            show()
        }
    }

    private fun showConfirmLogoutDialog() {
        val layout = DoubleOptionsDialogBinding.inflate(layoutInflater, null, false)
        Dialog(requireContext()).apply {
            setContentView(layout.root)
            layout.textBody.text = getString(R.string.want_quit)
            layout.yesOption.setOnClickListener {
                dismiss()
                viewModel.logOut()
                navController.navigate(R.id.action_profileFragment_to_homeFragment)
            }
            layout.noOption.setOnClickListener { dismiss() }
            window?.attributes =
                WindowManager.LayoutParams().apply {
                    copyFrom(window?.attributes)
                    width = (resources.displayMetrics.widthPixels * 0.85).toInt()
                }
            show()
        }
    }
}
