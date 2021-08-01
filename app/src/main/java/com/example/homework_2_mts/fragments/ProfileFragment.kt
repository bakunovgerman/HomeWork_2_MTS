package com.example.homework_2_mts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.adapters.PopularNowAdapter
import com.example.homework_2_mts.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.models.PopularNowModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {

    private lateinit var rvInteresting: RecyclerView
    private val interestingUserModel = PopularNowModel(PopularNowDataSourceImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        rvInteresting = view.findViewById(R.id.rvInterestsUser)
        val popularNowAdapter = PopularNowAdapter(){

        }
        val items = interestingUserModel.getPopularNow()
        popularNowAdapter.popularNowList = items
        rvInteresting.apply {
            adapter = popularNowAdapter
            addItemDecoration(SpacesItemDecoration(spaceRight = 6 ,spaceLeft = 20, size = items.size))
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}