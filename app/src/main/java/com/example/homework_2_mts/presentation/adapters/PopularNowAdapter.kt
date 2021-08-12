package com.example.homework_2_mts.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.data.dto.PopularNowDto

class PopularNowAdapter(
    private val onPopularNowItemClick: (PopularNowDto) -> Unit
) : RecyclerView.Adapter<PopularNowAdapter.PopularNowViewHolder>() {

    private var popularNowList: MutableList<PopularNowDto> = ArrayList()

    inner class PopularNowViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val popularNowNameText: TextView = itemView.findViewById(R.id.tvPopularNow)
        private lateinit var item: PopularNowDto

        init {
            popularNowNameText.setOnClickListener {
                onPopularNowItemClick.invoke(item)
            }
        }

        fun bind(popularNowDto: PopularNowDto) {
            item = popularNowDto
            popularNowNameText.text = popularNowDto.name
        }

    }

    fun initData(popularNowItems: List<PopularNowDto>?){
        popularNowList.clear()
        popularNowList.addAll(popularNowItems!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularNowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PopularNowViewHolder(inflater.inflate(R.layout.popular_now_item, parent, false))
    }

    override fun onBindViewHolder(holder: PopularNowViewHolder, position: Int) {
        holder.bind(popularNowList[position])
        holder.popularNowNameText.setOnClickListener { onPopularNowItemClick.invoke(popularNowList[position]) }
    }

    override fun getItemCount(): Int = popularNowList.size
}