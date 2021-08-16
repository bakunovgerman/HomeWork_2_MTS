package com.example.homework_2_mts.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.domain.ProfileFragmentViewModel
import com.example.homework_2_mts.presentation.adapters.PopularNowAdapter
import com.example.homework_2_mts.presentation.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.repository.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.repository.database.entities.Profile
import com.example.homework_2_mts.repository.models.PopularNowModel
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    // init ViewModel
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel
    private lateinit var rvInteresting: RecyclerView
    private lateinit var saveProfileInfoButton: Button
    private val interestingUserModel = PopularNowModel(PopularNowDataSourceImpl())
    private lateinit var rootView: ConstraintLayout
    private lateinit var nameProfileTextView: TextView
    private lateinit var emailProfileTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var emailIEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var progressBar: FrameLayout

    data class InsertProfileState(
        val isInsert: Boolean = false,
        val exception: Throwable? = null
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initView(view)
        initListener()
        initRv(view)
        initObserver()

        profileFragmentViewModel.getProfile()

        return view
    }

    private fun initObserver() {
        profileFragmentViewModel = ProfileFragmentViewModel()
        profileFragmentViewModel.profileInsertComplete.observe(
            viewLifecycleOwner,
            Observer(::showSnackBar)
        )
        profileFragmentViewModel.getProfileInfo.observe(
            viewLifecycleOwner,
            Observer(::setProfileInfo)
        )
    }

    private fun initView(view: View) {
        saveProfileInfoButton = view.findViewById(R.id.btnSaveProfileInfo)
        rootView = view.findViewById(R.id.rootViewProfileFragment)
        nameProfileTextView = view.findViewById(R.id.tvFirstNameUser)
        emailProfileTextView = view.findViewById(R.id.tvEmailUser)
        nameEditText = view.findViewById(R.id.ilEtNameUser)
        passwordEditText = view.findViewById(R.id.ilEtPasswordUser)
        emailIEditText = view.findViewById(R.id.ilEtEmailUser)
        phoneNumberEditText = view.findViewById(R.id.ilEtPhoneNumberUser)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun initListener() {
        saveProfileInfoButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailIEditText.text.toString()
            val password = passwordEditText.text.toString()
            val phone = phoneNumberEditText.text.toString()
            profileFragmentViewModel.insertProfile(
                Profile(
                    id = 1,
                    name = name,
                    email = email,
                    password = password,
                    phoneNumber = phone
                )
            )
        }
    }

    private fun initRv(view: View) {
        rvInteresting = view.findViewById(R.id.rvInterestsUser)
        val popularNowAdapter = PopularNowAdapter() {

        }
        val items = interestingUserModel.getPopularNow()
        popularNowAdapter.initData(items)
        rvInteresting.apply {
            adapter = popularNowAdapter
            addItemDecoration(
                SpacesItemDecoration(
                    spaceRight = 6,
                    spaceLeft = 20,
                    size = items.size
                )
            )
        }
    }

    private fun showSnackBar(insertProfileState: InsertProfileState) {
        if (insertProfileState.isInsert) {
            Snackbar.make(rootView, "Информация о профиле изменена!", Snackbar.LENGTH_LONG).show()
            showProgressBar()
            profileFragmentViewModel.getProfile()
        } else if (insertProfileState.exception != null)
            Snackbar.make(rootView, "Ошибка: ${insertProfileState.exception}", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun setProfileInfo(profile: Profile?) {
        if (profile != null) {
            nameProfileTextView.text = profile.name
            emailProfileTextView.text = profile.email
            nameEditText.setText(profile.name)
            emailIEditText.setText(profile.email)
            passwordEditText.setText(profile.password)
            phoneNumberEditText.setText(profile.phoneNumber)
        }
        hideProgressBar()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}