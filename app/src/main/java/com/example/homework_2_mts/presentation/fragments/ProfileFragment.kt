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
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.ProfileEntity
import com.example.homework_2_mts.repository.models.PopularNowModel
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    // init ViewModel
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel
    private lateinit var rvInteresting: RecyclerView
    private lateinit var saveProfileInfoButton: Button
    private lateinit var rootView: ConstraintLayout
    private lateinit var nameProfileTextView: TextView
    private lateinit var emailProfileTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var emailIEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var progressBar: FrameLayout
    private lateinit var genresAdapter: PopularNowAdapter

    data class InsertProfileState(
        val isInsert: Boolean = false,
        val exception: Throwable? = null
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // initView
        saveProfileInfoButton = view.findViewById(R.id.btnSaveProfileInfo)
        rootView = view.findViewById(R.id.rootViewProfileFragment)
        nameProfileTextView = view.findViewById(R.id.tvFirstNameUser)
        emailProfileTextView = view.findViewById(R.id.tvEmailUser)
        nameEditText = view.findViewById(R.id.ilEtNameUser)
        passwordEditText = view.findViewById(R.id.ilEtPasswordUser)
        emailIEditText = view.findViewById(R.id.ilEtEmailUser)
        phoneNumberEditText = view.findViewById(R.id.ilEtPhoneNumberUser)
        progressBar = view.findViewById(R.id.progressBar)

        //initListener
        saveProfileInfoButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailIEditText.text.toString()
            val password = passwordEditText.text.toString()
            val phone = phoneNumberEditText.text.toString()
            profileFragmentViewModel.insertProfile(
                ProfileEntity(
                    id = 1,
                    name = name,
                    email = email,
                    password = password,
                    phoneNumber = phone
                )
            )
        }

        // initRv
        rvInteresting = view.findViewById(R.id.rvInterestsUser)
        genresAdapter = PopularNowAdapter() {}

        // initObserver
        profileFragmentViewModel = ProfileFragmentViewModel()
        profileFragmentViewModel.profileInsertComplete.observe(
            viewLifecycleOwner,
            Observer(::showSnackBar)
        )
        profileFragmentViewModel.getProfileEntityInfo.observe(
            viewLifecycleOwner,
            Observer(::setProfileInfo)
        )
        profileFragmentViewModel.genresList.observe(
            viewLifecycleOwner,
            Observer(::initGenresData)
        )

        profileFragmentViewModel.getProfile()

        return view
    }

    private fun initGenresData(genresList: List<GenreEntity>) {
        genresAdapter.initData(genresList)
        rvInteresting.apply {
            adapter = genresAdapter
            if (this.itemDecorationCount == 0) {
                addItemDecoration(
                    SpacesItemDecoration(
                        spaceRight = 6,
                        spaceLeft = 20,
                        size = genresList.size
                    )
                )
            }
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

    private fun setProfileInfo(profileEntity: ProfileEntity?) {
        if (profileEntity != null) {
            nameProfileTextView.text = profileEntity.name
            emailProfileTextView.text = profileEntity.email
            nameEditText.setText(profileEntity.name)
            emailIEditText.setText(profileEntity.email)
            passwordEditText.setText(profileEntity.password)
            phoneNumberEditText.setText(profileEntity.phoneNumber)
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