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
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        rvInteresting = view.findViewById(R.id.rvInterestsUser)
        rvInteresting.apply {
            val items = interestingUserModel.getPopularNow()

            adapter = PopularNowAdapter(items) {
                Unit
            }
            addItemDecoration(SpacesItemDecoration(spaceRight = 6 ,spaceLeft = 20, size = items.size))
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
//            ProfileFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}