package com.example.homework_2_mts.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.entities.Genre

class PopularNowAdapter(
    private val onPopularNowItemClick: (Genre) -> Unit
) : RecyclerView.Adapter<PopularNowAdapter.PopularNowViewHolder>() {

    private var items: MutableList<Genre> = ArrayList()

    inner class PopularNowViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val popularNowNameText: TextView = itemView.findViewById(R.id.tvPopularNow)
        private lateinit var item: Genre

        init {
            popularNowNameText.setOnClickListener {
                onPopularNowItemClick.invoke(item)
            }
        }

        fun bind(genre: Genre) {
            item = genre
            popularNowNameText.text = genre.name
        }

    }

    fun initData(popularNowItems: List<Genre>?) {
        items.clear()
        items.addAll(popularNowItems!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularNowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PopularNowViewHolder(inflater.inflate(R.layout.popular_now_item, parent, false))
    }

    override fun onBindViewHolder(holder: PopularNowViewHolder, position: Int) {
        holder.bind(items[position])
        holder.popularNowNameText.setOnClickListener { onPopularNowItemClick.invoke(items[position]) }
    }

    override fun getItemCount(): Int = items.size
}