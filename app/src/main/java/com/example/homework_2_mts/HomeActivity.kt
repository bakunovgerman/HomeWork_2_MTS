package com.example.homework_2_mts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.adapters.SpacesItemDecoration
import com.example.homework_2_mts.adapters.popularNowRecyclerViewAdapter
import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.models.PopularNowModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var popularNowModel: PopularNowModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        popularNowModel = PopularNowModel(PopularNowDataSourceImpl())
        findViewById<RecyclerView>(R.id.popularNowRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowRecyclerViewAdapter(popularNowModel.getPopularNow())
            addItemDecoration(SpacesItemDecoration(20))
        }
    }
}