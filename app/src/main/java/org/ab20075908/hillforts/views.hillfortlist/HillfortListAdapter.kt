package org.ab20075908.hillforts.views.hillfortlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.models.HillfortModel

//Adapter for HillfortList


interface HillfortListener {
    fun onHillfortClick(hillfort: HillfortModel)
}


class HillfortAdapter constructor(
    private var hillforts: List<HillfortModel>,
    private val listener: HillfortListener
) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_hillfort,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel,  listener : HillfortListener) {
            itemView.hillfortTitle.text = hillfort.title + " | Rating: " + hillfort.rating
            itemView.description.text = hillfort.description
            Glide.with(itemView.context).load(hillfort.image1).into(itemView.imageIcon);
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
        }
    }
}