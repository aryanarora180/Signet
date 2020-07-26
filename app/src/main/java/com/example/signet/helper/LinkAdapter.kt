package com.example.signet.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.signet.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_link.view.*
import java.text.SimpleDateFormat
import java.util.*

class LinkAdapter : RecyclerView.Adapter<LinkAdapter.ViewHolder>() {

    var data: List<Link> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dataSavedText: TextView = view.link_date_text
        val linkTitleText: TextView = view.link_title_text
        val featuredImage: ImageView = view.link_featured_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_link, parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val link = data[position]

        if (link.featuredImageUrl.isNotEmpty())
            Picasso.get().load(link.featuredImageUrl).into(holder.featuredImage)
        else
            holder.featuredImage.visibility = View.GONE

        holder.linkTitleText.text = link.title
        holder.dataSavedText.text = dateFormatter.format(link.dateAdded)
    }

}