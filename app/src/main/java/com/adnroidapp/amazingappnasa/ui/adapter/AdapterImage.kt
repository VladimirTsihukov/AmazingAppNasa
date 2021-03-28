package com.adnroidapp.amazingappnasa.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adnroidapp.amazingappnasa.BuildConfig
import com.adnroidapp.amazingappnasa.PlanetsEnum
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.data.ImageDataClass
import kotlinx.android.synthetic.main.view_holder_image.view.*

class AdapterImage : RecyclerView.Adapter<ImageHolder>() {

    private var image = listOf<ImageDataClass>()
    private var namePlanet: PlanetsEnum = PlanetsEnum.EARTH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.onBind(image[position], namePlanet)
    }

    override fun getItemCount(): Int = image.size

    fun bindImage(imageResponse: List<ImageDataClass>, planetsEnum: PlanetsEnum) {
        namePlanet = planetsEnum
        image = imageResponse
        notifyDataSetChanged()
    }
}

class ImageHolder(private val item: View) : RecyclerView.ViewHolder(item) {
    fun onBind(date: ImageDataClass, planetsEnum: PlanetsEnum) {

        when (planetsEnum) {
            PlanetsEnum.EARTH -> imageLoad(
                "https://api.nasa.gov/EPIC/archive/natural/2021/" +
                        "03/25" +
                        "/png/${date.image}.png?api_key=${BuildConfig.NASA_API_KEY}"
            )
            PlanetsEnum.MARS -> imageLoad(date.image)
            PlanetsEnum.MOON -> TODO()
        }
        item.text_cart_date.text = date.date
        item.text_cart_planet.text = date.namePlanet
    }

    private fun imageLoad(url: String) {
        itemView.img_view_holder.load(url) {
            crossfade(true)
            error(R.drawable.ic_menu_bot_nav_mars)
                .placeholder(R.drawable.ic_placeholder_earth)
        }
    }
}