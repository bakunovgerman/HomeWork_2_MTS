package com.example.homework_2_mts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.PopularNowDto

class PopularNowRecyclerViewAdapter(
    private val list: List<PopularNowDto>,
    val onPopularNowItemClick: ((PopularNowDto) -> Unit)
) : RecyclerView.Adapter<PopularNowRecyclerViewAdapter.PopularNowViewHolder>() {

    private var popularNowNameView: TextView? = null

    inner class PopularNowViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.popular_now_item, parent, false)) {

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
        holder.bind(list[position])
        popularNowNameView?.setOnClickListener { onPopularNowItemClick?.invoke(list[position]) }
    }

    override fun getItemCount(): Int = list.size
}