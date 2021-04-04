package com.adnroidapp.amazingappnasa.ui.adapter

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adnroidapp.amazingappnasa.BuildConfig
import com.adnroidapp.amazingappnasa.PlanetsEnum
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.data.ImageDataClass
import kotlinx.android.synthetic.main.fragment_image.view.*
import kotlinx.android.synthetic.main.view_holder_image.view.*

class AdapterImage (private val viewContainer: View) : RecyclerView.Adapter<AdapterImage.ImageHolder>() {

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

  inner class ImageHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        private var isExpanded = false

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

            item.img_view_holder.setOnClickListener {
                enlargeImage()
            }
        }

        private fun imageLoad(url: String) {
            itemView.img_view_holder.load(url) {
                crossfade(true)
                error(R.drawable.ic_menu_bot_nav_mars)
                    .placeholder(R.drawable.ic_placeholder_earth)
            }
        }

        private fun enlargeImage() {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                viewContainer.container_image_layout, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = item.img_view_holder.layoutParams
            params.height = if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT
            else ViewGroup.LayoutParams.WRAP_CONTENT

            item.img_view_holder.layoutParams = params
            item.img_view_holder.scaleType = if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }
}