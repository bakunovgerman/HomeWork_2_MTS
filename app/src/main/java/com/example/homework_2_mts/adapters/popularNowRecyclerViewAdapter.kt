package com.example.homework_2_mts.adapters

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.PopularNowDto

class popularNowRecyclerViewAdapter (private val list: List<PopularNowDto>)
    : RecyclerView.Adapter<popularNowRecyclerViewAdapter.PopularNowViewHolder>(){

    class PopularNowViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.popular_now_item, parent, false)) {
        private var popularNowNameView: TextView? = null


        init {
            popularNowNameView = itemView.findViewById(R.id.popularNowText)
        }

        fun bind(popularNowDto: PopularNowDto) {
            popularNowNameView?.text = popularNowDto.name
        }

    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularNowViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return PopularNowViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: PopularNowViewHolder, position: Int) {
            val popularNowDto: PopularNowDto = list[position]
            holder.bind(popularNowDto)
        }

        override fun getItemCount(): Int = list.size
 }