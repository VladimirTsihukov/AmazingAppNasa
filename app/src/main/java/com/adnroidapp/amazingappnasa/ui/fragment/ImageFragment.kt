package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.amazingappnasa.PlanetsEnum
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.toast
import com.adnroidapp.amazingappnasa.ui.adapter.AdapterImage
import com.adnroidapp.amazingappnasa.ui.viewModel.ImageViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class ImageFragment : Fragment(R.layout.fragment_image) {

    private val imageViewModel: ImageViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val adapterImage by lazy { AdapterImage() }
    private lateinit var bottomNav: BottomNavigationView
    private var namePlanets = PlanetsEnum.EARTH

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.res_view_image)
        recyclerView.adapter = adapterImage

        initBottomNavigation()

        imageViewModel.liveDataImage.observe(viewLifecycleOwner, {
            adapterImage.bindImage(it, namePlanets)
        })
    }

    private fun initBottomNavigation() {
        activity?.let {
            bottomNav = it.findViewById(R.id.bottom_nav_view)
            bottomNav.getOrCreateBadge(R.id.item_bottom_nav_earth)
            val badge = bottomNav.getBadge(R.id.item_bottom_nav_earth)
            badge?.apply {
                maxCharacterCount = 2
                badgeGravity = BadgeDrawable.TOP_END
                number = 8
            }
            bottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_bottom_nav_earth -> {
                        namePlanets = PlanetsEnum.EARTH
                        imageViewModel.getImagesEarthInServer()
                        true
                    }
                    R.id.item_bottom_nav_mars -> {
                        namePlanets = PlanetsEnum.MARS
                        imageViewModel.getPhotoMarsInServer()
                        true
                    }
                    R.id.item_bottom_nav_moon -> {
                        namePlanets = PlanetsEnum.MOON
                        toast("Moon")
                        true
                    }
                    else -> true
                }
            }
        }
    }

    companion object {
        fun newInstance() = ImageFragment()
        const val TAG = "ImageFragment"
    }
}