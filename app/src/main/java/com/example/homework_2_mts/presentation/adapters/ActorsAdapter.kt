package com.example.homework_2_mts.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.squareup.picasso.Picasso

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    private var items: MutableList<ActorEntity> = ArrayList()

    inner class ActorsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val actorPhotoImageView: ImageView = itemView.findViewById(R.id.imgActorPhoto)
        private val actorNameTextView: TextView = itemView.findViewById(R.id.tvActorName)

        fun bind(actorEntity: ActorEntity) {
            if (actorEntity.photoUrl != null) {
                Picasso.get()
                    .load(App.applicationContext.getString(R.string.actor_photo_url) + actorEntity.photoUrl)
                    .into(actorPhotoImageView)
            } else {
                actorPhotoImageView.setImageResource(R.drawable.actor_no_photo)
            }

            actorNameTextView.text = actorEntity.name
        }

    }

    fun initData(list: List<ActorEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ActorsViewHolder(inflater.inflate(R.layout.item_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}