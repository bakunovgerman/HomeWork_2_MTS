package com.example.homework_2_mts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.PopularNowDto

class PopularNowAdapter(
    private val list: List<PopularNowDto>,
    private val onPopularNowItemClick: ((PopularNowDto) -> Unit)
) : RecyclerView.Adapter<PopularNowAdapter.PopularNowViewHolder>() {

    inner class PopularNowViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val tvPopularNowName: TextView = itemView.findViewById(R.id.popularNowText)
        private lateinit var item: PopularNowDto

        init {
            tvPopularNowName.setOnClickListener {
                onPopularNowItemClick.invoke(item)
            }
        }

        fun bind(popularNowDto: PopularNowDto) {
            item = popularNowDto
            tvPopularNowName.text = popularNowDto.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularNowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PopularNowViewHolder(inflater.inflate(R.layout.popular_now_item, parent, false))
    }

    override fun onBindViewHolder(holder: PopularNowViewHolder, position: Int) {
        holder.bind(list[position])
        holder.tvPopularNowName.setOnClickListener { onPopularNowItemClick.invoke(list[position]) }
    }

    override fun getItemCount(): Int = list.size
}