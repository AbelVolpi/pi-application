package com.projetointegrador.pi_application.presentation.ui

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.*
import com.projetointegrador.pi_application.presentation.viewmodel.ProfileViewModel
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.utils.Utils.showAboutDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                    R.id.account_item -> {
                        showAccountActionsDialog()
                    }

                    R.id.about_item -> {
                        showAboutDialog(requireContext(), layoutInflater)
                    }

                    R.id.logout_item -> {
                        showConfirmLogoutDialog()
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

    private fun showAccountActionsDialog() {
        val layout = AccountActionsDialogLayoutBinding.inflate(
            layoutInflater,
            null,
            false
        )

        Dialog(requireContext()).apply {
            val thisDialog = this
            setContentView(layout.root)

            layout.deleteAccountLayout.setOnClickListener {
                this.dismiss()
                showRemoveAccountDialog()
            }

            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(thisDialog.window?.attributes)
                width = (resources.displayMetrics.widthPixels * 0.85).toInt()
            }
            thisDialog.window?.attributes = layoutParams

            show()
        }
    }

    private fun showRemoveAccountDialog() {

        val layout = DoubleOptionsDialogBinding.inflate(
            layoutInflater,
            null,
            false
        )

        Dialog(requireContext()).apply {
            val thisDialog = this

            setContentView(layout.root)

            layout.textBody.text = getString(R.string.are_you_sure)

            layout.yesOption.setOnClickListener {
                removeAccount()
                thisDialog.dismiss()
            }

            layout.noOption.setOnClickListener {
                thisDialog.dismiss()
            }

            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(thisDialog.window?.attributes)
                width = (resources.displayMetrics.widthPixels * 0.85).toInt()
            }
            thisDialog.window?.attributes = layoutParams
            show()
        }
    }

    private fun showConfirmLogoutDialog() {
        val layout = DoubleOptionsDialogBinding.inflate(
            layoutInflater,
            null,
            false
        )
        Dialog(requireContext()).apply {
            val thisDialog = this
            setContentView(layout.root)

            layout.textBody.text = getString(R.string.want_quit)

            layout.yesOption.setOnClickListener {
                thisDialog.dismiss()
                viewModel.logOut()
                navController.navigate(R.id.action_profileFragment_to_homeFragment)
            }

            layout.noOption.setOnClickListener {
                thisDialog.dismiss()
            }

            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(thisDialog.window?.attributes)
                width = (resources.displayMetrics.widthPixels * 0.85).toInt()
            }
            thisDialog.window?.attributes = layoutParams
            show()
        }
    }

    private fun removeAccount() {
        val userId = SessionManager.getGetUserId() ?: ""

        viewModel.removeAccount(userId)
        navController.navigate(R.id.action_profileFragment_to_homeFragment)
    }
}
