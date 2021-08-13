package com.example.homework_2_mts.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import java.lang.Exception


class ProfileFragment : Fragment() {

    // init ViewModel
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel

    private lateinit var rvInteresting: RecyclerView
    private lateinit var saveProfileInfoButton: Button
    private val interestingUserModel = PopularNowModel(PopularNowDataSourceImpl())
    private lateinit var rootView: ConstraintLayout

    data class InsertProfileState(
        val isInsert: Boolean = false,
        val exception: Throwable? = null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileFragmentViewModel = ProfileFragmentViewModel()
        profileFragmentViewModel.profileInsertComplete.observe(
            viewLifecycleOwner,
            Observer(::showSnackBar)
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initView(view)
        initListener()
        initRv(view)

        profileFragmentViewModel.getProfile()

        return view
    }

    private fun initView(view: View) {
        saveProfileInfoButton = view.findViewById(R.id.btnSaveProfileInfo)
        rootView = view.findViewById(R.id.rootViewProfileFragment)
    }

    private fun initListener(){
        saveProfileInfoButton.setOnClickListener {

        }
    }

    private fun initRv(view: View){
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
        if (insertProfileState.isInsert)
            Snackbar.make(rootView, "Информация о профиле изменена!", Snackbar.LENGTH_LONG).show()
        else if (insertProfileState.exception != null)
            Snackbar.make(rootView, "Ошибка: ${insertProfileState.exception}", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun setProfileInfo(profile: Profile){

    }
}