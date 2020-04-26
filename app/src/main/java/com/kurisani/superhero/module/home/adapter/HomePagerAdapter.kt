package com.kurisani.superhero.module.home.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.common.util.CollectionUtils
import com.kurisani.superhero.R
import com.kurisani.superhero.model.SuperHeroResponse
import com.kurisani.superhero.module.home.HomeContract
import com.kurisani.superhero.room.entity.Superhero
import com.kurisani.superhero.util.Util

class HomePagerAdapter(
    private val activity: Activity,
    private val homeView: HomeContract.HomeView
) : RecyclerView.Adapter<HomePagerAdapter.HeroViewHolder>() {

    private var newHeros: MutableList<SuperHeroResponse>? = mutableListOf()
    private var localHeros: MutableList<Superhero>? = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder =
        HeroViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewpager_hero_item, parent, false)
        )

    override fun getItemCount(): Int {
        if (Util.isOnline()) {
            return newHeros?.size!!
        } else {
            return localHeros?.size!!
        }
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        if (Util.isOnline()) {
            val hero = newHeros?.get(position)

            if (hero != null) {
                holder.textViewHeroName.visibility = View.VISIBLE
                holder.textViewFav.visibility = View.VISIBLE
                holder.textViewHeroName.text = hero.name

                holder.layoutViewMore.visibility = View.VISIBLE
                holder.textViewNoLimit.visibility = View.GONE
                holder.textViewInfo.text = activity.getString(R.string.view_more)
                holder.textViewFav.text = activity.getString(R.string.favourite)

                holder.imageViewFav.setImageDrawable(
                    AppCompatResources.getDrawable(
                        activity,
                        R.drawable.ic_heart
                    )
                )

                holder.textViewInfo.setOnClickListener {
                    homeView.showDetails(hero)
                }

                holder.imageViewCover.visibility = View.VISIBLE
                Glide.with(activity)
                    .load(hero.image?.url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageViewCover)

                holder.itemView.tag = position
            }
        } else {
            val hero = localHeros?.get(position)

            if (hero != null) {
                holder.textViewHeroName.visibility = View.VISIBLE
                holder.textViewFav.visibility = View.VISIBLE
                holder.textViewHeroName.text = hero.name

                holder.layoutViewMore.visibility = View.VISIBLE
                holder.textViewNoLimit.visibility = View.GONE
                holder.textViewInfo.text = activity.getString(R.string.view_more)
                holder.textViewFav.text = activity.getString(R.string.favourite)

                holder.imageViewFav.setImageDrawable(
                    AppCompatResources.getDrawable(
                        activity,
                        R.drawable.ic_heart
                    )
                )

                holder.textViewInfo.setOnClickListener {
                    homeView.showOfflineDetails(hero)
                }

                holder.imageViewCover.visibility = View.VISIBLE
                Glide.with(activity)
                    .load(hero.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageViewCover)

                holder.itemView.tag = position
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return if (Util.isOnline()) {
            newHeros?.get(position)!!.hashCode().toLong()
        } else {
            localHeros?.get(position)!!.hashCode().toLong()
        }
    }

    fun setOnlineItem(response: SuperHeroResponse?) {
        if (Util.isOnline()) {
            response?.let { newHeros?.add(it) }
        }
    }

    fun setOfflineItem(response: List<Superhero>) {
        if (!CollectionUtils.isEmpty(response)) {
            response.let { localHeros?.addAll(it) }
        }

//        if (!CollectionUtils.isEmpty(response)) {
//            response.let {
//                it.forEach { nextHero ->
//                    localHeros?.add(nextHero)
//                }
//            }
//        }
        notifyDataSetChanged()
    }

    class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCover: ImageView = itemView.findViewById(R.id.imageViewCover)
        val textViewHeroName: TextView = itemView.findViewById(R.id.textViewHeroName)
        val layoutFav: RelativeLayout = itemView.findViewById(R.id.layoutFav)
        val imageViewFav: ImageView = itemView.findViewById(R.id.imageViewFav)
        val textViewFav: TextView = itemView.findViewById(R.id.textViewFav)
        val layoutViewMore: RelativeLayout = itemView.findViewById(R.id.layoutViewMore)
        val textViewInfo: TextView = itemView.findViewById(R.id.textViewInfo)
        val textViewNoLimit: TextView = itemView.findViewById(R.id.textViewNoLimit)
    }

}